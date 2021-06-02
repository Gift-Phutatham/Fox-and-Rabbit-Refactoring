package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Hunter extends Actor {

    /**
     * Look for animals adjacent to the current location.
     *
     * @return Where animal was found, or null if it wasn't.
     */
    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if (organism instanceof Animal) {
                Animal animal = (Animal) organism;
                if (animal.isAlive()) {
                    animal.setDead();
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    protected void act(List<Actor> newActor) {
        Location newLocation = moveToNewLocation();
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }

    @Override
    protected Location moveToNewLocation() {
        Location newLocation = findPrey();
        if (newLocation == null) {
            newLocation = field.freeAdjacentLocation(location);
        }
        return newLocation;
    }
}
