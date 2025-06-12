package org.cyberpath.util.audio;

import org.cyberpath.util.Sistema;
import org.json.JSONArray;
import org.json.JSONObject;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntradaAudioControlador {

    private static volatile TargetDataLine lineaActiva;
    private static AtomicBoolean escuchando;
    private static EntradaAudioControlador entradaAudioControlador = null;
    private final Model model;
    private final AudioFormat format;
    private final byte[] buffer;
    private Sistema sistema = Sistema.getInstance();

    private EntradaAudioControlador(String modeloPath) throws IOException {
        LibVosk.setLogLevel(LogLevel.WARNINGS);
        this.model = new Model(modeloPath);
        this.format = new AudioFormat(16000, 16, 1, true, false);
        this.buffer = new byte[4096];
        escuchando = new AtomicBoolean(false);
    }

    public static EntradaAudioControlador getInstance() throws IOException {
        if (entradaAudioControlador == null) {
            entradaAudioControlador = new EntradaAudioControlador("src/main/resources/stt/model-es/vosk-model-small-es-0.42");
        }
        return entradaAudioControlador;
    }

    public void detenerEscucha() {
        sistema.pausa(1);
        escuchando.set(false);
        if (lineaActiva != null && lineaActiva.isOpen()) {
            lineaActiva.stop();
            lineaActiva.close();
            System.out.println("🔇 Escucha de audio detenida.");
        }
    }

    public boolean entradaAfirmacionNegacion() throws Exception {
        detenerEscucha();
        try (Recognizer recognizer = new Recognizer(model, 16000.0f)) {
            lineaActiva = obtenerLineaDeMicrofonoCompatible();
            lineaActiva.open(format);
            lineaActiva.start();
            escuchando.set(true);

            System.out.println("🎤 Esperando respuesta: sí o no...");

            while (escuchando.get()) {
                int nbytes = lineaActiva.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    String textoReconocido = new JSONObject(recognizer.getResult()).getString("text").toLowerCase();
                    System.out.println("📝 Texto reconocido: '" + textoReconocido + "'");

                    if (textoReconocido.contains("sí") || textoReconocido.contains("aceptar")) return true;
                    if (textoReconocido.contains("no")) return false;

                    System.out.println("❌ No entendí su respuesta. Por favor diga 'sí' o 'no'.");
                } else {
                    System.out.println("⏳ Parcial: " + recognizer.getPartialResult());
                }
            }
        } finally {
            detenerEscucha();
        }
        throw new InterruptedException("Escucha interrumpida externamente.");
    }

    public String esperarPorPalabrasClave(String[] palabrasClave) throws Exception {
        detenerEscucha();
        try (Recognizer recognizer = new Recognizer(model, 16000.0f, new JSONArray(palabrasClave).toString())) {
            lineaActiva = obtenerLineaDeMicrofonoCompatible();
            lineaActiva.open(format);
            lineaActiva.start();
            escuchando.set(true);

            System.out.println("🎤 Esperando una de las palabras clave (≥70% de similitud): " + String.join(", ", palabrasClave));

            while (escuchando.get()) {
                int nbytes = lineaActiva.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    String textoReconocido = new JSONObject(recognizer.getResult()).getString("text").toLowerCase().trim();
                    System.out.println("📝 Texto reconocido: '" + textoReconocido + "'");
                    for (String palabra : palabrasClave) {
                        double similitud = calcularSimilitud(textoReconocido, palabra.toLowerCase());
                        if (similitud >= 0.7) {
                            System.out.println("✅ Coincidencia con: " + palabra + " (" + Math.round(similitud * 100) + "%)");
                            return palabra;
                        }
                    }
                    System.out.println("❌ Ninguna coincidencia suficiente.");
                } else {
                    String parcial = new JSONObject(recognizer.getPartialResult()).getString("partial").toLowerCase().trim();
                    for (String palabra : palabrasClave) {
                        double similitud = calcularSimilitud(parcial, palabra.toLowerCase());
                        if (similitud >= 0.7) {
                            System.out.println("✅ Coincidencia parcial con: " + palabra + " (" + Math.round(similitud * 100) + "%)");
                            return palabra;
                        }
                    }
                }
            }
        } finally {
            detenerEscucha();
        }
        throw new InterruptedException("Escucha interrumpida externamente.");
    }


    public void inicializarReconocimiento() throws Exception {
        detenerEscucha();
        try (Recognizer recognizer = new Recognizer(model, 16000.0f)) {
            lineaActiva = obtenerLineaDeMicrofonoCompatible();
            lineaActiva.open(format);
            lineaActiva.start();
            escuchando.set(true);

            long tiempoInicio = System.currentTimeMillis();
            while (escuchando.get() && System.currentTimeMillis() - tiempoInicio < 1000) {
                int nbytes = lineaActiva.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    new JSONObject(recognizer.getResult()).getString("text");
                } else {
                    System.out.println("⏳ Cargando...");
                }
            }
            System.out.println("✅ Reconocimiento inicializado.");
        } finally {
            detenerEscucha();
        }
    }

    private TargetDataLine obtenerLineaDeMicrofonoCompatible() throws LineUnavailableException {
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(info);
            for (Line.Info lineInfo : mixer.getTargetLineInfo()) {
                if (TargetDataLine.class.isAssignableFrom(lineInfo.getLineClass())) {
                    try {
                        TargetDataLine line = (TargetDataLine) mixer.getLine(lineInfo);
                        line.open(format); // Probar compatibilidad
                        System.out.println("🎤 Usando micrófono: " + info.getName());
                        return line;
                    } catch (LineUnavailableException | IllegalArgumentException e) {
                        // Ignorar micrófonos incompatibles
                    }
                }
            }
        }
        throw new LineUnavailableException("⚠️ No se encontró ningún micrófono compatible.");
    }

    private double calcularSimilitud(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }

        int distancia = dp[a.length()][b.length()];
        int longitudMax = Math.max(a.length(), b.length());
        return 1.0 - ((double) distancia / longitudMax);
    }
}
