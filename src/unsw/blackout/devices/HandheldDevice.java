package unsw.blackout.devices;

import java.time.LocalTime;
import unsw.blackout.Device;

public class HandheldDevice extends Device {
    
    private int connectTime = 1;

    // Constructor
    public HandheldDevice(LocalTime startTime, LocalTime endTime, String id, int position) {
        super(startTime, endTime, id, position, "HandheldDevice");
    }

    // Getters
    public int getConnectTime() { return connectTime; } 
}