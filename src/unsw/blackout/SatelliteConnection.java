package unsw.blackout;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class SatelliteConnection {

    private Device    device;
    private LocalTime endTime;
    private int       minutesActive;
    private Satellite satellite;
    private LocalTime startTime;

    // Constructor
    public SatelliteConnection(Device device, Satellite satellite, LocalTime startTime) {
        this.device = device;
        this.satellite = satellite;
        this.startTime = startTime;
    }
    
    // Getters
    public Device    getDevice()        { return device; }
    public String    getDeviceId()      { return device.getId(); }
    public LocalTime getEndTime()       { return endTime; }
    public int       getMinutesActive() { return minutesActive; }
    public Satellite getSatellite()     { return satellite; }
    public String    getSatelliteId()   { return satellite.getId(); }
    public LocalTime getStartTime()     { return startTime; }

    // Setters
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public void updateMinutesActive(LocalTime currentTime) {
        try {
            minutesActive = (int) startTime.until(endTime, ChronoUnit.MINUTES) - 1;
            if (satellite.getType().equals("NasaSatellite")) minutesActive -= 10;
            else if (satellite.getType().equals("SovietSatellite")) minutesActive -= 2 * device.getTimeToConnect();
            else if (satellite.getType().equals("BlueOriginSatellite")) minutesActive -= device.getTimeToConnect();
        } catch (Exception e) {
            minutesActive = (int) startTime.until(currentTime, ChronoUnit.MINUTES);
            if (satellite.getType().equals("NasaSatellite")) minutesActive -= 10;
            else if (satellite.getType().equals("SovietSatellite")) minutesActive -= 2 * device.getTimeToConnect();
            else if (satellite.getType().equals("BlueOriginSatellite")) minutesActive -= device.getTimeToConnect();
        }
    }
    public void terminateConnection(LocalTime currentTime) {
        // System.out.println("[" + device.getId() + ", " + satellite.getId() + "]");
        device.minusOneToNumberOfConnections();
        if (device.getNumberOfActiveConnections() == 0)
            device.setConnection(false);
        setEndTime(currentTime);
        satellite.removeActiveConnection(this);
    }

    // HelperFunctions 
    public boolean deviceOutside3040() {
        return getDevice().getPosition() < 30 && getDevice().getPosition() > 40;
    }
}