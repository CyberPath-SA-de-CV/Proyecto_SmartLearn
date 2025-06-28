package org.cyberpath.vista.pantallas.cuenta;

import org.cyberpath.util.Sistema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.controlador.usuario.InicioSesionControlador;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class InicioSesionVentanta extends JFrame {

    private static final EntradaAudioControlador sttControlador;
    private static final SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;

    public InicioSesionVentanta() throws Exception {
        configurarVentana();
        campoUsuario = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        configurarModoAudio();
        JPanel panel = crearPanel();
        agregarComponentesAlPanel(panel);
        add(panel, BorderLayout.CENTER);
    }

    private void configurarVentana() {
        setTitle("Login");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursosGraficos/logos/logo.png")));
        setBackground(new Color(5, 100, 110));
    }

    private void configurarModoAudio() {
        if (VariablesGlobales.auxModoAudio) {
            sttControlador.detenerEscucha();
            ttsControlador.hablar("Bienvenido a la ventana de inicio de sesión. Use la tecla Tabulador para cambiar entre campos y Alt más i para iniciar sesión, se hará saber cuando pueda empezar a escribir", 14);
            agregarFocusListeners();
        }
    }

    private void agregarFocusListeners() {
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

    private JPanel crearPanel() {
        JPanel panel = new PanelConRayasVerticales();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(5, 100, 110));
        return panel;
    }

    private void agregarComponentesAlPanel(JPanel panel) {
        JLabel usrEtiqueta = crearEtiqueta("Usuario:");
        JLabel pswdEtiqueta = crearEtiqueta("Contraseña:");
        configurarEtiquetas(usrEtiqueta, pswdEtiqueta);

        JButton botonLogin = crearBotonEstilizado("Login", null, e -> {
            try {
                realizarInicioSesion();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        botonLogin.setFocusable(false);
        configurarBoton(botonLogin, KeyEvent.VK_I);

        JButton botonVolver = crearBotonEstilizado("Volver a Inicio", null, e -> volverAInicio());
        botonVolver.setFocusable(false);
        configurarAtajosDeTeclado(panel);
        agregarComponentes(panel, usrEtiqueta, pswdEtiqueta, botonLogin, botonVolver);
    }

    private void configurarEtiquetas(JLabel usrEtiqueta, JLabel pswdEtiqueta) {
        usrEtiqueta.setFont(usrEtiqueta.getFont().deriveFont(20f));
        pswdEtiqueta.setFont(pswdEtiqueta.getFont().deriveFont(20f));
        usrEtiqueta.setForeground(Color.WHITE);
        pswdEtiqueta.setForeground(Color.WHITE);
    }

    private void configurarBoton(JButton boton, int mnemonic) {
        boton.setFocusable(false);
        boton.setMnemonic(mnemonic); // Alt + I
    }

    private void volverAInicio() {
        try {
            new InicioVentana().setVisible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        dispose();
    }

    private void configurarAtajosDeTeclado(JPanel panel) {
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

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "salir");
        actionMap.put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAInicio();
            }
        });
    }

    private void agregarComponentes(JPanel panel, JLabel usrEtiqueta, JLabel pswdEtiqueta, JButton botonLogin, JButton botonVolver) {
        panel.add(usrEtiqueta, crearConstraint(0, 0, 1, 1, 10));
        panel.add(campoUsuario, crearConstraint(0, 1, 2, 1, 60));
        panel.add(pswdEtiqueta, crearConstraint(1, 0, 1, 1, 10));
        panel.add(campoContrasena, crearConstraint(1, 1, 2, 1, 60));
        panel.add(botonLogin, crearConstraintBotonAncho(2, 0, 3, 1, 200));
        panel.add(botonVolver, crearConstraintBotonAncho(3, 0, 3, 1, 200));
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
