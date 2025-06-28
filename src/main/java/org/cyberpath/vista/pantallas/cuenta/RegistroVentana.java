package org.cyberpath.vista.pantallas.cuenta;

import org.cyberpath.controlador.usuario.RegistroControlador;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class RegistroVentana extends JFrame {

    private static final EntradaAudioControlador sttControlador;
    private static final SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JPanel panelPrincipal;
    private JButton botonRegistro;
    private JButton botonVolver;
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmar;
    private JComboBox<String> comboRol;

    public RegistroVentana() {
        super("Registro de Usuario");
        configurarVentana();
        inicializarComponentes();
        //ttsControlador.hablar("Bienvenido a la ventana de inicio de sesión. Use la tecla Tabulador para cambiar entre campos y Alt más i para iniciar sesión, se hará saber cuando pueda empezar a escribir", 14);
        agregarEventos();
        setContentPane(panelPrincipal);
        if (VariablesGlobales.auxModoAudio) inicializarAccesibilidad();

    }

    private void configurarVentana() {
        setSize(700, 550);
        setLocationRelativeTo(null); // Centrar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Boolean pedirContrasenaRol() {
        final JDialog dialog = new JDialog((Frame) null, "Ingrese Contraseña", true);
        final JPasswordField passwordField = new JPasswordField(15);
        final String[] contrasenaIngresada = new String[1];

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel(Salidas.contrasenaRol);
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setFocusable(false);

        botonAceptar.addActionListener(e -> {
            contrasenaIngresada[0] = new String(passwordField.getPassword());
            dialog.dispose();
        });

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(label, BorderLayout.NORTH);
        centro.add(passwordField, BorderLayout.CENTER);

        JPanel sur = new JPanel();
        sur.add(botonAceptar);

        panel.add(centro, BorderLayout.CENTER);
        panel.add(sur, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return Objects.equals(contrasenaIngresada[0], VariablesGlobales.contrasenaRol);
    }

    private void inicializarComponentes() {
        panelPrincipal = new PanelConRayasVerticales();
        panelPrincipal.setLayout(new GridBagLayout());

        agregarEtiquetasYCampos();
        agregarBotones();
        getRootPane().setDefaultButton(botonRegistro);
    }

    private void agregarEtiquetasYCampos() {
        JLabel etiquetaTitulo = crearEtiqueta("Registro de Usuario");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        etiquetaTitulo.setForeground(Color.WHITE);
        panelPrincipal.add(etiquetaTitulo, crearConstraintCentrado(0, 0, 3, 1, 10));

        JLabel instrucciones = crearEtiqueta("Complete los siguientes campos:");
        instrucciones.setForeground(Color.WHITE);
        instrucciones.setFont(instrucciones.getFont().deriveFont(15f));
        panelPrincipal.add(instrucciones, crearConstraint(1, 0, 3, 1, 10));

        String[] etiquetas = {"Nombre de usuario:", "Correo electrónico:", "Contraseña:", "Confirmar contraseña:", "Rol:"};
        for (int i = 0; i < etiquetas.length; i++) {
            JLabel label = crearEtiqueta(etiquetas[i]);
            label.setFont(label.getFont().deriveFont(15f));
            label.setForeground(Color.WHITE);
            panelPrincipal.add(label, crearConstraint(i + 2, 0, 1, 1, 20));
        }

        campoNombre = crearCampoTxt(15);
        campoCorreo = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        campoConfirmar = new JPasswordField(15);
        comboRol = new JComboBox<>(new String[]{"Administrador", "Estudiante"});

        panelPrincipal.add(campoNombre, crearConstraint(2, 1, 2, 1, 100));
        panelPrincipal.add(campoCorreo, crearConstraint(3, 1, 2, 1, 100));
        panelPrincipal.add(campoContrasena, crearConstraint(4, 1, 2, 1, 100));
        panelPrincipal.add(campoConfirmar, crearConstraint(5, 1, 2, 1, 100));
        panelPrincipal.add(comboRol, crearConstraint(6, 1, 2, 1, 100));
    }

    private void agregarBotones() {
        botonRegistro = crearBotonEstilizado("Registrar", null, null);
        botonRegistro.setMnemonic(KeyEvent.VK_R); // ALT + R

        botonVolver = crearBotonEstilizado("Volver a Inicio", null, null);
        botonVolver.setMnemonic(KeyEvent.VK_V); // ALT + V

        panelPrincipal.add(botonRegistro, crearConstraintBotonAncho(7, 0, 3, 1, 200));
        panelPrincipal.add(botonVolver, crearConstraintBotonAncho(8, 0, 3, 1, 200));
    }

    private void agregarEventos() {
        botonRegistro.addActionListener(e -> registrarUsuario());
        botonVolver.addActionListener(e -> volverAInicio());
    }

    private void registrarUsuario() {
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String contra = new String(campoContrasena.getPassword());
        String confirmar = new String(campoConfirmar.getPassword());
        int idRol = comboRol.getSelectedIndex() == 0 ? 1 : 2;

        if (validarCampos(nombre, correo, contra, confirmar)) {
            if (VariablesGlobales.auxModoAudio) {
                confirmarRegistroPorVoz(nombre, correo);
            } else {
                procesarRegistro(nombre, contra, correo, idRol);
            }
        }
    }

    private boolean validarCampos(String nombre, String correo, String contra, String confirmar) {
        if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() || confirmar.isEmpty()) {
            mostrarMensaje("Por favor complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostrarMensaje("Correo electrónico inválido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!contra.equals(confirmar)) {
            mostrarMensaje("Las contraseñas no coinciden.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void confirmarRegistroPorVoz(String nombre, String correo) {
        String mensajeConfirmacion = "¿Está seguro de registrar al usuario con nombre " + nombre + " y correo " + correo + "? Responda sí o no.";
        ttsControlador.hablar(mensajeConfirmacion);

        Boolean respuesta;
        try {
            respuesta = sttControlador.entradaAfirmacionNegacion();
            if (respuesta != null && !respuesta) {
                ttsControlador.hablar("Registro cancelado.");
                return;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        procesarRegistro(nombre, new String(campoContrasena.getPassword()), correo, comboRol.getSelectedIndex() + 1);
    }

    private void procesarRegistro(String nombre, String contra, String correo, int idRol) {
        try {
            if (new RegistroControlador().procesarRegistro(nombre, contra, correo, idRol, this)) {
                mostrarMensaje("¡Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        dispose();
    }

    private void volverAInicio() {
        try {
            new InicioVentana().setVisible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        dispose();
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    private void inicializarAccesibilidad() {
        ttsControlador.hablar("Bienvenido al registro de usuario. Complete los campos requeridos. Presione Alt más R para registrar, o Tab para moverse entre campos.");

        campoNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Campo: Nombre de usuario");
            }
        });

        campoCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Campo: Correo electrónico");
            }
        });

        campoContrasena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Campo: Contraseña");
            }
        });

        campoConfirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Campo: Confirmar contraseña");
            }
        });

        comboRol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Seleccione un rol: Administrador o Estudiante");
            }
        });

        botonRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Botón: Registrar. Presione Enter para continuar");
            }
        });

        botonVolver.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                ttsControlador.hablar("Botón: Volver a Inicio. Presione Enter para regresar");
            }
        });
    }
}
