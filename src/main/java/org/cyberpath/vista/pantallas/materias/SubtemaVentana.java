package org.cyberpath.vista.pantallas.materias;

import org.cyberpath.controlador.materias.SubtemaControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class SubtemaVentana extends PanelDegradado {

    private static final String FONT_NAME = "Segoe UI";
    private static final int FONT_SIZE = 16;
    private static final Color BUTTON_COLOR = new Color(220, 53, 69);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    public SubtemaVentana(Tema tema, MenuPrincipalVentana menu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initializeComponents(tema, menu);
        startAccessibilityThread(tema, menu);
    }

    private void initializeComponents(Tema tema, MenuPrincipalVentana menu) {
        add(Box.createVerticalStrut(20));
        add(createTitlePanel(tema));
        add(Box.createVerticalStrut(20));
        for (Subtema subtema : tema.getSubtemas()) {
            JButton btnSubtema = crearBotonEstilizado(subtema.getNombre(), null, e -> mostrarVentanaSeleccion(subtema, menu));
            add(btnSubtema);
            add(Box.createVerticalStrut(5));
        }
        add(Box.createVerticalStrut(20));
        add(createBackButton(menu));
    }

    private JPanel createTitlePanel(Tema tema) {
        return crearPanelTituloConLogo("Subtemas | " + tema.getNombre(),
                "src/main/resources/recursosGraficos/titulos/subtema.jpg");
    }

    private JPanel createSubtemaButtons(Tema tema, MenuPrincipalVentana menu) {
        JPanel panel = crearPanelDegradadoDecorativo();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Subtema subtema : tema.getSubtemas()) {
            JButton btnSubtema = crearBotonEstilizado(subtema.getNombre(), null, e -> mostrarVentanaSeleccion(subtema, menu));
            panel.add(btnSubtema);
            panel.add(Box.createVerticalStrut(5));
        }
        return panel;
    }

    private JButton createBackButton(MenuPrincipalVentana menu) {
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
        btnRegresar.setBackground(BUTTON_COLOR);
        btnRegresar.setForeground(BUTTON_TEXT_COLOR);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(200, 40));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> menu.regresar());
        return btnRegresar;
    }

    private void startAccessibilityThread(Tema tema, MenuPrincipalVentana menu) {
        new Thread(() -> {
            try {
                SubtemaControlador.procesarAccesibilidad(tema, menu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void mostrarVentanaSeleccion(Subtema subtema, MenuPrincipalVentana menu) {
        JDialog dialog = createSelectionDialog();
        JPanel panelContenido = createSelectionPanel(subtema, menu, dialog);
        dialog.setContentPane(panelContenido);
        dialog.setVisible(true);
    }

    private JDialog createSelectionDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Selecciona el tipo de contenido", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        return dialog;
    }

    private JPanel createSelectionPanel(Subtema subtema, MenuPrincipalVentana menu, JDialog dialog) {
        PanelDegradado panelContenido = new PanelDegradado();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblMensaje = new JLabel("¿Qué tipo de contenido deseas ver?");
        lblMensaje.setFont(new Font(FONT_NAME, Font.BOLD, 18));
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

        return panelContenido;
    }
}
