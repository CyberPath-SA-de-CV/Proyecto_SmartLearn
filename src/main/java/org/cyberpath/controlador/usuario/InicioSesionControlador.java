package org.cyberpath.controlador.usuario;

import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import javax.swing.*;

public class InicioSesionControlador {
    public static void procesarInicioSesion(String nombre, String contrasena, JFrame ventanaActual) throws Exception {
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
        boolean valido = Usuario.validarCredenciales(nombre, contrasena);
        if(valido){
            ventanaActual.dispose();
            VariablesGlobales.usuario.setModoAudio(VariablesGlobales.auxModoAudio);
            Usuario.actualizar(VariablesGlobales.usuario);
            MenuPrincipalVentana menuPrincipalPantalla = new MenuPrincipalVentana();
            PantallasControlador.asignarContenedor(menuPrincipalPantalla.getPanelContenedor());
            menuPrincipalPantalla.setVisible(true);
            System.out.println(VariablesGlobales.usuario.getModoAudio());
        } else {
            if (VariablesGlobales.auxModoAudio) {
                ttsControlador.hablar("No hay un usuario con ese nombre o contraseña");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
            }
        }
    }

}
