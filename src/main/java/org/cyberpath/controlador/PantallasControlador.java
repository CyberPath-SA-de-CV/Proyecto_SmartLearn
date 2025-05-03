package org.cyberpath.controlador;

import org.cyberpath.vista.pantallas.combo.AccesibilidadVentana;
import org.cyberpath.vista.pantallas.combo.ConfiguracionVentana;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.pantallas.combo.ModificarContenidoVentana;

import javax.swing.*;

public class PantallasControlador {
    private static JFrame ventanaActual;

    public static void mostrarPantalla(PantallasEnum pantalla) {
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
        switch (pantalla) {
            case MENU_PRINCIPAL:
                ventanaActual = new MenuPrincipalVentana();
                break;
            case ACCESIBILIDAD:
                ventanaActual = new AccesibilidadVentana();
                break;
            case CONFIGURACION:
                ventanaActual = new ConfiguracionVentana();
                break;
            case MODIFICAR_CONTENIDO:
                ventanaActual = new ModificarContenidoVentana();
                break;
            default:
                throw new IllegalArgumentException("Pantalla desconocida: " + pantalla);
        }
        SwingUtilities.invokeLater(() -> ventanaActual.setVisible(true));;
    }
}