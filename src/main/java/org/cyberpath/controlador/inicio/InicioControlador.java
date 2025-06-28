package org.cyberpath.controlador.inicio;

import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.cuenta.InicioSesionVentanta;
import org.cyberpath.vista.pantallas.cuenta.RegistroVentana;

import java.awt.*;
import java.util.Objects;

public class InicioControlador {

    private static final String[] OPCIONES_STT = {
            "inicio de sesión", "registro de usuario", "iniciar sesión", "registrar usuario"
    };

    public static void procesarAccesiblidad(Window window) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return;

        SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
        EntradaAudioControlador stt = EntradaAudioControlador.getInstance();

        tts.hablar(
                "Bienvenido a Smart Learn. Diga qué desea hacer en voz alta o presione Alt más I para iniciar sesión, Alt más R para registrar un usuario, o Alt más S para salir de la aplicación."
        );

        String entrada = stt.esperarPorPalabrasClave(OPCIONES_STT);

        if (window != null) {
            window.dispose();
        }

        if (entrada.equals("inicio de sesión") || entrada.equals("iniciar sesión")) {
            new InicioSesionVentanta().setVisible(true);
        } else if (entrada.equals("registro de usuario") || entrada.equals("registrar usuario")) {
            new RegistroVentana().setVisible(true);
        } else {
            System.exit(0);
        }
    }
}
