package org.cyberpath.vista.pantallas.cuenta;

import org.cyberpath.controlador.Usuario.RegistroControlador;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class RegistroVentana extends JFrame {

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
        setSize(700, 550);
        setLocationRelativeTo(null); // Centrar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarComponentes();
        agregarEventos();
        setContentPane(panelPrincipal);
    }

    public static Boolean pedirContrasenaRol() {
        final JDialog dialog = new JDialog((Frame) null, "Ingrese Contraseña", true);
        final JPasswordField passwordField = new JPasswordField(15);
        final String[] contrasenaIngresada = new String[1];

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel(Salidas.contrasenaRol);
        JButton botonAceptar = new JButton("Aceptar");

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

        // Título
        JLabel etiquetaTitulo = crearEtiqueta("Registro de Usuario");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        etiquetaTitulo.setForeground(Color.WHITE);
        panelPrincipal.add(etiquetaTitulo, crearConstraintCentrado(0, 0, 3, 1, 10));

        JLabel instrucciones = crearEtiqueta("Complete los siguientes campos:");
        instrucciones.setForeground(Color.WHITE);
        instrucciones.setFont(instrucciones.getFont().deriveFont(15f));
        panelPrincipal.add(instrucciones, crearConstraint(1, 0, 3, 1, 10));

        JLabel nombreLabel = crearEtiqueta("Nombre de usuario:");
        JLabel correoLabel = crearEtiqueta("Correo electrónico:");
        JLabel contrasenaLabel = crearEtiqueta("Contraseña:");
        JLabel confirmarLabel = crearEtiqueta("Confirmar contraseña:");
        JLabel rolLabel = crearEtiqueta("Rol:");

        for (JLabel lbl : new JLabel[]{nombreLabel, correoLabel, contrasenaLabel, confirmarLabel, rolLabel}) {
            lbl.setFont(lbl.getFont().deriveFont(15f));
            lbl.setForeground(Color.WHITE);
        }

        campoNombre = crearCampoTxt(15);
        campoCorreo = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        campoConfirmar = new JPasswordField(15);
        comboRol = new JComboBox<>(new String[]{"Administrador", "Estudiante"});

        panelPrincipal.add(nombreLabel, crearConstraint(2, 0, 1, 1, 20));
        panelPrincipal.add(campoNombre, crearConstraint(2, 1, 2, 1, 100));

        panelPrincipal.add(correoLabel, crearConstraint(3, 0, 1, 1, 20));
        panelPrincipal.add(campoCorreo, crearConstraint(3, 1, 2, 1, 100));

        panelPrincipal.add(contrasenaLabel, crearConstraint(4, 0, 1, 1, 20));
        panelPrincipal.add(campoContrasena, crearConstraint(4, 1, 2, 1, 100));

        panelPrincipal.add(confirmarLabel, crearConstraint(5, 0, 1, 1, 20));
        panelPrincipal.add(campoConfirmar, crearConstraint(5, 1, 2, 1, 100));

        panelPrincipal.add(rolLabel, crearConstraint(6, 0, 1, 1, 20));
        panelPrincipal.add(comboRol, crearConstraint(6, 1, 2, 1, 100));

        botonRegistro = crearBotonEstilizado("Registrar", null, null);
        botonRegistro.setMnemonic(KeyEvent.VK_R);

        botonVolver = crearBotonEstilizado("Volver a Inicio", null, null);

        panelPrincipal.add(botonRegistro, crearConstraintBotonAncho(7, 0, 3, 1, 200));
        panelPrincipal.add(botonVolver, crearConstraintBotonAncho(8, 0, 3, 1, 200));

        getRootPane().setDefaultButton(botonRegistro);
    }

    private void agregarEventos() {
        ActionListener registrar = e -> {
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();
            String contra = new String(campoContrasena.getPassword());
            String confirmar = new String(campoConfirmar.getPassword());
            int idRol = comboRol.getSelectedIndex() == 0 ? 1 : 2;

            if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() || confirmar.isEmpty()) {
                mostrarMensaje("Por favor complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            } else if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                mostrarMensaje("Correo electrónico inválido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!contra.equals(confirmar)) {
                mostrarMensaje("Las contraseñas no coinciden.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    if (new RegistroControlador().procesarRegistro(nombre, contra, correo, idRol, this)) {
                        mostrarMensaje("¡Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            dispose();
        };

        botonRegistro.addActionListener(registrar);

        botonVolver.addActionListener(e -> {
            try {
                new InicioVentana().setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
