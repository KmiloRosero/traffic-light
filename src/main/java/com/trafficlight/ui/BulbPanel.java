package com.trafficlight.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BulbPanel extends JPanel {

    private final Color color;
    private boolean on;

    public BulbPanel(Color color) {
        this.color = color;
        this.on = false;
        setOpaque(false);
        setPreferredSize(new Dimension(34, 34));
        setMinimumSize(new Dimension(34, 34));
    }

    public void setOn(boolean on) {
        this.on = on;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        Color fill = on ? color : new Color(230, 230, 230);
        g2.setColor(fill);
        g2.fillOval(x, y, size, size);

        g2.setColor(new Color(140, 140, 140));
        g2.drawOval(x, y, size, size);

        if (on) {
            g2.setColor(new Color(255, 255, 255, 90));
            int highlightSize = (int) (size * 0.45);
            g2.fillOval(x + (int) (size * 0.18), y + (int) (size * 0.18), highlightSize, highlightSize);
        }

        g2.dispose();
    }
}
