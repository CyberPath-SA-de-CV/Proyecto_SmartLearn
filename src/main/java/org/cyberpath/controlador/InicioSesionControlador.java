package org.cyberpath.controlador;

import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.vista.pantallas.MenuPrincipalVentana;

import javax.swing.*;

public class InicioSesionControlador {
    public void procesarInicio(String nombre, String contrasena, JFrame ventanaActual){
        boolean valido = Usuario.validarCredenciales(nombre, contrasena);

        if(valido){
            //crear una función que convierta si en true y no en false
            //VariablesGlobales.reproduccionGlobalAudio = opcionAudio == "si"; // ESTO SE MODIFICA EN LA VENTANA PRINCIPAL AL PREGUNTAR
            ventanaActual.dispose();
            //Aqui va el menú principal de la aplicacion
            System.out.println("Menu c:");
            new MenuPrincipalVentana().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
        }

    }
}
