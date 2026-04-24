package com.trafficlight.states;

import com.trafficlight.context.TrafficLight;
import com.trafficlight.state.TrafficLightState;

/**
 * Represents the yellow (caution) state of a traffic light.
 * <p>
 * This state warns that the light is about to turn red and drivers should prepare to stop.
 */
public class YellowState implements TrafficLightState {

    private static final String COLOR_HEX = "#FFC300";

    /**
     * Transitions the traffic light to the next state, which is {@link RedState}.
     *
     * @param context the traffic light context to transition
     * @throws IllegalArgumentException if {@code context} is {@code null}
     */
    @Override
    public void changeToNext(TrafficLight context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        context.setState(new RedState());
    }

    /**
     * Returns the name of the color for this state.
     *
     * @return "YELLOW"
     */
    @Override
    public String getColorName() {
        return "YELLOW";
    }

    /**
     * Returns the number of seconds this state remains active.
     *
     * @return {@code 5}
     */
    @Override
    public int getDurationSeconds() {
        return 5;
    }

    /**
     * Returns a concise description of what this state means.
     *
     * @return a user-facing description of the yellow state
     */
    @Override
    public String getDescription() {
        return "CAUTION - Prepare to stop, light is about to turn red";
    }

    /**
     * Returns a human-readable description of what actions are permitted in this state.
     *
     * @return allowed actions while the light is yellow
     */
    @Override
    public String getAllowedActions() {
        return "No new crossings allowed. Finish crossing if already started";
    }

    /**
     * Returns the hex color code associated with this state.
     *
     * @return the hex color code for yellow
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
