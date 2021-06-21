package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class NasaSatellite extends Satellite {

    // Constructor
    public NasaSatellite(double height, String id, double position) {
        super(height, id, position, "NasaSatellite", 85);
    }

}