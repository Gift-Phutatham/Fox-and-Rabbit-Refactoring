package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Fox extends Animal {
    // Characteristics shared by all foxes (class variables).

    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // Random generator
    private static final Random RANDOM = new Random();

    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;
    private static final int FOX_FOOD_VALUE = 9;

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    @Override
    protected void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        foodLevel = RANDOM.nextInt(9);
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newActor A list to return newly born foxes.
     */
    @Override
    protected void act(List<Actor> newActor) {
        incrementHunger();
        super.act(newActor);
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if (organism instanceof Rabbit) {
                Animal animal = (Animal) organism;
                if (animal.isAlive()) {
                    animal.setDead();
                    foodLevel = animal.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected Location moveToNewLocation() {
        Location newLocation = findPrey();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        return newLocation;
    }

    @Override
    protected int getFoodValue() {
        return FOX_FOOD_VALUE;
    }
}
