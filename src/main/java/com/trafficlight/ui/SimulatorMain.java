package com.trafficlight.ui;

import javax.swing.SwingUtilities;

public class SimulatorMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficLightSimulatorFrame().setVisible(true));
    }
}
