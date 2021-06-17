package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class SpaceXSatellite extends Satellite {

    private String acceptableDevice = "HandheldDevice";

    // Constructor
    public SpaceXSatellite(double height, String id, double position) {
        super(height, id, position, "SpaceXSatellite", 55.5);
    }
    
    // Getters
    public String getAcceptableDevice() { return acceptableDevice; }
}