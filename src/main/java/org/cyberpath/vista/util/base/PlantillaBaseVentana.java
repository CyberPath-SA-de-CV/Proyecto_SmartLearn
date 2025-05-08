package org.cyberpath.vista.util.base;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public abstract class PlantillaBaseVentana extends JFrame {

    private JPanel panelSuperior;
    private JPanel panelCentral;  // Panel con CardLayout

    public PlantillaBaseVentana(String tituloVentana, int ancho, int alto) {
        super(tituloVentana);
        configurarVentana(ancho, alto);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // Panel superior fijo
        panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central que cambia
        panelCentral = new JPanel(new CardLayout());
        add(panelCentral, BorderLayout.CENTER);

        inicializarComponentes();  // Lo que quieras mostrar al inicio
        agregarEventos();

        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursosGraficos/logos/logo.png"));
        setIconImage(icono);
    }

    protected void configurarVentana(int ancho, int alto) {
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Agrega una nueva pantalla al panel central con nombre y la muestra.
     */
    public void mostrarPantalla(String nombre, JPanel pantalla) {
        panelCentral.add(pantalla, nombre);
        ((CardLayout) panelCentral.getLayout()).show(panelCentral, nombre);
    }

    /**
     * Opcional: si necesitas acceso al panel central directamente.
     */
    public JPanel getPanelCentral() {
        return this.panelCentral;
    }

    /**
     * Si necesitas refrescar solo el panel superior.
     */
    public void actualizarPanelSuperior(JPanel nuevoPanelSuperior) {
        remove(panelSuperior);
        panelSuperior = nuevoPanelSuperior;
        add(panelSuperior, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    protected JPanel crearPanelSuperior() {
        return new PanelSuperior();
    }

    protected abstract void inicializarComponentes();
    protected abstract void agregarEventos();
    public abstract JPanel getContenido();  // Podrías eliminar esto si ya no lo necesitas
}
