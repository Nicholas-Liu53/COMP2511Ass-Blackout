package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class BlueOriginSatellite extends Satellite {

    private int deviceLimit  = 10;
    private int laptopLimit  = 5;
    private int desktopLimit = 2;

    // Constructor
    public BlueOriginSatellite(double height, String id, double position) {
        super(height, id, position, "BlueOriginSatellite", 141.66);
    }

    // Getters
    public int getDeviceLimit()  { return deviceLimit; }
    public int getLaptopLimit()  { return laptopLimit; }
    public int getDesktopLimit() { return desktopLimit; }
}