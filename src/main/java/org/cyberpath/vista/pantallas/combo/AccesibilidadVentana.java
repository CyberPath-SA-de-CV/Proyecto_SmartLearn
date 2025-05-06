package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class AccesibilidadVentana extends PlantillaBaseVentana {

    public AccesibilidadVentana() {
        super("Accesiblidad", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Úsalo solo para pruebas independientes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccesibilidadVentana().setVisible(true));
    }

    @Override
    protected void inicializarComponentes() {
        JPanel contenido = crearPanelDegradadoDecorativo("Accesiblidad");
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        contenido.add(Box.createVerticalStrut(30));
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Configuración del sistema");
        contenido.add(panelTituloConLogo);

        contenido.add(Box.createVerticalStrut(40));

        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        JCheckBox activarVoz = crearCheckBox("Activar Voz", VariablesGlobales.usuario.getModoAudio());
        activarVoz.addActionListener(e -> VariablesGlobales.usuario.setModoAudio(activarVoz.isSelected()));
        JCheckBox modoAccesible = crearCheckBox("Opción 2", false); // Cambiar si es necesario
        opciones.add(activarVoz);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(modoAccesible);
        opciones.add(Box.createVerticalStrut(50));
        JButton botonSalir = crearBotonSalirAPantallaPrincipal();
        opciones.add(botonSalir);

        contenido.add(opciones);
        contenido.add(Box.createVerticalGlue());

        JScrollPane scrollContenido = crearScrollPaneTransparente(contenido);
        establecerContenidoConPanelSuperior(scrollContenido);
    }

    @Override
    protected void agregarEventos() {

    }

    @Override
    public JPanel getContenido() {
        return null;
    }

}
