package org.cyberpath.vista.util.base;

import javax.swing.*;
import java.awt.*;

public abstract class PlantillaBaseVentana extends JFrame {

    protected JPanel panelContenedor;

    public PlantillaBaseVentana(String tituloVentana, int ancho, int alto) {
        super(tituloVentana);
        configurarVentana(ancho, alto);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        panelContenedor = new JPanel(); // inicializamos el contenedor
        panelContenedor.setLayout(new BorderLayout());
        add(panelContenedor, BorderLayout.CENTER); // lo agregamos al frame

        inicializarComponentes();
        agregarEventos();

        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursosGraficos/logos/logo.png"));
        setIconImage(icono);
    }

    protected void configurarVentana(int ancho, int alto) {
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    protected void establecerContenidoConPanelSuperior(JComponent contenidoPrincipal) {
        ContenidoConPanelSuperior panelConSuperior = new ContenidoConPanelSuperior(contenidoPrincipal);
        panelContenedor.removeAll();
        panelContenedor.add(panelConSuperior, BorderLayout.CENTER);
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    protected abstract void inicializarComponentes();
    protected abstract void agregarEventos();
    public abstract JPanel getContenido();

}
