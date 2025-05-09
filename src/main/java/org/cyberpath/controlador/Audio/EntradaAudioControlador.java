package org.cyberpath.controlador.Audio;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;

public class EntradaAudioControlador {

    public static void main(String[] args) throws Exception {
        // Prueba de la función obtenerRespuestaBooleano
        boolean respuesta = entradaAfirmacionNegacion();
        System.out.println("🗣 Respuesta final: " + respuesta);
    }

    /**
     * Función que maneja la detección de voz y convierte las respuestas "sí" o "no" en un valor booleano.
     * @return true si la respuesta es "sí", false si la respuesta es "no".
     * @throws Exception Si ocurre un error durante el proceso de reconocimiento de voz.
     */

    public static boolean entradaAfirmacionNegacion() throws Exception {
        // Establecemos el nivel de logs
        LibVosk.setLogLevel(LogLevel.WARNINGS);

        try (Model model = new Model("src/main/resources/stt/model-es/vosk-model-small-es-0.42");
             Recognizer recognizer = new Recognizer(model, 16000.0f)) {

            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Micrófono no soportado");
                return false;
            }

            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("🎤 Esperando respuesta: sí o no...");

            byte[] buffer = new byte[4096];
            while (true) {
                int nbytes = line.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    String resultado = recognizer.getResult();
                    System.out.println("📝 Resultado: " + resultado);

                    // Llamamos a la función para convertir la respuesta
                    boolean respuesta = detectarRespuesta(resultado);
                    return respuesta; // Devolvemos el resultado (true o false)
                } else {
                    System.out.println("⏳ Parcial: " + recognizer.getPartialResult());
                }
            }
        }
    }

    /**
     * Función que convierte la respuesta "sí" o "no" en un valor booleano.
     * @param resultado El texto reconocido por el modelo Vosk.
     * @return true si la respuesta es "sí", false si la respuesta es "no".
     */
    public static boolean detectarRespuesta(String resultado) {
        // Convertimos el resultado a minúsculas para evitar problemas de mayúsculas/minúsculas
        String respuesta = resultado.toLowerCase();

        // Verificamos si la palabra "sí" o "no" está en el resultado
        if (respuesta.contains("sí") || respuesta.contains("aceptar")) {
            return true; // Si dice "sí", retorna true
        } else if (respuesta.contains("no")) {
            return false; // Si dice "no", retorna false
        }

        // Si no contiene "sí" ni "no", devolvemos false por defecto
        return false;
    }
}