package org.cyberpath.controlador.combo;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;

import javax.swing.*;
import java.util.Objects;

public class AccesibilidadControlador {

    private static final String[] opciones_voz = {
            "cambiar", "cambiar modo audio", "audio", "volver", "volver a presentar"
    };

    public static void procesarAccesibilidad(JFrame window) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return;

        SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
        EntradaAudioControlador stt = EntradaAudioControlador.getInstance();

        tts.hablar("¿Qué deseas hacer? Cambiar el modo de audio o volver a presentar el menú", 5);
        String seleccion = stt.esperarPorPalabrasClave(opciones_voz);

        if (alternarAudio(seleccion)) {
            alternarModoAudio(tts);
        } else if (repetirMenu(seleccion)) {
            if (PantallasControlador.menuAccesibilidad("Accesibilidad", window)) {
                procesarAccesibilidad(window);
            }
        }
    }

    private static boolean alternarAudio(String seleccion) {
        return !Objects.equals(seleccion, "volver") &&
                !Objects.equals(seleccion, "volver a presentar");
    }

    private static boolean repetirMenu(String seleccion) {
        return Objects.equals(seleccion, "volver") ||
                Objects.equals(seleccion, "volver a presentar");
    }

    private static void alternarModoAudio(SalidaAudioControlador tts) throws Exception {
        VariablesGlobales.auxModoAudio = !VariablesGlobales.auxModoAudio;
        String estado = VariablesGlobales.auxModoAudio ? "activada" : "desactivada";
        tts.hablar("La voz está " + estado, 5);
    }
}
