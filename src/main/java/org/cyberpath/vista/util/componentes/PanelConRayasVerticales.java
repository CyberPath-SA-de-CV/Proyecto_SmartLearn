package org.cyberpath.vista.util.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelConRayasVerticales extends JPanel {
    private static final Color FONDO_COLOR = new Color(5, 100, 110);         // Color base
    private static final Color RAYAS_COLOR = new Color(10, 130, 140, 80);    // Color de rayas semitransparentes
    private static final int ANCHO_RAYA = 6;                                 // Ancho de las rayas
    private static final int ESPACIO_RAYAS = 16;                             // Espacio entre las rayas

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        dibuajarFondo(g2);
        dibujarLineasDecorativas(g2);

        g2.dispose();
    }

    private void dibuajarFondo(Graphics2D g2) {
        g2.setColor(FONDO_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void dibujarLineasDecorativas(Graphics2D g2) {
        g2.setColor(RAYAS_COLOR);
        for (int x = 0; x < getWidth(); x += ESPACIO_RAYAS) {
            g2.fillRect(x, 0, ANCHO_RAYA, getHeight());
        }
    }
}
