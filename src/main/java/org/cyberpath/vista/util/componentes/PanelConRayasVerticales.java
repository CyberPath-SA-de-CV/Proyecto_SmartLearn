package org.cyberpath.vista.util.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelConRayasVerticales extends JPanel {
    private final Color fondo = new Color(5, 100, 110);         // Color base
    private final Color rayas = new Color(10, 130, 140, 80);    // Color de rayas semitransparentes

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Fondo liso
        g2.setColor(fondo);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Rayas verticales
        g2.setColor(rayas);
        int anchoRaya = 6;
        int espacio = 16;

        for (int x = 0; x < getWidth(); x += espacio) {
            g2.fillRect(x, 0, anchoRaya, getHeight());
        }

        g2.dispose();
    }
}