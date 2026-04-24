package com.trafficlight.state;

import com.trafficlight.context.TrafficLight;

/**
 * Defines the contract for a traffic light state within a State Pattern implementation.
 * <p>
 * Implementations encapsulate state-specific behavior and metadata (e.g., color, timing, and
 * user-facing descriptions) while delegating state transitions through {@link #changeToNext(TrafficLight)}.
 * This interface keeps responsibilities focused: it describes what a state must provide, not how the
 * context stores or renders state.
 */
public interface TrafficLightState {

    /**
     * Transitions the provided {@code context} to the next appropriate state.
     * <p>
     * Implementations should only perform the state transition and any state-specific transition rules.
     * The {@code context} owns the state reference; states decide what the next state is.
     *
     * @param context the traffic light context whose state should be advanced
     * @throws IllegalArgumentException if {@code context} is {@code null}
     */
    void changeToNext(TrafficLight context);

    /**
     * Returns the human-readable name of the color represented by this state.
     *
     * @return the color name (for example, "Red", "Yellow", or "Green")
     */
    String getColorName();

    /**
     * Returns the configured duration, in seconds, for which this state should remain active.
     *
     * @return the state duration in seconds
     */
    int getDurationSeconds();

    /**
     * Returns a short description of the meaning of this state.
     *
     * @return a description intended for display to users
     */
    String getDescription();

    /**
     * Returns a description of what actions are permitted while the traffic light is in this state.
     *
     * @return allowed actions, expressed as a human-readable string
     */
    String getAllowedActions();
}
