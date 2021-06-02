package io.muic.ooc.fab;

public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (class variables).

    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;

    private static final int RABBIT_FOOD_VALUE = 9;

    protected int getRabbitFoodValue() {
        return RABBIT_FOOD_VALUE;
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
        return field.freeAdjacentLocation(location);
    }
}
