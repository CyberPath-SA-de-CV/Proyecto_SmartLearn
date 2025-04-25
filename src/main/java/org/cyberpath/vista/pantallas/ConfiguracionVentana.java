package org.cyberpath.vista.pantallas;

import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.componentesR.ComponentesReutilizables;

import javax.swing.*;
import java.awt.*;

public class ConfiguracionVentana extends ComponentesReutilizables {

    public ConfiguracionVentana() {
        setTitle("Configuración");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Fondo con diseño degradado y líneas
        JPanel fondo = crearPanelDegradadoDecorativo();
        fondo.setLayout(new BorderLayout());

        // Panel principal con padding y transparencia
        JPanel contenido = crearPanelTransparenteConPadding(40, 100, 40, 100);

        // Barra superior con usuario y fecha
        JPanel barraSuperior = crearBarraSuperior("Usuario: " + VariablesGlobales.usuario.getNombre());
        contenido.add(barraSuperior);
        contenido.add(Box.createVerticalStrut(30));

        // Panel con logo y título
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon iconoLogo = new ImageIcon("img/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel labelTexto = crearTituloCentrado("Configuración del sistema");
        labelTexto.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);
        contenido.add(panelTituloConLogo);
        contenido.add(Box.createVerticalStrut(40));

        // Panel de checkboxes u opciones
        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox activarVoz = crearCheckBoxEstilizado("Activar Voz", VariablesGlobales.reproduccionGlobalAudio);
        activarVoz.addActionListener(e -> VariablesGlobales.reproduccionGlobalAudio = activarVoz.isSelected());

        JCheckBox ejemploCheck2 = crearCheckBoxEstilizado("Opción 2", false);
        JCheckBox ejemploCheck3 = crearCheckBoxEstilizado("Opción 3", false);

        opciones.add(activarVoz);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(ejemploCheck2);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(ejemploCheck3);
        opciones.add(Box.createVerticalStrut(30));

        // Botón "Salir"
        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonSalir.setBackground(new Color(220, 53, 69)); // rojo suave
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setFocusPainted(false);
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setMaximumSize(new Dimension(200, 40));
        botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonSalir.addActionListener(e -> dispose()); // cerrar solo esta ventana

        opciones.add(botonSalir);

        contenido.add(opciones);
        contenido.add(Box.createVerticalGlue());

        // Scroll
        JScrollPane scrollPane = new JScrollPane(contenido,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        fondo.add(scrollPane, BorderLayout.CENTER);
        setContentPane(fondo);
        setVisible(true);
    }

    private JCheckBox crearCheckBoxEstilizado(String texto, boolean seleccionado) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setSelected(seleccionado);
        checkBox.setOpaque(false);
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        checkBox.setForeground(Color.WHITE);
        checkBox.setFocusPainted(false);
        checkBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return checkBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConfiguracionVentana::new);
    }
}
