package org.cyberpath.vista.componentesR;

import javax.swing.*;
import java.awt.*;

/**
 * PlantillaVentanaBase
 * Clase base para pantallas en la aplicación Smart-Learn.
 * Proporciona estructura reutilizable con Java Swing y soporte para accesibilidad.
 */
public abstract class PlantillaVentanaBase extends JFrame {

    protected JPanel panelContenedor;

    public PlantillaVentanaBase(String tituloVentana, int ancho, int alto) {
        super(tituloVentana);
        configurarVentana(ancho, alto);
        panelContenedor = new JPanel(); // inicializamos el contenedor
        panelContenedor.setLayout(new BorderLayout());
        add(panelContenedor, BorderLayout.CENTER); // lo agregamos al frame

        inicializarComponentes();
        agregarEventos();
        construirInterfaz();

        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logos/logo.png"));
        setIconImage(icono);
    }

    protected void configurarVentana(int ancho, int alto) {
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    protected abstract void inicializarComponentes();

    protected void agregarEventos() {
        // Puede sobrescribirse
    }

    protected void construirInterfaz() {
        // Puede usarse en subclases para agregar componentes a panelContenedor
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    public abstract JPanel getContenido();
}