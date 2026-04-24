package com.trafficlight.ui;

import com.trafficlight.context.TrafficLight;
import com.trafficlight.controller.TrafficLightController;
import com.trafficlight.state.TrafficLightState;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrafficLightSimulatorFrame extends JFrame {

    private static final List<String> DEFAULT_INTERSECTIONS = List.of(
            "Main St & 1st Ave",
            "Park Ave & 5th St",
            "Central Blvd & Oak Rd"
    );

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private TrafficLightController controller;
    private final JPanel cardsPanel;
    private final JTextArea historyArea;
    private final JLabel totalCyclesLabel;

    public TrafficLightSimulatorFrame() {
        super("Traffic Light - State Pattern Simulator");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 640));
        setLocationByPlatform(true);

        controller = new TrafficLightController();
        DEFAULT_INTERSECTIONS.forEach(location -> controller.addTrafficLight(new TrafficLight(location)));

        JPanel header = buildHeader();
        JPanel controls = buildControls();

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(0, 3, 12, 12));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane cardsScroll = new JScrollPane(cardsPanel);
        cardsScroll.setBorder(BorderFactory.createTitledBorder("Intersections"));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);

        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createTitledBorder("State Change History"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cardsScroll, historyScroll);
        splitPane.setResizeWeight(0.70);

        totalCyclesLabel = new JLabel("Total cycles: 0");
        totalCyclesLabel.setFont(totalCyclesLabel.getFont().deriveFont(Font.PLAIN, 12f));
        totalCyclesLabel.setForeground(new Color(70, 70, 70));

        JPanel footer = buildFooter();

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add(header, BorderLayout.NORTH);
        top.add(controls, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        appendHistory("Simulator ready");
        refreshCards();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(14, 14, 10, 14));

        JLabel title = new JLabel("Traffic Light State Pattern", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        JLabel subtitle = new JLabel("Design Patterns - Software Engineering", SwingConstants.LEFT);
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 13f));
        subtitle.setForeground(new Color(70, 70, 70));

        header.add(title);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);
        return header;
    }

    private JPanel buildControls() {
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controls.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton btnAdvanceAll = new JButton("Advance All Lights");
        btnAdvanceAll.addActionListener(e -> {
            controller.advanceAll();
            appendHistory("Advanced all lights");
            refreshCards();
        });

        JButton btnResetAll = new JButton("Reset All");
        btnResetAll.addActionListener(e -> {
            controller = new TrafficLightController();
            DEFAULT_INTERSECTIONS.forEach(location -> controller.addTrafficLight(new TrafficLight(location)));
            historyArea.setText("");
            appendHistory("All lights reset to RED");
            refreshCards();
        });

        JButton btnAdd = new JButton("+ Add Intersection");
        btnAdd.addActionListener(e -> {
            String location = JOptionPane.showInputDialog(this, "Enter intersection name:", "Add Intersection", JOptionPane.QUESTION_MESSAGE);
            if (location == null) {
                return;
            }
            String trimmed = location.trim();
            if (trimmed.isEmpty()) {
                return;
            }
            controller.addTrafficLight(new TrafficLight(trimmed));
            appendHistory("Added intersection: " + trimmed);
            refreshCards();
        });

        JButton btnClearHistory = new JButton("Clear History");
        btnClearHistory.addActionListener(e -> {
            historyArea.setText("");
            appendHistory("History cleared");
        });

        controls.add(btnAdvanceAll);
        controls.add(btnResetAll);
        controls.add(btnAdd);
        controls.add(btnClearHistory);
        return controls;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JLabel left = new JLabel("State Pattern | Universidad Cooperativa de Colombia");
        left.setFont(left.getFont().deriveFont(Font.PLAIN, 12f));
        left.setForeground(new Color(70, 70, 70));

        footer.add(left, BorderLayout.WEST);
        footer.add(totalCyclesLabel, BorderLayout.EAST);
        return footer;
    }

    private void refreshCards() {
        cardsPanel.removeAll();

        for (TrafficLight light : controller.getTrafficLights()) {
            cardsPanel.add(new TrafficLightCardPanel(light, controller, this::appendHistory, this::refreshCards));
        }

        totalCyclesLabel.setText("Total cycles: " + controller.getTotalCycles());

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private void appendHistory(String message) {
        String line = "[" + LocalTime.now().format(TIME_FORMATTER) + "] " + message;
        if (historyArea.getText().isBlank()) {
            historyArea.setText(line);
        } else {
            historyArea.setText(line + System.lineSeparator() + historyArea.getText());
        }
    }

    static Color colorForState(TrafficLightState state) {
        String name = state.getColorName();
        if ("RED".equalsIgnoreCase(name)) {
            return Color.decode("#FF0000");
        }
        if ("YELLOW".equalsIgnoreCase(name)) {
            return Color.decode("#FFC300");
        }
        if ("GREEN".equalsIgnoreCase(name)) {
            return Color.decode("#00C851");
        }
        return new Color(120, 120, 120);
    }
}
