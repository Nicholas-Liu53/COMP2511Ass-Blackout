package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.blackout.devices.*;
import unsw.blackout.satellites.*;

public class Blackout {

    private WorldState worldState;

    // Constructor
    public Blackout() {
        worldState = new WorldState(LocalTime.of(0, 0));
    }

    public void createDevice(String id, String type, double position) {
        if (type.equals("HandheldDevice"))    worldState.addDevice(new HandheldDevice(id, position));
        else if (type.equals("LaptopDevice")) worldState.addDevice(new LaptopDevice(id, position));
        else                                  worldState.addDevice(new DesktopDevice(id, position));
    }

    public void createSatellite(String id, String type, double height, double position) {
        if (type.equals("SpaceXSatellite"))          worldState.addSatellite(new SpaceXSatellite(height, id, position));
        else if (type.equals("BlueOriginSatellite")) worldState.addSatellite(new BlueOriginSatellite(height, id, position));
        else if (type.equals("NasaSatellite"))       worldState.addSatellite(new NasaSatellite(height, id, position));
        else                                         worldState.addSatellite(new SovietSatellite(height, id, position));
    }

    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) throws Exception {
        Device theDevice = new Device("404notFound", 0, "404notFound", 0);
        boolean deviceFound = false;
        for (Device device : worldState.getDevices()) {
            if (device.getId().equals(deviceId)) {
                theDevice = device;
                deviceFound = true;
            }
        }
        if (!deviceFound) throw new Exception("Device doesn't exist");
        theDevice.setActivationPeriods(start, start.plusMinutes(durationInMinutes));
    }

    public void removeSatellite(String id) throws Exception {
        Satellite theSatellite = new Satellite(0, "404notFound", 0, "404notFound", 0);
        boolean satelliteFound = false;
        for (Satellite satellite : worldState.getSatellites()) {
            if (satellite.getId().equals(id)) {
                theSatellite = satellite;
                satelliteFound = true;
            }
            if (!satelliteFound) throw new Exception("Satellite doesn't exist");
        }
        worldState.removeSatellite(theSatellite);
    }

    public void removeDevice(String id) throws Exception {
        Device theDevice = new Device("404notFound", 0, "404notFound", 0);
        boolean deviceFound = false;
        for (Device device : worldState.getDevices()) {
            if (device.getId().equals(id)) {
                theDevice = device;
                deviceFound = true;
            }
        }
        if (!deviceFound) throw new Exception("Device doesn't exist");
        worldState.removeDevice(theDevice);
    }

    public void moveDevice(String id, double newPos) throws Exception {
        Device theDevice = new Device("404notFound", 0, "404notFound", 0);
        boolean deviceFound = false;
        for (Device device : worldState.getDevices()) {
            if (device.getId().equals(id)) {
                theDevice = device;
                deviceFound = true;
            }
        }
        if (!deviceFound) throw new Exception("Device doesn't exist");
        theDevice.setPosition(newPos);
    }

    public JSONObject showWorldState() {
        JSONObject result = new JSONObject();
        JSONArray devices = new JSONArray();
        JSONArray satellites = new JSONArray();

        // "devices" : [
        for (Device device : worldState.getDevices()) { // {
            JSONObject map = new JSONObject();
            map.put("activationPeriods", device.getActivationPeriods());  // "activationPeriods" : [{
            map.put("id", device.getId());                                // "id" : 
            map.put("isConnected", device.isConnected());                 // "isConnected" :
            map.put("position", device.getPosition());                    // "position" :
            map.put("type", device.getType());                            // "type" :
            devices.put(map);                          // }
        } // ],
    
        // "satellites" : [
        for (Satellite satellite : worldState.getSatellites()) { // {
            JSONObject map = new JSONObject();                       
            JSONArray connections = new JSONArray();                        // "connections" : [
            for (SatelliteConnection connection : satellite.getConnections()) {  // {
                JSONObject satelliteConnection = new JSONObject();
                satelliteConnection.put("deviceId", connection.getDeviceId());            // "deviceId" :
                satelliteConnection.put("endTime", connection.getEndTime());              // "endTime" :
                satelliteConnection.put("minutesActive", connection.getMinutesActive());  // "minutesActive" :
                satelliteConnection.put("satelliteId", connection.getSatelliteId());      // "satelliteId" :
                satelliteConnection.put("startTime", connection.getStartTime());          // "startTime" :
                connections.put(satelliteConnection);                            // }
            }                                                               
            map.put("connections", connections);                            // ],
            map.put("height", satellite.getHeight());                       // "height" :
            map.put("id", satellite.getId());                               // "id" :
            map.put("position", satellite.getPosition());                   // "position" :
            JSONArray possibleConnList = new JSONArray();                   // "possibleConnections" : [
            for (Device device : worldState.getDevices()) {              
                if (MathsHelper.satelliteIsVisibleFromDevice(satellite.getPosition(), satellite.getHeight(), device.getPosition())) {
                    if (satellite.getType().equals("SpaceXSatellite") && device.getType().equals("HandHeldDevice"))
                        possibleConnList.put(device.getId());
                    else if (satellite.getType().equals("BlueOriginSatellite") || satellite.getType().equals("NasaSatellite")) 
                        possibleConnList.put(device.getId());
                    else if (satellite.getType().equals("SovietSatellite") && !(device.getType().equals("HandHeldDevice")))
                        possibleConnList.put(device.getId());
                }
            }                                                               
            map.put("possibleConnections", possibleConnList);               // ],
            map.put("type", satellite.getType());                           // "type" : 
            map.put("velocity", satellite.getVelocity());                   // "velocity" :
            satellites.put(map);                                  // }
        } // ]

        result.put("devices", devices);
        result.put("satellites", satellites);

        // "currentTime" : <LocalTime>,
        result.put("currentTime", worldState.getTime());

        return result;
    }

    public void simulate(int tickDurationInMinutes) throws Exception {
        for (int i = 0; i < tickDurationInMinutes; i++) {
            
            //* It is now the next minute
            worldState.nextMinute();
            
            //* Update satellite positions
            for (Satellite satellite : worldState.getSatellites()) 
                satellite.newPosOneMinLater();
            
            //* Update connections
            for (Device device : worldState.getDevices()) {
                if (device.isConnected()) {
                    SatelliteConnection connection = findActiveConnectionByDevice(device);
                    Satellite connectedSatellite = connection.getSatellite();
                    if (
                        !(device.inActivationPeriod(worldState.getTime())) || 
                        !(MathsHelper.satelliteIsVisibleFromDevice(connectedSatellite.getPosition(), connectedSatellite.getHeight(), device.getPosition()))
                    ) {
                        connection.terminateConnection(worldState.getTime());
                    }
                    connection.updateMinutesActive(worldState.getTime());
                } 
                if (!(device.isConnected()) && device.inActivationPeriod(worldState.getTime())) {
                    HashMap<String, ArrayList<Satellite>> sortedSatelliteList = findSatellitesForDevice(device);
                    if (sortedSatelliteList.isEmpty()) continue;
                    else if (!sortedSatelliteList.get("SpaceXSatellite").isEmpty()) { 
                        SatelliteConnection connection = connectDeviceToSatellite(device, sortedSatelliteList.get("SpaceXSatellite").get(0));
                        connection.updateMinutesActive(worldState.getTime());
                    } else if (!sortedSatelliteList.get("BlueOriginSatellite").isEmpty()) {
                        SatelliteConnection connection = connectDeviceToSatellite(device, sortedSatelliteList.get("BlueSpaceSatellite").get(0));
                        connection.updateMinutesActive(worldState.getTime());
                    } else if (!sortedSatelliteList.get("SovietSatellite").isEmpty()) {
                        SatelliteConnection connection = connectDeviceToSatellite(device, sortedSatelliteList.get("SovietSatellite").get(0));
                        connection.updateMinutesActive(worldState.getTime());
                    } else if (!sortedSatelliteList.get("NasaSatellite").isEmpty()) {
                        SatelliteConnection connection = connectDeviceToSatellite(device, sortedSatelliteList.get("NasaSatellite").get(0));
                        connection.updateMinutesActive(worldState.getTime());
                    } 
                }
            }
        } 
    }

    //* Helper Functions
    // Finds the active connection of a device
    public SatelliteConnection findActiveConnectionByDevice(Device device) throws Exception {
        for (Satellite satellite : worldState.getSatellites()) {
            for (SatelliteConnection connection : satellite.getActiveConnections()) {
                if (connection.getDeviceId().equals(device.getId())) return connection;
            }
        }
        throw new Exception("No Active Connection Exists for this Device");
    }

    // Finds available satellites for a device and sorts them by type
    public HashMap<String, ArrayList<Satellite>> findSatellitesForDevice(Device device) {
        HashMap<String, ArrayList<Satellite>> sortedList = new HashMap<String, ArrayList<Satellite>>();
        sortedList.put("SpaceXSatellite", new ArrayList<Satellite>());
        sortedList.put("BlueOriginSatellite", new ArrayList<Satellite>());
        sortedList.put("NasaSatellite", new ArrayList<Satellite>());
        sortedList.put("SovietSatellite", new ArrayList<Satellite>());
        for (Satellite satellite : worldState.getSatellites()) {
            if (MathsHelper.satelliteIsVisibleFromDevice(satellite.getPosition(), satellite.getHeight(), device.getPosition())) {
                if (satellite.getType().equals("SpaceXSatellite") && device.getType().equals("HandheldDevice"))
                    sortedList.get(satellite.getType()).add(satellite);
                else if (satellite.getType().equals("BlueOriginSatellite")) {
                    if (satellite.getActiveConnections().size() < 11 && satellite.getLaptopConnections() < 6 && satellite.getDesktopConnections() < 3) 
                        sortedList.get(satellite.getType()).add(satellite);
                } else if (satellite.getType().equals("SovietSatellite") && !device.getType().equals("HandheldDevice"))
                    sortedList.get(satellite.getType()).add(satellite);
                else if (satellite.getType().equals("NasaSatellite")) {
                    if ((satellite.getActiveConnections().size() == 6 && device.getPosition() >= 30 && device.getPosition() <= 40 && satellite.hasActiveConnectionOutside3040()) || satellite.getActiveConnections().size() < 6)
                        sortedList.get(satellite.getType()).add(satellite);
                }
            }
        }
        return sortedList;
    }
    
    // Connects device and satellite
    public SatelliteConnection connectDeviceToSatellite(Device device, Satellite satellite) {
        // Gotta do some stuff for Soviet and Nasa cos they drop connections to connect to new
        if (satellite.getType().equals("SovietSatellite") && satellite.getActiveConnections().size() == 9) {
            satellite.getActiveConnections().get(0).terminateConnection(worldState.getTime());
        } else if (satellite.getType().equals("NasaSatellite") && satellite.getActiveConnections().size() == 6) {
            for (SatelliteConnection c : satellite.getActiveConnections()) {
                if (c.deviceOutside3040()) {
                    c.terminateConnection(worldState.getTime());
                    break;
                }
            }
        }
        device.setConnection(true);
        SatelliteConnection connection = new SatelliteConnection(device, satellite, worldState.getTime());
        satellite.addConnection(connection);
        return connection;
    }
}
