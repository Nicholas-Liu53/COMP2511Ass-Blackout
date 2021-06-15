package unsw.blackout;

import java.time.LocalTime;

public class SatelliteConnection {

    private String    deviceId;
    private LocalTime endTime;
    private int       minutesActive;
    private String    satelliteId;
    private LocalTime startTime;

    // Constructor
    public SatelliteConnection(String deviceId, LocalTime endTime, int minutesActive, String satelliteId, LocalTime startTime) {
        this.deviceId = deviceId;
        this.endTime = endTime;
        this.minutesActive = minutesActive;
        this.satelliteId = satelliteId;
        this.startTime = startTime;
    }
    
    // Getters
    public String    getDeviceId()      { return deviceId; }
    public LocalTime getEndTime()       { return endTime; }
    public int       getMinutesActive() { return minutesActive; }
    public String    getSatelliteId()   { return satelliteId; }
    public LocalTime getStartTime()     { return startTime; }
}