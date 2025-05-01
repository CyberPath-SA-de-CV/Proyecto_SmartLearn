package org.cyberpath.controlador;

import org.cyberpath.vista.pantallas.AccesibilidadVentana;
import org.cyberpath.vista.pantallas.ConfiguracionPantalla;
import org.cyberpath.vista.pantallas.MenuPrincipalPantalla;

import javax.swing.*;
import java.awt.*;

public class ControladorDePantallas {

    private static JPanel panelContenedor;

    public static void asignarContenedor(JPanel panel) {
        panelContenedor = panel;
    }

    public static void cambiarContenido(JPanel nuevoContenido) {
        if (panelContenedor == null) {
            throw new IllegalStateException("Panel contenedor no ha sido asignado.");
        }
        panelContenedor.removeAll();
        panelContenedor.setLayout(new BorderLayout());
        panelContenedor.add(nuevoContenido, BorderLayout.CENTER);
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    public static void mostrarPantalla(int idPantalla) {
        switch (idPantalla) {
            case PANTALLA_MENU_PRINCIPAL:
                cambiarContenido(new MenuPrincipalPantalla().getContenido());
                break;
            case PANTALLA_ACCESIBILIDAD:
                cambiarContenido(new AccesibilidadVentana().getContenido());
                break;
            case PANTALLA_CONFIGURACION:
                cambiarContenido(new ConfiguracionPantalla().getContenido());
                break;
            default:
                throw new IllegalArgumentException("ID de pantalla desconocido: " + idPantalla);
        }
    }

    public static final int PANTALLA_MENU_PRINCIPAL = 0;
    public static final int PANTALLA_ACCESIBILIDAD = -1;
    public static final int PANTALLA_CONFIGURACION = -2;
}