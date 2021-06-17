package unsw.blackout;

import java.time.LocalTime;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.blackout.MathsHelper;
import unsw.blackout.WorldState;
import unsw.blackout.devices.*;
import unsw.blackout.satellites.*;
import unsw.blackout.Device;
import unsw.blackout.Satellite;

public class Blackout {

    private WorldState worldState;

    // Constructor
    public Blackout() {
        worldState = new WorldState(LocalTime.of(0, 0));
    }

    public void createDevice(String id, String type, double position) {
        if (type.equals("HandheldDevice"))    new HandheldDevice(id, position);
        else if (type.equals("LaptopDevice")) new LaptopDevice(id, position);
        else                                  new DesktopDevice(id, position);
    }

    public void createSatellite(String id, String type, double height, double position) {
        if (type.equals("SpaceXSatellite"))          new SpaceXSatellite(height, id, position);
        else if (type.equals("BlueOriginSatellite")) new BlueOriginSatellite(height, id, position);
        else if (type.equals("NasaSatellite"))       new NasaSatellite(height, id, position);
        else                                         new SovietSatellite(height, id, position);
    }

    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        worldState.getDevice(deviceId).setActivationPeriods(start, start.plusMinutes(durationInMinutes));
    }

    public void removeSatellite(String id) {
        worldState.getSatellite(id).remove();
    }

    public void removeDevice(String id) {
        worldState.getDevice(id).remove();
    }

    public void moveDevice(String id, double newPos) {
        worldState.getDevice(id).setPosition(newPos);
    }

    public JSONObject showWorldState() {
        JSONObject result = new JSONObject();
        JSONArray devices = new JSONArray();
        JSONArray satellites = new JSONArray();

        // "currentTime" : <LocalTime>,
        result.put("currentTime", worldState.getTime()); 

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
                connections.add(satelliteConnection);                            // }
            }                                                               
            map.put("connections", connections);                            // ],
            map.put("height", satellite.getHeight());                       // "height" :
            map.put("id", satellite.getId());                               // "id" :
            map.put("position", satellite.getPosition());                   // "position" :
            JSONArray possibleConnList = new JSONArray();                   // "possibleConnections" : [
            for (Device device : worldState.getDevices()) {              
                if (MathsHelper.satelliteIsVisibleFromDevice(satellite.getPosition(), satellite.getHeight(), device.getPosition())) {
                    if (satellite.getType().equals("SpaceXSatellite") && device.getType().equals("HandHeldDevice"))
                        possibleConnList.add(device.getId());
                    else if (satellite.getType().equals("BlueOriginSatellite") || satellite.getType().equals("NasaSatellite")) 
                        possibleConnList.add(device.getId());
                    else if (satellite.getType().equals("SovietSatellite") && !(device.getType().equals("HandHeldDevice")))
                        possibleConnList.add(device.getId());
                }
            }                                                               
            map.put("possibleConnections", possibleConnList);               // ],
            map.put("type", satellite.getType());                           // "type" : 
            map.put("velocity", satellite.getVelocity());                   // "velocity" :
            satellite.put(map);                                  // }
        } // ]

        result.put("devices", devices);
        result.put("satellites", satellites);

        // TODO: you'll want to replace this for Task2
        result.put("currentTime", LocalTime.of(0, 0));

        return result;
    }

    public void simulate(int tickDurationInMinutes) {
        // TODO:
    }
}
