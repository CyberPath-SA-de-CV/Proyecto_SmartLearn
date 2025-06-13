package org.cyberpath.controlador.combo;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.AccesibilidadVentana;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class AccesibilidadControlador {

    public static EntradaAudioControlador sttControlador;
    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

    public static void procesarAccesibilidad(JFrame window) throws Exception {
        if (VariablesGlobales.auxModoAudio) {
            String[] opciones = {"cambiar", "cambiar modo audio", "audio", "volver", "volver a presentar"};
            ttsControlador.hablar("¿Qué deseas hacer?, cambiar el modo de audio o volver a presentar el menú", 5);
            String seleccion = sttControlador.esperarPorPalabrasClave(opciones);
            if (!Objects.equals(seleccion, "volver") && !Objects.equals(seleccion, "volver a presentar")) {
                VariablesGlobales.auxModoAudio = !VariablesGlobales.auxModoAudio; // Alternar estado
                ttsControlador.hablar("La voz está ahora " + (VariablesGlobales.auxModoAudio ? "activada" : "desactivada") + ".", 5);
            } else {
                if (PantallasControlador.menuAccesibilidad("Accesibilidad", window)) {
                    AccesibilidadControlador.procesarAccesibilidad(window);
                }
            }
        }
    }
}
