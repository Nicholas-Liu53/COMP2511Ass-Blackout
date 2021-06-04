package unsw.blackout;

import java.time.LocalTime;

import org.json.JSONArray;
import org.json.JSONObject;

public class Blackout {
    
    private String id;
    private String type;
    private double position;
    private double height = 3000;

    public void createDevice(String id, String type, double position) {
        this.id = id;
        this.type = type;
        this.position = position;
    }

    public void createSatellite(String id, String type, double height, double position) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.height = height;
    }

    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        // TODO:
    }

    public void removeSatellite(String id) {
        // TODO:
    }

    public void removeDevice(String id) {
        // TODO:
    }

    public void moveDevice(String id, double newPos) {
        // TODO:
    }

    public JSONObject showWorldState() {
        JSONObject result = new JSONObject();
        JSONArray devices = new JSONArray();
        JSONArray satellites = new JSONArray();

        // TODO:

        result.put("devices", devices);
        result.put("satellites", satellites);

        // TODO: you'll want to replace this for Task2
        result.put("currentTime", LocalTime.of(0, 0));

        return result;
    }

    public void simulate(int tickDurationInMinutes) {
        // TODO:
    }
}
