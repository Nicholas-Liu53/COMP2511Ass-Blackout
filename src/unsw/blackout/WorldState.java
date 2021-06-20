package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;

public class WorldState {

    private LocalTime currentTime;
    private ArrayList<Satellite> satellites;
    private ArrayList<Device> devices;

    // Constructor
    public WorldState(LocalTime currentTime) {
        this.currentTime = currentTime;
        this.satellites  = new ArrayList<Satellite>();
        this.devices     = new ArrayList<Device>();
    }

    // Getters
    public LocalTime            getTime()       { return currentTime; }
    public ArrayList<Device>    getDevices()    { return devices; }
    public ArrayList<Satellite> getSatellites() { return satellites; }

    // Setters
    public void nextMinute() {
        currentTime = currentTime.plusMinutes(1);
    }
    public void addDevice(Device device) {
        devices.add(device);
    }
    public void addSatellite(Satellite satellite) {
        satellites.add(satellite);
    }
    public void removeDevice(Device device) {
        devices.remove(device);
    }
    public void removeSatellite(Satellite satellite) {
        satellites.remove(satellite);
    }
}