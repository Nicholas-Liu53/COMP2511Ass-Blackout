package unsw.blackout;

// import java.time.LocalTime;
import unsw.blackout.SatelliteConnection;

public class Satellite {

    private int                   height;
    private String                id;
    private float                 position;
    private SatelliteConnection[] possibleConnections;
    private String                type;
    private float                 velocity;

    // Constructor
    public Satellite(int height, String id, float position, String type, float velocity) {
        this.height = height;
        this.id = id;
        this.position = position;
        this.type = type;
        this.velocity = velocity;
    }
    
    // Getters
    public int    getHeight()   { return height; }
    public String getId()       { return id; }
    public float  getPosition() { return position; }
    public String getType()     { return type; }
    public float  getVelocity() { return velocity; }
}