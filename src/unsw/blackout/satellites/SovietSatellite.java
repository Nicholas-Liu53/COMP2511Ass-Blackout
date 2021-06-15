package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class SovietSatellite extends Satellite {

    private String unacceptableDevice = "HandheldDevice";
    private int    deviceLimit        = 9;
    private int    regionRangeMin     = 140;
    private int    regionRangeMax     = 190;

    // Constructor
    public SovietSatellite(int height, String id, float position) {
        super(height, id, position, "SovietSatellite", 6000);
    }
    
    // Getters
    public String getUnacceptableDevice() { return unacceptableDevice; }
    public int    deviceLimit()           { return deviceLimit; }
    public int    getRegionMin()          { return regionRangeMin; }
    public int    getRegionMax()          { return regionRangeMax; }
}