package io.muic.ooc.fab;

import java.util.List;

public abstract class Actor {
    private boolean alive;
    protected Field field;
    protected Location location;

    protected void initialize(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);
        setAlive(true);
    }

    protected boolean isAlive() {
        return alive;
    }

    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    protected abstract Location moveToNewLocation();

    protected abstract void act(List<Actor> newActor);
}
