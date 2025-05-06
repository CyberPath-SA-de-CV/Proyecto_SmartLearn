package org.cyberpath.controlador;

import org.cyberpath.vista.pantallas.combo.AccesibilidadVentana;
import org.cyberpath.vista.pantallas.combo.ConfiguracionVentana;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.pantallas.combo.ModificarContenidoVentana;

import javax.swing.*;
import java.awt.*;

public class PantallasControlador {
    private static JPanel panelContenedor;

    public static void asignarContenedor(JPanel panel){
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

    public static void mostrarPantalla(PantallasEnum pantalla) {
//        if (ventanaActual != null) {
//            ventanaActual.dispose();
//        }
        switch (pantalla) {
            case MENU_PRINCIPAL:
                cambiarContenido(new MenuPrincipalVentana().getContenido());
                break;
            case ACCESIBILIDAD:
                cambiarContenido(new AccesibilidadVentana().getContenido());
                break;
            case CONFIGURACION:
                cambiarContenido(new ConfiguracionVentana().getContenido());
                break;
            case MODIFICAR_CONTENIDO:
                cambiarContenido(new ModificarContenidoVentana().getContenido());
                break;
            default:
                throw new IllegalArgumentException("Pantalla desconocida: " + pantalla);
        }
    }
}