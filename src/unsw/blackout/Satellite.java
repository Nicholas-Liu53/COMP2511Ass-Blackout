package unsw.blackout;

// import java.time.LocalTime;
import java.util.ArrayList;

import unsw.blackout.SatelliteConnection;

public class Satellite {

    private double                         height;
    private String                         id;
    private double                         position;
    private ArrayList<SatelliteConnection> connections;
    private ArrayList<SatelliteConnection> activeConnections;
    private String                         type;
    private double                         velocity;

    // Constructor
    public Satellite(int height, String id, double position, String type, double velocity) {
        this.height = height;
        this.id = id;
        this.position = position;
        this.type = type;
        this.velocity = velocity;
    }
    
    // Getters
    public int                            getHeight()   { return height; }
    public String                         getId()       { return id; }
    public double                         getPosition() { return position; }
    public String                         getType()     { return type; }
    public double                         getVelocity() { return velocity; }
    public ArrayList<SatelliteConnection> getConnections() { return connections; }
}