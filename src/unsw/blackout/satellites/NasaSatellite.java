package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class NasaSatellite extends Satellite {

    private int connectTime    = 10;
    private int deviceLimit    = 6;
    private int regionRangeMin = 30;
    private int regionRangeMax = 40;

    // Constructor
    public NasaSatellite(double height, String id, double position) {
        super(height, id, position, "NasaSatellite", 85);
    }
    
    // Getters
    public int getConnectTime() { return connectTime; }
    public int deviceLimit()    { return deviceLimit; }
    public int getRegionMin()   { return regionRangeMin; }
    public int getRegionMax()   { return regionRangeMax; }
}