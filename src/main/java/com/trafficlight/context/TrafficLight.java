package com.trafficlight.context;

import com.trafficlight.state.TrafficLightState;
import com.trafficlight.states.RedState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Context class for a traffic light implemented using the State Pattern.
 * <p>
 * The context maintains a reference to the current {@link TrafficLightState} and delegates state
 * transitions to the state instance. It also tracks operational metadata such as the intersection
 * location, completed full cycles, and a history of state changes.
 */
public class TrafficLight {

    private TrafficLightState currentState;
    private final String location;
    private int cycleCount;
    private final List<String> stateHistory;

    /**
     * Creates a new traffic light for the given intersection location.
     * <p>
     * The traffic light starts in {@link RedState}. The cycle count is initialized to {@code 0}, and
     * the state change history starts empty.
     *
     * @param location the intersection name where this traffic light is located
     * @throws IllegalArgumentException if {@code location} is {@code null} or blank
     */
    public TrafficLight(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location must not be null or blank");
        }

        this.location = location;
        this.currentState = new RedState();
        this.cycleCount = 0;
        this.stateHistory = new ArrayList<>();
    }

    /**
     * Sets the current state of this traffic light.
     * <p>
     * This method updates the active state and records the transition in the state history.
     * If the new state is red, the cycle count is incremented to reflect a completed
     * {@code RED→GREEN→YELLOW→RED} cycle.
     *
     * @param state the new state to set
     * @throws IllegalArgumentException if {@code state} is {@code null}
     */
    public void setState(TrafficLightState state) {
        if (state == null) {
            throw new IllegalArgumentException("state must not be null");
        }

        String previousName = currentState == null ? "NONE" : currentState.getColorName();
        String nextName = state.getColorName();

        this.currentState = state;
        this.stateHistory.add(previousName + " -> " + nextName);

        if ("RED".equalsIgnoreCase(nextName)) {
            this.cycleCount++;
        }
    }

    /**
     * Advances this traffic light to its next state.
     * <p>
     * The transition decision is delegated to the current state.
     */
    public void changeToNext() {
        currentState.changeToNext(this);
    }

    /**
     * Returns the current active state.
     *
     * @return the current state
     */
    public TrafficLightState getCurrentState() {
        return currentState;
    }

    /**
     * Returns the intersection location name for this traffic light.
     *
     * @return the location name
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns how many full cycles have been completed.
     * <p>
     * A cycle is counted whenever the light transitions into red.
     *
     * @return the completed cycle count
     */
    public int getCycleCount() {
        return cycleCount;
    }

    /**
     * Returns an unmodifiable copy of the state transition history.
     *
     * @return an unmodifiable list of state transitions
     */
    public List<String> getStateHistory() {
        return Collections.unmodifiableList(new ArrayList<>(stateHistory));
    }

    /**
     * Prints a formatted status box describing the current state of this traffic light.
     * <p>
     * The output includes location, current color, duration, description, allowed actions, and the
     * cycle count.
     */
    public void printStatus() {
        List<String> lines = List.of(
                "Location: " + location,
                "Color: " + currentState.getColorName(),
                "Duration: " + currentState.getDurationSeconds() + " seconds",
                "Description: " + currentState.getDescription(),
                "Allowed: " + currentState.getAllowedActions(),
                "Cycles: " + cycleCount
        );

        int width = 0;
        for (String line : lines) {
            width = Math.max(width, line.length());
        }

        String border = "+" + "-".repeat(width + 2) + "+";
        System.out.println(border);
        for (String line : lines) {
            System.out.println("| " + padRight(line, width) + " |");
        }
        System.out.println(border);
    }

    /**
     * Pads the given string on the right with spaces until it reaches the specified length.
     *
     * @param value the value to pad
     * @param length the target length
     * @return the padded string
     */
    private static String padRight(String value, int length) {
        if (value.length() >= length) {
            return value;
        }
        return value + " ".repeat(length - value.length());
    }
}
