package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import unsw.blackout.Device;
import unsw.blackout.Satellite;
import unsw.blackout.Blackout;

public class WorldState {

    private LocalTime currentTime;
    private ArrayList<Satellite> satellites;
    private ArrayList<Device> devices;

    // Constructor
    public WorldState(Localtime currentTime) {
        this.currentTime = currentTime;
        this.satellites  = new ArrayList<Satellite>();
        this.devices     = new ArrayList<Device>();
    }

    // Getter
    public LocalTime getTime() { return currentTime; }
    public Device getDevice(String id) {
        for (Device device : devices)
            if (device.getId().equals(id)) return device;
        return false; 
    }
    public Satellite getSatellite(String id) {
        for (Satellite satellite : satellites)
            if (satellite.getId().equals(id)) return satellite;
        return false; 
    }
    public ArrayList<Device>    getDevices()    { return devices; }
    public ArrayList<Satellite> getSatellites() { return satellites; }
}