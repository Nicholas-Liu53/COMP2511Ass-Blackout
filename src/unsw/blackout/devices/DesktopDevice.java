package unsw.blackout.devices;

import java.time.LocalTime;
import unsw.blackout.Device;

public class DesktopDevice extends Device {
    
    private int connectTime = 5;

    // Constructor
    public DesktopDevice(String id, double position) {
        super(id, position, "DesktopDevice");
    }

    // Getters
    public int getConnectTime() { return connectTime; } 
}