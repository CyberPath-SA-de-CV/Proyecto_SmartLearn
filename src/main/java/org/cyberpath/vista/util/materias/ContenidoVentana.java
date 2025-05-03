package org.cyberpath.vista.util.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ContenidoVentana extends PanelDegradado {
    public ContenidoVentana(Subtema subtema, MenuPrincipalVentana menu) {
        setLayout(new BorderLayout());

        add(Box.createVerticalStrut(20));
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Teoría. Subtema | " + subtema.getNombre());
        add(panelTituloConLogo);
        add(Box.createVerticalStrut(20));

        JTextArea texto = new JTextArea(subtema.getContenidoTeorico().getTexto());
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Serif", Font.PLAIN, 19));
        texto.setForeground(Color.BLACK); // Texto negro
        texto.setBackground(new Color(255, 255, 255, 200)); // Fondo blanco semi-transparente
        texto.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Padding interno

        JScrollPane scroll = new JScrollPane(texto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scroll, BorderLayout.CENTER);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegresar.setBackground(new Color(220, 53, 69));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(200, 40));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> menu.regresar());
        add(btnRegresar, BorderLayout.SOUTH);

        /// METODO LEER EL TEXTO
    }
}

