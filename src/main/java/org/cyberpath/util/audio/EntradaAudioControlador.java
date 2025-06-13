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

    private static volatile EntradaAudioControlador instancia = null;
    private static final Object LOCK = new Object();

    private volatile TargetDataLine lineaActiva = null;
    private final AtomicBoolean escuchando = new AtomicBoolean(false);
    private final Model model;
    private final AudioFormat formato;
    private final byte[] buffer;
    private final Sistema sistema = Sistema.getInstance();

    private EntradaAudioControlador(String modeloPath) throws IOException {
        LibVosk.setLogLevel(LogLevel.WARNINGS);
        this.model = new Model(modeloPath);
        this.formato = new AudioFormat(16000, 16, 1, true, false);
        this.buffer = new byte[4096];
    }

    public static EntradaAudioControlador getInstance() throws IOException {
        if (instancia == null) {
            synchronized (LOCK) {
                if (instancia == null) {
                    instancia = new EntradaAudioControlador("src/main/resources/stt/model-es/vosk-model-small-es-0.42");
                }
            }
        }
        return instancia;
    }

    public synchronized void detenerEscucha() {
        escuchando.set(false);
        if (lineaActiva != null) {
            try {
                if (lineaActiva.isRunning()) {
                    lineaActiva.stop();
                }
                if (lineaActiva.isOpen()) {
                    lineaActiva.close();
                }
                System.out.println("🔇 Escucha de audio detenida.");
            } catch (Exception e) {
                System.err.println("⚠️ Error al detener la escucha: " + e.getMessage());
            } finally {
                lineaActiva = null;
            }
        }
    }

    public boolean entradaAfirmacionNegacion() throws Exception {
        detenerEscucha();

        try (Recognizer recognizer = new Recognizer(model, 16000.0f)) {
            lineaActiva = obtenerLineaDeMicrofonoCompatible();
            lineaActiva.open(formato);
            lineaActiva.start();
            escuchando.set(true);

            System.out.println("🎤 Esperando respuesta: sí o no...");

            while (escuchando.get()) {
                int nbytes = lineaActiva.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    String texto = new JSONObject(recognizer.getResult()).getString("text").toLowerCase();
                    System.out.println("📝 Texto reconocido: '" + texto + "'");

                    if (texto.contains("sí") || texto.contains("aceptar")) return true;
                    if (texto.contains("no")) return false;

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

        JSONArray jsonPalabras = new JSONArray(palabrasClave);
        try (Recognizer recognizer = new Recognizer(model, 16000.0f, jsonPalabras.toString())) {
            lineaActiva = obtenerLineaDeMicrofonoCompatible();
            lineaActiva.open(formato);
            lineaActiva.start();
            escuchando.set(true);

            System.out.println("🎤 Esperando una palabra clave (≥70% similitud): " + String.join(", ", palabrasClave));

            while (escuchando.get()) {
                int nbytes = lineaActiva.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, nbytes)) {
                    String texto = new JSONObject(recognizer.getResult()).getString("text").toLowerCase().trim();
                    System.out.println("📝 Texto reconocido: '" + texto + "'");

                    for (String palabra : palabrasClave) {
                        if (calcularSimilitud(texto, palabra.toLowerCase()) >= 0.85) {
                            System.out.println("✅ Coincidencia con: " + palabra);
                            return palabra;
                        }
                    }
                    System.out.println("❌ Ninguna coincidencia suficiente.");
                } else {
                    String parcial = new JSONObject(recognizer.getPartialResult()).getString("partial").toLowerCase().trim();
                    for (String palabra : palabrasClave) {
                        if (calcularSimilitud(parcial, palabra.toLowerCase()) >= 0.85) {
                            System.out.println("✅ Coincidencia parcial con: " + palabra);
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
            lineaActiva.open(formato);
            lineaActiva.start();
            escuchando.set(true);

            long inicio = System.currentTimeMillis();
            while (escuchando.get() && System.currentTimeMillis() - inicio < 1000) {
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
                        line.open(formato);
                        System.out.println("🎤 Usando micrófono: " + info.getName());
                        return line;
                    } catch (LineUnavailableException | IllegalArgumentException ignored) {
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
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        int distancia = dp[a.length()][b.length()];
        return 1.0 - ((double) distancia / Math.max(a.length(), b.length()));
    }
}
