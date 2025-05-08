package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class AccesibilidadVentana extends PlantillaBaseVentana {

    private JPanel contenidoPrincipal;

    public AccesibilidadVentana() {
        super("Accesibilidad", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccesibilidadVentana().setVisible(true));
    }

    @Override
    protected void inicializarComponentes() {
        contenidoPrincipal = crearPanelDegradadoDecorativo("Accesibilidad");
        contenidoPrincipal.setLayout(new BoxLayout(contenidoPrincipal, BoxLayout.Y_AXIS));

        contenidoPrincipal.add(Box.createVerticalStrut(30));
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Configuración del sistema");
        contenidoPrincipal.add(panelTituloConLogo);
        contenidoPrincipal.add(Box.createVerticalStrut(40));

        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox activarVoz = crearCheckBox("Activar Voz", VariablesGlobales.usuario.getModoAudio());
        activarVoz.addActionListener(e -> VariablesGlobales.usuario.setModoAudio(activarVoz.isSelected()));

        JCheckBox modoAccesible = crearCheckBox("Opción 2", false); // Puedes reemplazar por funcionalidad real

        opciones.add(activarVoz);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(modoAccesible);
        opciones.add(Box.createVerticalStrut(50));

        JButton botonSalir = crearBotonSalirAPantallaPrincipal();
        opciones.add(botonSalir);

        contenidoPrincipal.add(opciones);
        contenidoPrincipal.add(Box.createVerticalGlue());

        JScrollPane scrollContenido = crearScrollPaneTransparente(contenidoPrincipal);
        getPanelCentral().add(scrollContenido, BorderLayout.CENTER); // Cambio aquí
    }

    @Override
    protected void agregarEventos() {
        // Eventos adicionales si los necesitas
    }

    @Override
    public JPanel getContenido() {
        return contenidoPrincipal;
    }
}
