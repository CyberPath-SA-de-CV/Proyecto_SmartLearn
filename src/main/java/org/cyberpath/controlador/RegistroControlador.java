package org.cyberpath.controlador;

import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.vista.pantallas.MenuPrincipalPantalla;

import javax.swing.*;

public class RegistroControlador {
    public void procesarRegistro(String nombre, String contrasena, String correo, Integer idRol, JFrame ventanaActual) {
        if (Usuario.agregarUsuario(nombre, contrasena, correo, idRol)) {
            ventanaActual.dispose();

            MenuPrincipalPantalla menuPrincipalPantalla =new MenuPrincipalPantalla();
            ControladorDePantallas.asignarContenedor(menuPrincipalPantalla.getPanelContenedor());
            ControladorDePantallas.mostrarPantalla(ControladorDePantallas.PANTALLA_MENU_PRINCIPAL);
            menuPrincipalPantalla.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
        }
    }
}