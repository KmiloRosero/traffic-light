package com.trafficlight.states;

import com.trafficlight.context.TrafficLight;
import com.trafficlight.state.TrafficLightState;

/**
 * Represents the red state of a traffic light.
 * <p>
 * In this state, vehicles must stop and pedestrians are allowed to cross.
 */
public class RedState implements TrafficLightState {

    private static final String COLOR_HEX = "#FF0000";

    /**
     * Transitions the traffic light to the next state, which is {@link GreenState}.
     *
     * @param context the traffic light context to transition
     * @throws IllegalArgumentException if {@code context} is {@code null}
     */
    @Override
    public void changeToNext(TrafficLight context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        context.setState(new GreenState());
    }

    /**
     * Returns the name of the color for this state.
     *
     * @return "RED"
     */
    @Override
    public String getColorName() {
        return "RED";
    }

    /**
     * Returns the number of seconds this state remains active.
     *
     * @return {@code 30}
     */
    @Override
    public int getDurationSeconds() {
        return 30;
    }

    /**
     * Returns a concise description of what this state means.
     *
     * @return a user-facing description of the red state
     */
    @Override
    public String getDescription() {
        return "STOP - Vehicles must stop completely";
    }

    /**
     * Returns a human-readable description of what actions are permitted in this state.
     *
     * @return allowed actions while the light is red
     */
    @Override
    public String getAllowedActions() {
        return "Pedestrians may cross";
    }

    /**
     * Returns the hex color code associated with this state.
     *
     * @return the hex color code for red
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
