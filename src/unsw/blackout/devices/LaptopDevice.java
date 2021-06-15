package unsw.blackout.devices;

import java.time.LocalTime;
import unsw.blackout.Device;

public class LaptopDevice extends Device {
    
    private int connectTime = 2;

    // Constructor
    public LaptopDevice(LocalTime startTime, LocalTime endTime, String id, int position) {
        super(startTime, endTime, id, position, "LaptopDevice");
    }

    // Getters
    public int getConnectTime() { return connectTime; } 
}