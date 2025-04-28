package org.cyberpath.vista.componentesR;

import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
import java.awt.event.ActionListener;
=======
>>>>>>> 3c12c289a94bd8099422906a985e982ec15a8c0c

/**
 * PlantillaVentanaBase
 * Clase base para pantallas en la aplicación Smart-Learn.
 * Proporciona estructura reutilizable con Java Swing y soporte para accesibilidad.
 *
 * Métodos clave:
 * - configurarVentana: configura propiedades generales de la ventana.
 * - inicializarComponentes: crea y organiza los elementos visuales.
 * - agregarEventos: vincula acciones a botones y campos.
 * - construirInterfaz: junta los elementos y muestra la interfaz.
 *
 * @author TuNombre
 */
public abstract class PlantillaVentanaBase extends JFrame {

    protected JPanel panelPrincipal;

    public PlantillaVentanaBase(String tituloVentana, int ancho, int alto) {
        super(tituloVentana);
        configurarVentana(ancho, alto);
        inicializarComponentes();
        agregarEventos();
        construirInterfaz();
<<<<<<< HEAD
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/logo.png"));
=======
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logos/logo.png"));
>>>>>>> 3c12c289a94bd8099422906a985e982ec15a8c0c
        setIconImage(icono);
    }

    /**
     * Configura las propiedades principales de la ventana.
     *
     * @param ancho Ancho deseado de la ventana.
     * @param alto  Alto deseado de la ventana.
     */
    protected void configurarVentana(int ancho, int alto) {
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);
    }

    /**
     * Crea y configura los componentes visuales.
     * Debe ser implementado por las subclases.
     */
    protected abstract void inicializarComponentes();

    /**
     * Asocia los eventos (listeners) a los botones o campos.
     * Puede ser sobrescrito si no se usan eventos.
     */
    protected void agregarEventos() {
        // Implementar en subclase si es necesario
    }

    /**
     * Agrega los componentes al panel principal y los muestra.
     */
    protected void construirInterfaz() {
        if (panelPrincipal != null) {
            add(panelPrincipal);
        }
    }
}
