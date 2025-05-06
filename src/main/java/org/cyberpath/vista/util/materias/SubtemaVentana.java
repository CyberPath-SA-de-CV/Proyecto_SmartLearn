package org.cyberpath.vista.util.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class SubtemaVentana extends PanelDegradado {
    public SubtemaVentana(Tema tema, MenuPrincipalVentana menu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Subtemas | " + tema.getNombre());
        add(panelTituloConLogo);
        add(Box.createVerticalStrut(20));

        for (Subtema subtema : tema.getSubtemas()) {
            JButton btnSubtema = crearBotonEstilizado(subtema.getNombre(), null, e -> {
                mostrarVentanaSeleccion(subtema, menu);
            });
            add(btnSubtema);
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

    private void mostrarVentanaSeleccion(Subtema subtema, MenuPrincipalVentana menu) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Selecciona el tipo de contenido", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Panel decorado
        PanelDegradado panelContenido = new PanelDegradado();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblMensaje = new JLabel("¿Qué tipo de contenido deseas ver?");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setForeground(Color.WHITE);
        panelContenido.add(lblMensaje);
        panelContenido.add(Box.createVerticalStrut(20));

        JButton btnTeoria = crearBotonEstilizado("Teoría", null, e -> {
            dialog.dispose();
            menu.mostrarContenidoTeorico(subtema);
        });

        JButton btnPractica = crearBotonEstilizado("Práctica", null, e -> {
            dialog.dispose();
            menu.mostrarContenidoPractico(subtema);
        });

        btnTeoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPractica.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContenido.add(btnTeoria);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(btnPractica);

        dialog.setContentPane(panelContenido);
        dialog.setUndecorated(true); // si quieres un estilo más moderno sin bordes
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        dialog.setVisible(true);
    }
}
