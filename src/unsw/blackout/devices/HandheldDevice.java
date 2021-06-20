package unsw.blackout.devices;

import unsw.blackout.Device;

public class HandheldDevice extends Device {
    
    private int connectTime = 1;

    // Constructor
    public HandheldDevice(String id, double position) {
        super(id, position, "HandheldDevice", 1);
    }

    // Getters
    public int getConnectTime() { return connectTime; } 
}