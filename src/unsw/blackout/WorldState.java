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

    // Helper Functions
    // Sorts devices ArrayList into alphabetical order of id
    public void sortDevices() {
        for (int i = 0; i < devices.size(); i++) {
            for (int j = i + 1; j < devices.size(); j++) {
                if (devices.get(i).getId().compareTo(devices.get(j).getId()) > 0) {
                    Device temp = devices.get(i);
                    devices.set(i, devices.get(j));
                    devices.set(j, temp);
                }
            }
        }
    }
    // Sorts out satellite by alphabetical order of id
    public void sortSatellitesByAlphabet() {
        for (int i = 0; i < satellites.size(); i++) {
            for (int j = i + 1; j < satellites.size(); j++) {
                if (satellites.get(i).getId().compareTo(satellites.get(j).getId()) > 0) {
                    Satellite temp = satellites.get(i);
                    satellites.set(i, satellites.get(j));
                    satellites.set(j, temp);
                }
            }
        }
    }
    // Sorts out satellite by position angle size
    public void sortSatellitesByAngle() {
        for (int i = 0; i < satellites.size(); i++) {
            for (int j = i + 1; j < satellites.size(); j++) {
                if (satellites.get(i).getPosition() > satellites.get(j).getPosition()) {
                    Satellite temp = satellites.get(i);
                    satellites.set(i, satellites.get(j));
                    satellites.set(j, temp);
                }
            }
        }
    }
}