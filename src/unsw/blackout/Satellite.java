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
    public Satellite(double height2, String id, double position, String type, double velocity) {
        this.height = height2;
        this.id = id;
        this.position = position;
        this.type = type;
        this.velocity = velocity;
        connections = new ArrayList<SatelliteConnection>();
        activeConnections = new ArrayList<SatelliteConnection>();
    }
    
    // Getters
    public double                         getHeight()            { return height; }
    public String                         getId()                { return id; }
    public double                         getPosition()          { return position; }
    public String                         getType()              { return type; }
    public double                         getVelocity()          { return velocity; }
    public ArrayList<SatelliteConnection> getConnections()       { return connections; }
    public ArrayList<SatelliteConnection> getActiveConnections() { return activeConnections; }
}