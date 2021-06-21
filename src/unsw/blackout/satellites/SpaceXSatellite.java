package unsw.blackout.satellites;

// import java.time.LocalTime;
import unsw.blackout.Satellite;

public class SpaceXSatellite extends Satellite {

    // Constructor
    public SpaceXSatellite(double height, String id, double position) {
        super(height, id, position, "SpaceXSatellite", 55.5);
    }
    
}