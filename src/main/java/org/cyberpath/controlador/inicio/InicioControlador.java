package org.cyberpath.controlador.inicio;

import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.cuenta.InicioSesionVentanta;
import org.cyberpath.vista.pantallas.cuenta.RegistroVentana;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class InicioControlador {

    public static void procesarAccesiblidad(Window window) throws Exception {
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
        if (VariablesGlobales.auxModoAudio) {
            String[] palabras = {"inicio de sesión", "registro de usuario"};
            String entradaUsuario = sttControlador.esperarPorPalabrasClave(palabras);
            System.out.println(entradaUsuario);
            if (window != null) window.dispose();
            if (Objects.equals(entradaUsuario, "inicio de sesión")) {
                new InicioSesionVentanta().setVisible(true);
            } else if (Objects.equals(entradaUsuario, "registro de usuario")) {
                new RegistroVentana().setVisible(true);
            } else {
                System.exit(0);
            }
        }
    }


}