package org.cyberpath.vista.pantallas.inicio;

import org.cyberpath.controlador.inicio.InicioControlador;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.cuenta.InicioSesionVentanta;
import org.cyberpath.vista.pantallas.cuenta.RegistroVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class
InicioVentana extends JFrame {

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

    private JPanel panelPrincipal;
    private JButton botonLogin;
    private JButton botonRegistro;
    private JButton botonSalirApp;

    public InicioVentana() throws Exception {
        super("Smart-Learn");
        System.out.println(VariablesGlobales.auxModoAudio);
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        inicializarComponentes();
        agregarEventos();
        new Thread(() -> {
            try {
                InicioControlador.procesarAccesiblidad(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                new InicioVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void inicializarComponentes() throws Exception {
        panelPrincipal = new PanelConRayasVerticales();
        panelPrincipal.setLayout(new GridBagLayout());

        // Imagen decorativa arriba a la izquierda
        ImageIcon iconoSuperiorIzquierdo = new ImageIcon("src/main/resources/recursosGraficos/titulos/inicio.jpg");
        Image imagenEscalada = iconoSuperiorIzquierdo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel labelImagenIzquierda = new JLabel(new ImageIcon(imagenEscalada));
        GridBagConstraints gbcImagen = new GridBagConstraints();
        gbcImagen.gridx = 0;
        gbcImagen.gridy = 0;
        gbcImagen.anchor = GridBagConstraints.NORTHWEST;
        gbcImagen.insets = new Insets(10, 10, 0, 0);
        panelPrincipal.add(labelImagenIzquierda, gbcImagen);

        // Etiqueta de bienvenida
        JLabel mensaje = crearEtiqueta("¡Bienvenido a Smart Learn!");
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mensaje.setForeground(new Color(185, 222, 245));
        panelPrincipal.add(mensaje, crearConstraintBotonAncho(0, 0, 3, 1, 100));

        // Botón Login
        botonLogin = crearBotonEstilizado("Iniciar Sesión", null, null);
        botonLogin.setMnemonic(KeyEvent.VK_I); // Alt + I
        panelPrincipal.add(botonLogin, crearConstraintBotonAncho(1, 0, 3, 1, 100));

        // Botón Registro
        botonRegistro = crearBotonEstilizado("Registrar Usuario", null, null);
        botonRegistro.setMnemonic(KeyEvent.VK_R); // Alt + R
        panelPrincipal.add(botonRegistro, crearConstraintBotonAncho(2, 0, 3, 1, 100));

        // Botón Salir
        botonSalirApp = crearBotonEstilizado("Salir de la App", null, null);
        botonSalirApp.setMnemonic(KeyEvent.VK_S); // Alt + S
        panelPrincipal.add(botonSalirApp, crearConstraintBotonAncho(3, 0, 3, 1, 100));

        getRootPane().setDefaultButton(botonLogin);
        setContentPane(panelPrincipal);

        if (VariablesGlobales.auxModoAudio) {
            new Thread(() -> {
                try {
                    Thread.sleep(2500); // breve pausa para sincronizar TTS
                    ttsControlador.hablar("Bienvenido a Smart Learn. diga que desea hacer en voz alta o Presione Alt más I para iniciar sesión, Alt más R para registrar un usuario, o Alt más S para salir de la aplicación");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void agregarEventos() {
        botonLogin.addActionListener(e -> {
            try {
                new InicioSesionVentanta().setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Entrando a Login");
            dispose();
        });

        botonRegistro.addActionListener(e -> {
            new RegistroVentana().setVisible(true);
            System.out.println("Entrando a Registro Usr");
            dispose();
        });

        botonSalirApp.addActionListener(e -> {
            System.out.println("Saliendo de la aplicación");
            System.exit(0);
        });
    }
}
