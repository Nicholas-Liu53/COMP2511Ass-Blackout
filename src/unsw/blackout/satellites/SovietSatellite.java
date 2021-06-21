package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class SovietSatellite extends Satellite {

    private String unacceptableDevice = "HandheldDevice";
    private int    deviceLimit        = 9;
    private int    regionRangeMin     = 140;
    private int    regionRangeMax     = 190;
    private int    mostRecentBound;
    private int    speed;

    // Constructor
    public SovietSatellite(double height, String id, double position) {
        super(height, id, position, "SovietSatellite", 100);
        speed = 100;
    }
    
    // Getters
    public String getUnacceptableDevice() { return unacceptableDevice; }
    public int    deviceLimit()           { return deviceLimit; }
    public int    getRegionMin()          { return regionRangeMin; }
    public int    getRegionMax()          { return regionRangeMax; }
    public int    getMostRecentBound()    { return mostRecentBound; }

    // Setters
    @Override
    public void newPosOneMinLater() {
        if (position > 140 && position < 190) {
            if (mostRecentBound == 190) position = (position - speed / height) % 360;
            else position = (position + speed / height) % 360;
        } else {
            if (position <= 345 && position > 190) {
                mostRecentBound = 190;
                position = (position - speed / height) % 360;
            } else {
                mostRecentBound = 140;
                position = (position + speed / height) % 360;
            }
        }
        updateVelocity();
    }
    
    // Helper Functions
    public void updateVelocity() {
        if ((mostRecentBound == 140 && velocity < 0) || (mostRecentBound == 190 && velocity > 0))
            this.velocity *= -1;
    }
}