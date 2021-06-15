package unsw.blackout;

import java.time.LocalTime;

public class WorldState {

    private LocalTime currentTime;

    public WorldState(Localtime currentTime) {
        this.currentTime = currentTime;
    }

    public LocalTime getTime() {
        return currentTime;
    }
}