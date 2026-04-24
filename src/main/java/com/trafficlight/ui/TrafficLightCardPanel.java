package com.trafficlight.ui;

import com.trafficlight.context.TrafficLight;
import com.trafficlight.controller.TrafficLightController;
import com.trafficlight.state.TrafficLightState;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.function.Consumer;

public class TrafficLightCardPanel extends JPanel {

    private final TrafficLight light;
    private final TrafficLightController controller;
    private final Consumer<String> historySink;
    private final Runnable refresh;

    private final JLabel stateLabel;
    private final JLabel cyclesLabel;
    private final JLabel durationLabel;
    private final JLabel descriptionLabel;
    private final JLabel allowedLabel;
    private final BulbPanel redBulb;
    private final BulbPanel yellowBulb;
    private final BulbPanel greenBulb;

    public TrafficLightCardPanel(
            TrafficLight light,
            TrafficLightController controller,
            Consumer<String> historySink,
            Runnable refresh
    ) {
        this.light = light;
        this.controller = controller;
        this.historySink = historySink;
        this.refresh = refresh;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout(10, 4));
        header.setOpaque(false);

        JLabel locationLabel = new JLabel(light.getLocation());
        locationLabel.setFont(locationLabel.getFont().deriveFont(Font.BOLD, 14f));

        cyclesLabel = new JLabel();
        cyclesLabel.setHorizontalAlignment(JLabel.RIGHT);
        cyclesLabel.setForeground(new Color(90, 90, 90));

        header.add(locationLabel, BorderLayout.WEST);
        header.add(cyclesLabel, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout(10, 10));
        body.setOpaque(false);

        JPanel signal = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        signal.setOpaque(false);

        redBulb = new BulbPanel(Color.decode("#FF0000"));
        yellowBulb = new BulbPanel(Color.decode("#FFC300"));
        greenBulb = new BulbPanel(Color.decode("#00C851"));
        signal.add(redBulb);
        signal.add(yellowBulb);
        signal.add(greenBulb);

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new javax.swing.BoxLayout(details, javax.swing.BoxLayout.Y_AXIS));

        stateLabel = new JLabel();
        stateLabel.setFont(stateLabel.getFont().deriveFont(Font.BOLD, 13f));

        durationLabel = new JLabel();
        descriptionLabel = new JLabel();
        allowedLabel = new JLabel();

        durationLabel.setForeground(new Color(70, 70, 70));
        descriptionLabel.setForeground(new Color(70, 70, 70));
        allowedLabel.setForeground(new Color(70, 70, 70));

        details.add(stateLabel);
        details.add(durationLabel);
        details.add(descriptionLabel);
        details.add(allowedLabel);

        body.add(signal, BorderLayout.NORTH);
        body.add(details, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);

        JButton btnAdvance = new JButton("Advance");
        btnAdvance.addActionListener(e -> {
            String before = light.getCurrentState().getColorName();
            light.changeToNext();
            String after = light.getCurrentState().getColorName();
            historySink.accept(light.getLocation() + ": " + before + " → " + after);
            updateFromState();
            refresh.run();
        });

        JButton btnHistory = new JButton("History");
        btnHistory.addActionListener(e -> showHistoryDialog());

        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Remove intersection \"" + light.getLocation() + "\"?",
                    "Remove Intersection",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            controller.removeTrafficLight(light.getLocation());
            historySink.accept("Removed intersection: " + light.getLocation());
            refresh.run();
        });

        actions.add(btnHistory);
        actions.add(btnAdvance);
        actions.add(btnRemove);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        updateFromState();
    }

    private void updateFromState() {
        TrafficLightState state = light.getCurrentState();
        String name = state.getColorName();

        stateLabel.setText("State: " + name);
        cyclesLabel.setText("Cycles: " + light.getCycleCount());
        durationLabel.setText("Duration: " + state.getDurationSeconds() + " seconds");
        descriptionLabel.setText("Description: " + state.getDescription());
        allowedLabel.setText("Allowed: " + state.getAllowedActions());

        redBulb.setOn("RED".equalsIgnoreCase(name));
        yellowBulb.setOn("YELLOW".equalsIgnoreCase(name));
        greenBulb.setOn("GREEN".equalsIgnoreCase(name));

        repaint();
    }

    private void showHistoryDialog() {
        List<String> history = light.getStateHistory();
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        if (history.isEmpty()) {
            area.setText("(No state changes yet)");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String entry : history) {
                sb.append(entry).append(System.lineSeparator());
            }
            area.setText(sb.toString());
        }

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(520, 280));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "History - " + light.getLocation(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
