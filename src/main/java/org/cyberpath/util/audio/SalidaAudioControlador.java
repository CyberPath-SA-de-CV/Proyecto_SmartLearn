package org.cyberpath.util.audio;

import org.cyberpath.util.Sistema;

import java.io.IOException;

public class SalidaAudioControlador {

    private static Process procesoVoz;
    private static SalidaAudioControlador salidaAudioControlador = null;
    private Sistema sistema = Sistema.getInstance();

    public SalidaAudioControlador() {
    }

    public static SalidaAudioControlador getInstance() {
        if (salidaAudioControlador == null) {
            salidaAudioControlador = new SalidaAudioControlador();
        }
        return salidaAudioControlador;
    }

    public void hablar(String texto) {
        detener();
        try {
            ProcessBuilder pb = new ProcessBuilder("espeak-ng", "-s 145", "-v", "es", texto);
            procesoVoz = pb.start();
            System.out.println("Reproduciendo: " + texto);
        } catch (IOException e) {
            System.err.println("Error al reproducir el audio: " + e.getMessage());
        }
    }

    public void hablar(String texto, Integer tiempoPausa) {
        hablar(texto);
        sistema.pausa(tiempoPausa);
    }

    public void detener() {
        if (procesoVoz != null && procesoVoz.isAlive()) {
            procesoVoz.destroy();
            System.out.println("Reproducción de audio interrumpida");
        }
    }

}
