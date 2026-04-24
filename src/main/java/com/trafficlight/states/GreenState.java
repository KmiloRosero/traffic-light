package com.trafficlight.states;

import com.trafficlight.context.TrafficLight;
import com.trafficlight.state.TrafficLightState;

/**
 * Represents the green state of a traffic light.
 * <p>
 * In this state, vehicles may proceed through the intersection and pedestrians must wait.
 */
public class GreenState implements TrafficLightState {

    private static final String COLOR_HEX = "#00C851";

    /**
     * Transitions the traffic light to the next state, which is {@link YellowState}.
     *
     * @param context the traffic light context to transition
     * @throws IllegalArgumentException if {@code context} is {@code null}
     */
    @Override
    public void changeToNext(TrafficLight context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        context.setState(new YellowState());
    }

    /**
     * Returns the name of the color for this state.
     *
     * @return "GREEN"
     */
    @Override
    public String getColorName() {
        return "GREEN";
    }

    /**
     * Returns the number of seconds this state remains active.
     *
     * @return {@code 25}
     */
    @Override
    public int getDurationSeconds() {
        return 25;
    }

    /**
     * Returns a concise description of what this state means.
     *
     * @return a user-facing description of the green state
     */
    @Override
    public String getDescription() {
        return "GO - Vehicles may proceed through the intersection";
    }

    /**
     * Returns a human-readable description of what actions are permitted in this state.
     *
     * @return allowed actions while the light is green
     */
    @Override
    public String getAllowedActions() {
        return "Vehicles may go. Pedestrians must wait";
    }

    /**
     * Returns the hex color code associated with this state.
     *
     * @return the hex color code for green
     */
    public String getColorHex() {
        return COLOR_HEX;
    }

    /**
     * Returns a formatted representation containing the color name and duration.
     *
     * @return a formatted string representation of this state
     */
    @Override
    public String toString() {
        return String.format("%s (%ds)", getColorName(), getDurationSeconds());
    }
}
