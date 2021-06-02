package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Animal {
    // Characteristics shared by all tigers (class variables).

    // The age to which a tiger can live.
    private static final int MAX_AGE = 200;
    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 30;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.02;
    // Random generator
    private static final Random RANDOM = new Random();

    // The tiger's food level, which is increased by eating rabbits or foxes.
    private int foodLevel;

    /**
     * Create a tiger. A tiger can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    @Override
    protected void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        foodLevel = RANDOM.nextInt(9) + RANDOM.nextInt(13);
    }

    /**
     * This is what the tiger does most of the time: it hunts for rabbits and foxes. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newActor A list to return newly born tigers.
     */
    @Override
    protected void act(List<Actor> newActor) {
        incrementHunger();
        super.act(newActor);
    }

    /**
     * Make this tiger more hungry. This could result in the tiger's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits or foxes adjacent to the current location. Only the first live
     * rabbit or fox is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = rabbit.getRabbitFoodValue();
                    return where;
                }
            } else if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = fox.getFoxFoodValue();
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
}
