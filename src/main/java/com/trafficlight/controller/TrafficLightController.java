package com.trafficlight.controller;

import com.trafficlight.context.TrafficLight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Coordinates and manages a collection of {@link TrafficLight} instances.
 * <p>
 * This controller provides operations to register and remove traffic lights, advance state
 * transitions either globally or per location, and aggregate runtime information such as total
 * completed cycles.
 */
public class TrafficLightController {

    private final List<TrafficLight> trafficLights;

    /**
     * Creates a new controller with an empty traffic light collection.
     */
    public TrafficLightController() {
        this.trafficLights = new ArrayList<>();
    }

    /**
     * Adds the provided traffic light to the managed collection.
     *
     * @param light the traffic light to add
     * @throws IllegalArgumentException if {@code light} is {@code null}
     */
    public void addTrafficLight(TrafficLight light) {
        if (light == null) {
            throw new IllegalArgumentException("light must not be null");
        }

        trafficLights.add(light);
    }

    /**
     * Removes the traffic light whose location matches the provided location name.
     * <p>
     * If no traffic light matches, the collection remains unchanged.
     *
     * @param location the location name to match
     * @throws IllegalArgumentException if {@code location} is {@code null} or blank
     */
    public void removeTrafficLight(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location must not be null or blank");
        }

        trafficLights.stream()
                .filter(light -> light.getLocation().equalsIgnoreCase(location))
                .findFirst()
                .ifPresent(trafficLights::remove);
    }

    /**
     * Advances every managed traffic light to its next state.
     */
    public void advanceAll() {
        trafficLights.forEach(TrafficLight::changeToNext);
    }

    /**
     * Advances the traffic light at the specified location to its next state.
     *
     * @param location the location name to match
     * @throws IllegalArgumentException if no traffic light matches the provided location
     */
    public void advanceOne(String location) {
        getTrafficLight(location).changeToNext();
    }

    /**
     * Returns the traffic light whose location matches the provided location name.
     *
     * @param location the location name to match
     * @return the matching traffic light
     * @throws IllegalArgumentException if {@code location} is {@code null} or blank
     * @throws IllegalArgumentException if no traffic light matches the provided location
     */
    public TrafficLight getTrafficLight(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location must not be null or blank");
        }

        return trafficLights.stream()
                .filter(light -> light.getLocation().equalsIgnoreCase(location))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Traffic light not found for location: " + location));
    }

    /**
     * Prints the current status of every managed traffic light.
     */
    public void printAllStatus() {
        trafficLights.forEach(TrafficLight::printStatus);
    }

    /**
     * Returns the total number of completed cycles across all managed traffic lights.
     *
     * @return the sum of cycle counts
     */
    public int getTotalCycles() {
        return trafficLights.stream()
                .mapToInt(TrafficLight::getCycleCount)
                .sum();
    }

    /**
     * Returns an unmodifiable snapshot of the managed traffic lights.
     *
     * @return an unmodifiable list containing the currently managed traffic lights
     */
    public List<TrafficLight> getTrafficLights() {
        return Collections.unmodifiableList(new ArrayList<>(trafficLights));
    }
}
