package org.cyberpath.vista.pantallas;

import org.cyberpath.controlador.RegistroControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class RegistroVentana extends JFrame {

    private JPanel panelRegistro;
    private JButton botonRegistro;
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmar;
    private JComboBox<String> comboRol;

    public RegistroVentana() {
        setTitle("Registro Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrado

        inicializarComponentes();
        agregarEventos();

        add(panelRegistro);
    }

    private void inicializarComponentes() {
        panelRegistro = crearPanel();
        panelRegistro.setBackground(new Color(85, 85, 125));

        JLabel etiquetaTitulo = crearEtiqueta("Registro de Usuario");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaTitulo.setForeground(Color.WHITE);
        panelRegistro.add(etiquetaTitulo, crearConstraintCentrado(0, 0, 3, 1));

        JLabel instrucciones = crearEtiqueta("Complete los siguientes campos:");
        instrucciones.setForeground(Color.WHITE);
        panelRegistro.add(instrucciones, crearConstraint(1, 0, 3, 1));

        JLabel nombreLabel = crearEtiqueta("Nombre de usuario:");
        JLabel correoLabel = crearEtiqueta("Correo electrónico:");
        JLabel contrasenaLabel = crearEtiqueta("Contraseña:");
        JLabel confirmarLabel = crearEtiqueta("Confirmar contraseña:");
        JLabel rolLabel = crearEtiqueta("Rol:");

        for (JLabel lbl : new JLabel[]{nombreLabel, correoLabel, contrasenaLabel, confirmarLabel, rolLabel}) {
            lbl.setForeground(Color.WHITE);
        }

        campoNombre = crearCampoTxt(15);
        campoCorreo = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        campoConfirmar = new JPasswordField(15);
        comboRol = new JComboBox<>(new String[]{"Administrador", "Estudiante"});

        panelRegistro.add(nombreLabel, crearConstraint(2, 0, 1, 1));
        panelRegistro.add(campoNombre, crearConstraint(2, 1, 2, 1));

        panelRegistro.add(correoLabel, crearConstraint(3, 0, 1, 1));
        panelRegistro.add(campoCorreo, crearConstraint(3, 1, 2, 1));

        panelRegistro.add(contrasenaLabel, crearConstraint(4, 0, 1, 1));
        panelRegistro.add(campoContrasena, crearConstraint(4, 1, 2, 1));

        panelRegistro.add(confirmarLabel, crearConstraint(5, 0, 1, 1));
        panelRegistro.add(campoConfirmar, crearConstraint(5, 1, 2, 1));

        panelRegistro.add(rolLabel, crearConstraint(6, 0, 1, 1));
        panelRegistro.add(comboRol, crearConstraint(6, 1, 2, 1));

        botonRegistro = crearBoton("Registrar", null, 16, 10);
        botonRegistro.setPreferredSize(new Dimension(180, 40));
        botonRegistro.setMnemonic(KeyEvent.VK_R);
        getRootPane().setDefaultButton(botonRegistro);

        panelRegistro.add(botonRegistro, crearConstraintCentrado(7, 0, 3, 1));
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
            }

            if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                mostrarMensaje("Correo electrónico inválido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contra.equals(confirmar)) {
                mostrarMensaje("Las contraseñas no coinciden.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            new RegistroControlador().procesarRegistro(nombre, contra, correo, idRol, this);
            mostrarMensaje("¡Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        };

        botonRegistro.addActionListener(registrar);
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroVentana().setVisible(true));
    }
}
