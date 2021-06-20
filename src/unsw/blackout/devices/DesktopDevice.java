package unsw.blackout.devices;

import unsw.blackout.Device;

public class DesktopDevice extends Device {
    
    private int connectTime = 5;

    // Constructor
    public DesktopDevice(String id, double position) {
        super(id, position, "DesktopDevice", 5);
    }

    // Getters
    public int getConnectTime() { return connectTime; } 
}