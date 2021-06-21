package unsw.blackout.devices;

import unsw.blackout.Device;

public class AWSCloudServer extends Device {

    // Constructor
    public AWSCloudServer(String id, double position) {
        super(id, position, "AWSCloudServer", 5);
    }

}