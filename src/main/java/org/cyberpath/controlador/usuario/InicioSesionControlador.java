package org.cyberpath.controlador.usuario;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import javax.swing.*;

public class InicioSesionControlador {

    public static void procesarInicioSesion(String nombre, String contrasena, JFrame ventanaActual) throws Exception {
        boolean credencialesValidas = Usuario.validarCredenciales(nombre, contrasena);

        if (credencialesValidas) {
            ventanaActual.dispose();

            VariablesGlobales.usuario.setModoAudio(VariablesGlobales.auxModoAudio);
            Usuario.actualizar(VariablesGlobales.usuario);

            MenuPrincipalVentana menuPrincipal = new MenuPrincipalVentana();
            PantallasControlador.asignarContenedor(menuPrincipal.getPanelContenedor());
            menuPrincipal.setVisible(true);

            System.out.println("Modo audio actual del usuario: " + VariablesGlobales.usuario.getModoAudio());
        } else {
            manejarInicioSesionFallido(ventanaActual);
        }
    }

    private static void manejarInicioSesionFallido(JFrame ventanaActual) throws Exception {
        if (VariablesGlobales.auxModoAudio) {
            SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
            tts.hablar("No hay un usuario con ese nombre o contraseña");

            Thread.sleep(1000);
        } else {
            JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
        }
    }
}
