package org.cyberpath.controlador.usuario;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;
import org.cyberpath.modelo.entidades.usuario.Rol;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;

import javax.swing.*;

public class RegistroControlador {

    public Boolean procesarRegistro(String nombre, String contrasena, String correo, Integer idRol, JFrame ventanaActual) throws Exception {
        if (!Rol.registroRolVerificacion(idRol)) {
            mostrarMensajeError(ventanaActual, Salidas.contrasenaRolError);
            return false;
        }

        boolean registroExitoso = Usuario.agregar(nombre, contrasena, correo, idRol);

        if (registroExitoso) {
            ventanaActual.dispose();
            abrirMenuPrincipal();
            return true;
        } else {
            mostrarMensajeError(ventanaActual, Salidas.registroError);
            return false;
        }
    }

    private void mostrarMensajeError(JFrame ventana, String mensaje) throws Exception {
        JOptionPane.showMessageDialog(ventana, mensaje);
        ventana.dispose();
        new InicioVentana().setVisible(true);
    }

    private void abrirMenuPrincipal() throws Exception {
        MenuPrincipalVentana menuPrincipal = new MenuPrincipalVentana();
        PantallasControlador.asignarContenedor(menuPrincipal.getPanelContenedor());
        menuPrincipal.setVisible(true);
        PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
    }
}
