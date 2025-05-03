package org.cyberpath.vista.util.componentes;

import javax.swing.*;
import java.awt.*;

public abstract class PanelDegradado extends JPanel {
    public PanelDegradado() {
        setOpaque(false); // Permite que se pinte el degradado
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(30, 42, 82);
        Color color2 = new Color(60, 80, 120);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(255, 255, 255, 25));
        for (int x = 0; x < getWidth(); x += 60) {
            g2d.drawLine(x, 0, x, getHeight());
        }
    }
}
