package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.controlador.combo.AccesibilidadControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class AccesibilidadVentana extends PlantillaBaseVentana {

    private JPanel contenidoPrincipal;

    public AccesibilidadVentana() throws Exception {
        super("Accesibilidad", 1200, 800);
        iniciarAccesibilidad();
    }

    @Override
    protected void inicializarComponentes() {
        contenidoPrincipal = crearPanelDegradadoDecorativo("Accesibilidad", "src/main/resources/recursosGraficos/titulos/accesibilidad.jpg");

        JPanel panelTitulo = crearPanelTitulo();
        JPanel panelOpciones = crearPanelOpciones();

        JPanel panelInterno = new JPanel();
        panelInterno.setOpaque(false);
        panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));

        panelInterno.add(Box.createVerticalStrut(20));
        panelInterno.add(panelTitulo);
        panelInterno.add(Box.createVerticalStrut(30));
        panelInterno.add(panelOpciones);
        panelInterno.add(Box.createVerticalGlue());

        contenidoPrincipal.add(panelInterno, BorderLayout.CENTER);
        getPanelCentral().add(contenidoPrincipal);
    }

    private JPanel crearPanelTitulo() {
        JPanel titulo = new JPanel();
        JLabel labelTexto = crearTituloCentrado("Configuración de Accesibilidad");
        titulo.add(labelTexto);
        titulo.setOpaque(false);
        titulo.setLayout(new BoxLayout(titulo, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        return titulo;
    }
    private JPanel crearPanelOpciones() {
        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        JCheckBox activarVoz = crearCheckBox("Activar Voz", VariablesGlobales.auxModoAudio);
        activarVoz.addActionListener(e -> VariablesGlobales.auxModoAudio = activarVoz.isSelected());
        JCheckBox modoAccesible = crearCheckBox("Opción 2", false);

        opciones.add(activarVoz);
        opciones.add(Box.createVerticalStrut(30));
        opciones.add(modoAccesible);
        opciones.add(Box.createVerticalStrut(30));

        JButton botonSalir = crearBotonSalirAPantallaPrincipal();
        JPanel contenedorBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        contenedorBoton.setOpaque(false);
        contenedorBoton.add(botonSalir);

        opciones.add(contenedorBoton);

        return opciones;
    }



    @Override
    protected void agregarEventos() {
    }

    @Override
    public JPanel getContenido() {
        return contenidoPrincipal;
    }

    private void iniciarAccesibilidad() {
        new Thread(() -> {
            try {
                if (PantallasControlador.menuAccesibilidad("Accesibilidad", this)) {
                    AccesibilidadControlador.procesarAccesibilidad(this);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(33);
        VariablesGlobales.usuario = ejemplo;
        SwingUtilities.invokeLater(() -> {
            try {
                new AccesibilidadVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
