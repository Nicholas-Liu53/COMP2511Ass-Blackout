package unsw.blackout;

import java.time.LocalTime;
import java.util.HashMap;

public class Device {

    private HashMap<String, LocalTime> activationPeriods;
    private String                     id;
    private boolean                    isConnected = False;
    private int                        position;
    private String                     type;

    // Constructor
    public Device(Localtime startTime, LocalTime endTime, String id, int position, String type) {
        this.activationPeriods = new HashMap<String, LocalTime>();
        this.activationPeriods.put("startTime", startTime);
        this.activationPeriods.put("endTime", endTime);
        this.id = id;
        this.position = position;
        this.type = type;
    }
    
    // Getters
    public HashMap<String, LocalTime> getActivationPeriods() { return activationPeriods; }
    public String                     getId()                { return id; }
    public boolean                    isConnected()          { return isConnected; }
    public int                        getPosition()          { return position; }
    public String                     getType()              { return type; }
}