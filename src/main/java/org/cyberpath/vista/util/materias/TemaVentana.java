package org.cyberpath.vista.util.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;

import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class TemaVentana extends PanelDegradado {
    public TemaVentana(Materia materia, MenuPrincipalVentana menu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Temas | " + materia.getNombre());
        add(panelTituloConLogo);
        add(Box.createVerticalStrut(20));

        for (Tema tema : materia.getTemas()) {
            JButton btnTema = crearBotonEstilizado(tema.getNombre(), null, e ->
                    menu.mostrarSubtemas(tema));
            add(btnTema);
            add(Box.createVerticalStrut(5));
        }
        add(Box.createVerticalStrut(20));

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
    }
}