package org.cyberpath.util;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;

public class LecturaAudio {

    public static void main(String[] args) throws Exception {
        boolean respuesta = lecturaAudioUsuario();
        System.out.println("¿Respuesta afirmativa?: " + respuesta);
    }

    public static boolean lecturaAudioUsuario() throws Exception {
        LibVosk.setLogLevel(LogLevel.WARNINGS);

        String[] palabrasClave = {
                "si", "sip", "aceptar", "ajá", "correcto",
                "no", "negativo", "nop", "incorrecto"
        };

        String grammar = "[\"" + String.join("\",\"", palabrasClave) + "\"]";

        try (Model model = new Model("src/main/resources/voz/model-es/vosk-model-small-es-0.42");
             Recognizer recognizer = new Recognizer(model, 16000.0f, grammar)) {

            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                throw new LineUnavailableException("Micrófono no soportado");
            }

            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("🎤 Escuchando (di 'sí', 'no', etc.)...");

            byte[] buffer = new byte[4096];
            while (true) {
                int nbytes = line.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    String resultado = recognizer.getResult();
                    System.out.println("📝 Resultado: " + resultado);

                    String texto = resultado.replaceAll(".*\"text\"\\s*:\\s*\"(.*?)\".*", "$1").toLowerCase();

                    try {
                        boolean respuesta = esRespuestaPositivaONegativa(texto);
                        line.stop();
                        line.close();
                        return respuesta;
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ " + e.getMessage());
                    }
                } else {
                    System.out.println("⏳ Parcial: " + recognizer.getPartialResult());
                }
            }
        }
    }

    public static boolean esRespuestaPositivaONegativa(String respuesta) {
        String[] positivas = {"si", "sip", "aceptar", "ajá", "correcto"};
        String[] negativas = {"no", "negativo", "nop", "incorrecto"};

        respuesta = respuesta.trim().toLowerCase();

        for (String p : positivas) {
            if (respuesta.contains(p)) return true;
        }
        for (String n : negativas) {
            if (respuesta.contains(n)) return false;
        }

        throw new IllegalArgumentException("Respuesta no reconocida como afirmativa ni negativa: " + respuesta);
    }
}
