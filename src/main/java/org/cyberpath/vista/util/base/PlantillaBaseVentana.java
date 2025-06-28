package org.cyberpath.vista.util.base;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public abstract class PlantillaBaseVentana extends JFrame {

    private JPanel panelSuperior;
    @Getter
    private JPanel panelCentral;
    private JScrollPane scrollPaneCentral;

    public PlantillaBaseVentana(String tituloVentana, int ancho, int alto) throws Exception {
        super(tituloVentana);
        configurarVentana(ancho, alto);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        inicializarComponentesVentana();
        establecerIconoVentana();
    }

    private void inicializarComponentesVentana() throws Exception {
        // Panel superior fijo
        panelSuperior = new PanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con CardLayout y scroll
        panelCentral = new JPanel(new CardLayout());
        configurarScrollPaneCentral();

        add(scrollPaneCentral, BorderLayout.CENTER);
        inicializarComponentes();
        agregarEventos();
    }

    private void configurarScrollPaneCentral() {
        scrollPaneCentral = new JScrollPane(panelCentral);
        scrollPaneCentral.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCentral.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCentral.getVerticalScrollBar().setUnitIncrement(50);  // Desplazamiento aumentado
    }

    protected void configurarVentana(int ancho, int alto) {
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void establecerIconoVentana() {
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursosGraficos/logos/logo.png"));
        setIconImage(icono);
    }

    protected abstract void inicializarComponentes() throws Exception;

    protected abstract void agregarEventos();

    public abstract JPanel getContenido();  // Podrías eliminar esto si ya no lo necesitas
}
