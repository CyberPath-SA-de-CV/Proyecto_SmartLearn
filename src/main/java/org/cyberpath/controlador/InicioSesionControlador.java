package org.cyberpath.controlador;

import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;

import javax.swing.*;

public class InicioSesionControlador {
    public void procesarInicio(String nombre, String contrasena, JFrame ventanaActual){
        boolean valido = Usuario.validarCredenciales(nombre, contrasena);
        if(valido){
            ventanaActual.dispose();
            VariablesGlobales.usuario.setModoAudio(VariablesGlobales.auxModoAudio);
            Usuario.actualizar(VariablesGlobales.usuario);
            PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            System.out.println(VariablesGlobales.usuario.getModoAudio());
        } else {
            JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
        }
    }
}
