package org.cyberpath.vista.pantallas.inicio;

import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SplashVentana extends JFrame {

    private static EntradaAudioControlador sttControlador;
    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

    //---

    private JLabel mensaje;
    private JProgressBar barraCarga;

    public SplashVentana() throws IOException {
        setUndecorated(true);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Imagen decorativa centrada arriba
        ImageIcon iconoSuperior = new ImageIcon("src/main/resources/recursosGraficos/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoSuperior.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel imagenCentrada = new JLabel(new ImageIcon(imagenEscalada));
        imagenCentrada.setHorizontalAlignment(SwingConstants.CENTER);
        add(imagenCentrada, BorderLayout.NORTH);

        mensaje = new JLabel("Cargando Smart-Learn...", SwingConstants.CENTER);
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 22));

        barraCarga = new JProgressBar(0, 100);
        barraCarga.setStringPainted(true);
        barraCarga.setForeground(new Color(30, 144, 255));
        barraCarga.setBackground(Color.LIGHT_GRAY);

        add(mensaje, BorderLayout.CENTER);
        add(barraCarga, BorderLayout.SOUTH);
    }

    public void iniciarCarga() {
        setVisible(true);

        // Hilo de animación de la barra de carga
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int progreso = i;
                SwingUtilities.invokeLater(() -> barraCarga.setValue(progreso));
                try {
                    Thread.sleep(60); // Tiempo de animación
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Después del 100%, iniciar voz y lógica de audio
            SwingUtilities.invokeLater(() -> {
                realizarDeteccionVozYContinuar();
            });
        }).start();

        // Cargar datos en segundo plano (usuarios, etc.)
        new Thread(() -> {
            try {
                sttControlador.inicializarReconocimiento();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                List<Usuario> usuarios = Usuario.usuarioDao.findAll();
                System.out.println("✔️ Usuarios cargados: " + usuarios.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void realizarDeteccionVozYContinuar() {
        // Preguntar con TTS
        String mensaje = "¿Desea continuar con el modo de audio activado? Por favor responda sí o no en voz alta";
        ttsControlador.hablar(mensaje);

        // Capturar respuesta con voz
        new Thread(() -> {
            try {
                boolean respuesta = sttControlador.entradaAfirmacionNegacion();
                VariablesGlobales.auxModoAudio = respuesta;

                String confirmacion = respuesta
                        ? "Muy bien, continuemos con el modo de guía con audio activado."
                        : "Perfecto, continuemos sin modo de guía por voz.";

                ttsControlador.hablar(confirmacion, 4);
                try {
                    Thread.sleep(3000); // 2000 milisegundos = 2 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Abrir ventana de inicio
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    try {
                        new InicioVentana().setVisible(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
