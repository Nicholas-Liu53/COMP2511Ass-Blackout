package unsw.blackout;

// import java.time.LocalTime;
import java.util.ArrayList;

public class Satellite {

    protected double                         height;
    private   String                         id;
    protected double                         position;
    private   ArrayList<SatelliteConnection> connections;
    protected ArrayList<SatelliteConnection> activeConnections;
    private   String                         type;
    protected double                         velocity;

    // Constructor
    public Satellite(double height, String id, double position, String type, double velocity) {
        this.height = height;
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

    // Setters
    public void newPosOneMinLater() {
        position = (position + velocity / height) % 360;
    }
    public void addConnection(SatelliteConnection connection) {
        connections.add(connection);
        activeConnections.add(connection);
    }
    public void removeActiveConnection(SatelliteConnection connection) {
        activeConnections.remove(connection);
    }
    public int getLaptopConnections() {
        int count = 0;
        for (SatelliteConnection c : activeConnections) {
            if (c.getDevice().getType().equals("LaptopDevice")) count++;
        }
        return count;
    }
    public int getDesktopConnections() {
        int count = 0;
        for (SatelliteConnection c : activeConnections) {
            if (c.getDevice().getType().equals("DesktopDevice")) count++;
        }
        return count;
    }
    
    // Helper Functions
    public boolean hasActiveConnectionOutside3040() {
        for (SatelliteConnection c : activeConnections) {
            if (c.getDevice().getPosition() < 30 || c.getDevice().getPosition() > 40)
                return true;
        }
        return false;
    }
}