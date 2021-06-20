package unsw.blackout;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.ArrayList;

public class Device {

    private ArrayList<HashMap<String, LocalTime>> activationPeriods;
    private String                                id;
    private boolean                               isConnected = false;
    private double                                position;
    private String                                type;
    private int                                   timeToConnect;

    // Constructor
    public Device(String id, double position, String type, int timeToConnect) {
        this.activationPeriods = new ArrayList<HashMap<String, LocalTime>>();
        this.id = id;
        this.position = position;
        this.type = type;
        this.timeToConnect = timeToConnect;
    }
    
    // Getters
    public ArrayList<HashMap<String, LocalTime>> getActivationPeriods() { return activationPeriods; }
    public String                                getId()                { return id; }
    public boolean                               isConnected()          { return isConnected; }
    public double                                getPosition()          { return position; }
    public String                                getType()              { return type; }
    public int                                   getTimeToConnect()     { return timeToConnect; }
    
    public boolean inActivationPeriod(LocalTime time) {
        for (HashMap<String, LocalTime> activationPeriod : activationPeriods) {
            if (
                (time.isAfter(activationPeriod.get("startTime")) || time.equals(activationPeriod.get("startTime")))
                && 
                (time.isBefore(activationPeriod.get("endTime")) || time.equals(activationPeriod.get("endTime")))
            ) return true;
        }
        return false;
    }
    
    // Setters 
    public void setActivationPeriods(LocalTime startTime, LocalTime endTime) {
        HashMap<String, LocalTime> newPeriod = new HashMap<String, LocalTime>();
        newPeriod.put("startTime", startTime);
        newPeriod.put("endTime", endTime);
        activationPeriods.add(newPeriod);
    }
    public void setPosition(double newPos) {
        position = newPos;
    }
    public void setConnection(boolean bool) {
        isConnected = bool;
    }
}