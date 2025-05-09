package org.cyberpath.controlador.Audio;

import java.io.IOException;

public class SalidaAudioControlador {

    /**
     * Reproduce un texto hablado en español usando eSpeak.
     *
     * @param texto El texto que se desea pronunciar.
     */
    public static void hablar(String texto) {
        try {
            // Ejecutar eSpeak con idioma español (es)
            Process proceso = new ProcessBuilder("espeak-ng", "-v", "es", texto).start();
            proceso.waitFor(); // Espera a que termine de hablar antes de continuar (opcional)
            System.out.println("Acabó la reproducción");
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Error al reproducir el audio: " + e.getMessage());
        }
    }
}