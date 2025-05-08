package org.cyberpath.controlador;

import org.cyberpath.modelo.entidades.usuario.Rol;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import javax.swing.*;

public class RegistroControlador {
    public Boolean procesarRegistro(String nombre, String contrasena, String correo, Integer id_rol, JFrame ventanaActual) throws Exception {
        if (Rol.registroRolVerificacion(id_rol)) {
            //La contraseña es 123, solo es para que un usuario normal no pueda crear una cuenta de adminsitrador
            if (Usuario.agregar(nombre, contrasena, correo, id_rol)) {
                ventanaActual.dispose();
                MenuPrincipalVentana menuPrincipalPantalla =new MenuPrincipalVentana();
                PantallasControlador.asignarContenedor(menuPrincipalPantalla.getPanelContenedor());
                menuPrincipalPantalla.setVisible(true);
                return true;
            } else {
                JOptionPane.showMessageDialog(ventanaActual, Salidas.registroError);
                ventanaActual.dispose();
                new InicioVentana().setVisible(true);
                return false;
            }
        }
        JOptionPane.showMessageDialog(ventanaActual, Salidas.contrasenaRolError);
        ventanaActual.dispose();
        new InicioVentana().setVisible(true);
        return false;
    }
}