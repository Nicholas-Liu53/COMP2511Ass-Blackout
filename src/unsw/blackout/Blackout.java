package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;

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
        if (type.equals("HandheldDevice"))      
            worldState.addDevice(new HandheldDevice(id, position));

        else if (type.equals("LaptopDevice"))   
            worldState.addDevice(new LaptopDevice(id, position));

        else if (type.equals("MobileXDevice"))  
            worldState.addDevice(new MobileXDevice(id, position));

        else if (type.equals("AWSCloudServer")) 
            worldState.addDevice(new AWSCloudServer(id, position));

        else                                    
            worldState.addDevice(new DesktopDevice(id, position));
        worldState.sortDevices();
    }

    public void createSatellite(String id, String type, double height, double position) {
        if (type.equals("SpaceXSatellite"))          
            worldState.addSatellite(new SpaceXSatellite(height, id, position));
        else if (type.equals("BlueOriginSatellite")) 
            worldState.addSatellite(new BlueOriginSatellite(height, id, position));
        else if (type.equals("NasaSatellite"))       
            worldState.addSatellite(new NasaSatellite(height, id, position));
        else                                         
            worldState.addSatellite(new SovietSatellite(height, id, position));
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
        //? Note that print WorldState satellites by alphabetical order
        worldState.sortSatellitesByAlphabet();
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
        
            //* Update satellite positions
            for (Satellite satellite : worldState.getSatellites()) 
                satellite.newPosOneMinLater();

            //* Update connections
            // Sort out the satellites by angle (smaller angle high priority)
            worldState.sortSatellitesByAngle(); 
            // Loop through devices
            for (Device device : worldState.getDevices()) {
                // If device is connected --> see any connections to close
                if (device.isConnected()) {
                    // Get a list of connections
                    ArrayList<SatelliteConnection> connections = findActiveConnectionsByDevice(device);
                    // Loop through connections
                    for (SatelliteConnection connection : connections) {
                        // Get the satellite that's connected to the device
                        Satellite connectedSatellite = connection.getSatellite();
                        // If the device is no longer in activation period or satellite is no longer visible
                        if (
                            !(device.inActivationPeriod(worldState.getTime())) || 
                            !(MathsHelper.satelliteIsVisibleFromDevice(connectedSatellite.getPosition(), connectedSatellite.getHeight(), device.getPosition()))
                        ) {
                            // Disconnect it
                            connection.terminateConnection(worldState.getTime());
                        }
                        // Update minutesActive for that connection
                        connection.updateMinutesActive(worldState.getTime());
                    }
                } 
                // If device is in activation period
                if (device.inActivationPeriod(worldState.getTime())) {
                    // Get a list of available satellites (priority considered)
                    ArrayList<Satellite> satelliteList = findSatellitesForDevice(device);
                    // If AWS Cloud Server
                    if (device.getType().equals("AWSCloudServer")) {
                        // If there's no connections but 2 available satellites
                        if (device.getNumberOfActiveConnections() == 0 && satelliteList.size() >= 2) {
                            // Connect both
                            SatelliteConnection connection1 = connectDeviceToSatellite(device, satelliteList.get(0));
                            SatelliteConnection connection2 = connectDeviceToSatellite(device, satelliteList.get(1));
                            connection1.updateMinutesActive(worldState.getTime());
                            connection2.updateMinutesActive(worldState.getTime());
                        } else if (device.getNumberOfActiveConnections() == 1) {
                            // If there's only 1 connection
                            // if there are other satellites available
                            if (!satelliteList.isEmpty()) {
                                // Connect it
                                SatelliteConnection connection = connectDeviceToSatellite(device, satelliteList.get(0));
                                connection.updateMinutesActive(worldState.getTime());
                            } else {
                                // Otherwise disconnect the remaining connection
                                ArrayList<SatelliteConnection> connections = findActiveConnectionsByDevice(device);
                                for (SatelliteConnection connection : connections) {
                                    connection.terminateConnection(worldState.getTime());
                                    connection.updateMinutesActive(worldState.getTime());
                                }
                            }
                        }
                    } else if (!device.isConnected()) {
                        // If not AWS and is not connected
                        // If no available satellites, onto the next device
                        if (satelliteList.isEmpty()) continue;
                        // If it is a Mobile X Device --> Priority is Space X Satellite
                        if (device.getType().equals("MobileXDevice")) {
                            ArrayList<Satellite> spaceXList = findSpaceX(satelliteList);
                            // If there is a Space X Satellite available
                            if (!spaceXList.isEmpty()) {
                                SatelliteConnection connection = connectDeviceToSatellite(device, spaceXList.get(0));
                                connection.updateMinutesActive(worldState.getTime());
                                continue;
                            }
                        }
                        // Otherwise connect to what's available
                        SatelliteConnection connection = connectDeviceToSatellite(device, satelliteList.get(0));
                        connection.updateMinutesActive(worldState.getTime());
                    }
                }
            }

            //* It is now the next minute
            worldState.nextMinute();
        } 
    }

    //* Helper Functions
    // Finds the active connection of a device
    public ArrayList<SatelliteConnection> findActiveConnectionsByDevice(Device device) {
        ArrayList<SatelliteConnection> l = new ArrayList<SatelliteConnection>();
        for (Satellite satellite : worldState.getSatellites()) {
            for (SatelliteConnection connection : satellite.getActiveConnections()) {
                if (connection.getDeviceId().equals(device.getId())) 
                l.add(connection);
            }
        }
        return l;
    }

    // Finds available satellites for a device and sorts them by type
    public ArrayList<Satellite> findSatellitesForDevice(Device device) throws Exception {
        ArrayList<Satellite> list = new ArrayList<Satellite>();
        for (Satellite satellite : worldState.getSatellites()) {
            if (hasExistingConnection(device, satellite)) continue;
            if (MathsHelper.satelliteIsVisibleFromDevice(satellite.getPosition(), satellite.getHeight(), device.getPosition())) {
                if (satellite.getType().equals("SpaceXSatellite") && device.getType().equals("HandheldDevice"))
                    list.add(satellite);
                else if (satellite.getType().equals("BlueOriginSatellite")) {
                    if (satellite.getActiveConnections().size() < 11) {
                        if (device.getType().equals("HandheldDevice"))
                            list.add(satellite);
                        else if (device.getType().equals("LaptopDevice") && satellite.getLaptopConnections() < 6)
                            list.add(satellite);
                        else if (device.getType().equals("DesktopDevice") && satellite.getDesktopConnections() < 3)
                            list.add(satellite);
                    }
                } else if (satellite.getType().equals("SovietSatellite") && !device.getType().equals("HandheldDevice"))
                    list.add(satellite);
                else if (satellite.getType().equals("NasaSatellite")) {
                    if ((satellite.getActiveConnections().size() == 6 && device.getPosition() >= 30 && device.getPosition() <= 40 && satellite.hasActiveConnectionOutside3040()) || satellite.getActiveConnections().size() < 6)
                        list.add(satellite);
                }
            }
        }
        return list;
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
        // System.out.println("[" + device.getId() + ", " + satellite.getId() + "]");
        device.setConnection(true);
        device.addOneToNumberOfConnections();
        SatelliteConnection connection = new SatelliteConnection(device, satellite, worldState.getTime());
        satellite.addConnection(connection);
        return connection;
    }

    // Returns an array list containing a SpaceXSatellite
    public ArrayList<Satellite> findSpaceX(ArrayList<Satellite> satellitesInRange) {
        ArrayList<Satellite> list = new ArrayList<Satellite>();
        for (Satellite s : satellitesInRange) {
            if (s.getType().equals("SpaceXSatellite")) 
                list.add(s);
        }
        return list;
    }
    // Determines whether a device and a satellite has an existing connection
    public boolean hasExistingConnection(Device d, Satellite s) throws Exception {
        ArrayList<SatelliteConnection> connection = findActiveConnectionsByDevice(d);
        return !connection.isEmpty();
    }
}
