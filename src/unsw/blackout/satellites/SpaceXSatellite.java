package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class SpaceXSatellite extends Satellite {

    private String acceptableDevice = "HandheldDevice";

    // Constructor
    public SpaceXSatellite(int height, String id, float position) {
        super(height, id, position, "SpaceXSatellite", 3330);
    }
    
    // Getters
    public String getAcceptableDevice() { return acceptableDevice; }
}