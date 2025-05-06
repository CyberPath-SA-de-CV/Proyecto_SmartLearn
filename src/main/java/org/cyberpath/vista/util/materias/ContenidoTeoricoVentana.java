package org.cyberpath.vista.util.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ContenidoTeoricoVentana extends PanelDegradado {
    public ContenidoTeoricoVentana(Subtema subtema, MenuPrincipalVentana menu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Diseño vertical

        add(Box.createVerticalStrut(20));

        JPanel panelTituloConLogo = crearPanelTituloConLogo("Teoría. Subtema | " + subtema.getNombre());
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelTituloConLogo);


        JTextArea texto = new JTextArea(subtema.getContenidoTeorico().getTexto());
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Serif", Font.PLAIN, 19));
        texto.setForeground(Color.BLACK);
        texto.setBackground(new Color(255, 255, 255, 200));
        texto.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JScrollPane scroll = new JScrollPane(texto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(800, 550)); // Ajustable según necesidad

        // Panel envolvente para hacer que el scroll se expanda
        JPanel panelTexto = new JPanel();
        panelTexto.setOpaque(false);
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.add(scroll);
        panelTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        add(panelTexto);

        add(Box.createVerticalStrut(20));

        // Botón salir
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegresar.setBackground(new Color(220, 53, 69));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(200, 40));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> menu.regresar());
        add(btnRegresar);

        add(Box.createVerticalStrut(20));
    }
}
