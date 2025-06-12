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
        detener();  // Interrumpe cualquier audio en curso
        try {
            ProcessBuilder pb = new ProcessBuilder("espeak-ng", "-v", "es", texto);
            procesoVoz = pb.start();
            System.out.println("🔊 Reproduciendo: " + texto);
        } catch (IOException e) {
            System.err.println("❌ Error al reproducir el audio: " + e.getMessage());
        }

    }

    public void hablar(String texto, Integer tiempoPausa) {
        detener();  // Interrumpe cualquier audio en curso
        try {
            ProcessBuilder pb = new ProcessBuilder("espeak-ng", "-v", "es", texto);
            procesoVoz = pb.start();
            System.out.println("🔊 Reproduciendo: " + texto);
        } catch (IOException e) {
            System.err.println("❌ Error al reproducir el audio: " + e.getMessage());
        }
        sistema.pausa(tiempoPausa);
    }

    public void detener() {
        if (procesoVoz != null && procesoVoz.isAlive()) {
            procesoVoz.destroy();
            System.out.println("🔇 Reproducción interrumpida");
        }
    }

}
