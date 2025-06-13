package org.cyberpath.vista.pantallas.cuenta;

import org.cyberpath.util.Sistema;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.controlador.usuario.InicioSesionControlador;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

import org.cyberpath.util.VariablesGlobales;
import java.awt.event.*;
import java.io.IOException;

public class InicioSesionVentanta extends JFrame {

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

    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;

    public InicioSesionVentanta() throws Exception {
        setTitle("Login");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursosGraficos/logos/logo.png"));
        setIconImage(icono);
        setBackground(new Color(5, 100, 110));

        campoUsuario = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);

        // Si modo audio está activado, dar indicaciones y activar foco hablado
        if (VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("Bienvenido a la ventana de inicio de sesión. Use la tecla Tabulador para cambiar entre campos y Alt más i para iniciar sesión, se hará saber cuando pueda empezar a escribir");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            campoUsuario.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    ttsControlador.hablar("Campo usuario activo");
                }
            });

            campoContrasena.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    ttsControlador.hablar("Campo contraseña activo");
                }
            });
        }

        JPanel panel = new PanelConRayasVerticales();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(5, 100, 110));

        JLabel usrEtiqueta = crearEtiqueta("Usuario:");
        usrEtiqueta.setFont(usrEtiqueta.getFont().deriveFont(20f));
        JLabel pswdEtiqueta = crearEtiqueta("Contraseña:");
        pswdEtiqueta.setFont(pswdEtiqueta.getFont().deriveFont(20f));
        usrEtiqueta.setForeground(Color.WHITE);
        pswdEtiqueta.setForeground(Color.WHITE);

        JButton botonLogin = crearBotonEstilizado("Login", null, e -> {
            try {
                realizarInicioSesion();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        botonLogin.setFocusable(false);
        botonLogin.setMnemonic(KeyEvent.VK_I); // Alt + I

        // Atajos de teclado para enfocar campos (opcional si usas Tab)
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK), "focoUsuario");
        actionMap.put("focoUsuario", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoUsuario.requestFocusInWindow();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK), "focoContrasena");
        actionMap.put("focoContrasena", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoContrasena.requestFocusInWindow();
            }
        });

        // Atajo para salir: Alt + S
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "salir");
        actionMap.put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new InicioVentana().setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        panel.add(usrEtiqueta, crearConstraint(0, 0, 1, 1, 10));
        panel.add(campoUsuario, crearConstraint(0, 1, 2, 1, 60));
        panel.add(pswdEtiqueta, crearConstraint(1, 0, 1, 1, 10));
        panel.add(campoContrasena, crearConstraint(1, 1, 2, 1, 60));
        panel.add(botonLogin, crearConstraintBotonAncho(2, 0, 3, 1, 200));

        JButton botonVolver = crearBotonEstilizado("Volver a Inicio", null, e -> {
            try {
                new InicioVentana().setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        botonVolver.setFocusable(false);
        panel.add(botonVolver, crearConstraintBotonAncho(3, 0, 3, 1, 200));

        add(panel, BorderLayout.CENTER);
    }

    private void realizarInicioSesion() throws Exception {
        String usuario = campoUsuario.getText();
        String contrasena = new String(campoContrasena.getPassword());

        if (VariablesGlobales.auxModoAudio) {
            new Thread(() -> {
                try {
                    ttsControlador.hablar("Usted ingresó usuario: " + usuario + ". ¿Desea iniciar sesión con estos datos? Diga sí o no.");
                    boolean confirmacion = sttControlador.entradaAfirmacionNegacion();

                    if (confirmacion) {
                        ttsControlador.hablar("Iniciando sesión.", 2);
                        InicioSesionControlador.procesarInicioSesion(usuario, contrasena, this);
                    } else {
                        ttsControlador.hablar("Por favor, corrija los datos e intente de nuevo.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ttsControlador.hablar("Ocurrió un error al reconocer su respuesta.");
                }
            }).start();
        } else {
            InicioSesionControlador.procesarInicioSesion(usuario, contrasena, this);
        }
    }
}
