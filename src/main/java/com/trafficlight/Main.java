package com.trafficlight;

import com.trafficlight.context.TrafficLight;
import com.trafficlight.controller.TrafficLightController;

/**
 * Application entry point for demonstrating the Traffic Light State Pattern.
 */
public class Main {

    /**
     * Runs the Traffic Light State Pattern demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // 1) Welcome banner
        System.out.println("========================================");
        System.out.println("  TRAFFIC LIGHT - STATE PATTERN DEMO");
        System.out.println("========================================");

        // 2) Create controller
        TrafficLightController controller = new TrafficLightController();

        // 3) Create traffic lights
        TrafficLight mainAndFirst = new TrafficLight("Main St & 1st Ave");
        TrafficLight parkAndFifth = new TrafficLight("Park Ave & 5th St");
        TrafficLight centralAndOak = new TrafficLight("Central Blvd & Oak Rd");

        // 4) Register traffic lights
        controller.addTrafficLight(mainAndFirst);
        controller.addTrafficLight(parkAndFifth);
        controller.addTrafficLight(centralAndOak);

        // 5) Print initial statuses
        System.out.println("\n--- Initial Status (All RED) ---");
        controller.printAllStatus();

        // 6) Simulate 6 state changes for all lights
        for (int i = 1; i <= 6; i++) {
            System.out.println("\n----------------------------------------");
            System.out.println("Advance All: Step " + i);
            System.out.println("----------------------------------------");

            controller.advanceAll();
            controller.printAllStatus();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Sleep interrupted; continuing demo.");
            }
        }

        // 7) Advance only one light two more times
        System.out.println("\n--- Advancing Only: Main St & 1st Ave (2 steps) ---");
        controller.advanceOne("Main St & 1st Ave");
        controller.advanceOne("Main St & 1st Ave");
        controller.getTrafficLight("Main St & 1st Ave").printStatus();

        // 8) Print total cycles
        System.out.println("\n--- Total Cycles Across All Lights ---");
        System.out.println("Total cycles: " + controller.getTotalCycles());

        // 9) Print full state history for one location
        System.out.println("\n--- State History: Main St & 1st Ave ---");
        controller.getTrafficLight("Main St & 1st Ave")
                .getStateHistory()
                .forEach(System.out::println);

        // 10) Closing message
        System.out.println("\nDemo complete. Thank you for watching the State Pattern in action.");
    }
}
