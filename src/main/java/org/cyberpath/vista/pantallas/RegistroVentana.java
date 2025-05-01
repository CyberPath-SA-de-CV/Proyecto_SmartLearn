package org.cyberpath.vista.pantallas;

import org.cyberpath.controlador.RegistroControlador;
import org.cyberpath.vista.componentesR.PlantillaVentanaBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.cyberpath.util.VariablesGlobales.panelSuperior;
import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class RegistroVentana extends PlantillaVentanaBase {

    private JButton botonRegistro;
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmar;
    private JComboBox<String> comboRol;

    public RegistroVentana() {
        super("Registro Usuario", 500, 400);
    }

    @Override
    protected void inicializarComponentes() {
        panelSuperior = crearPanel();
        panelSuperior.setBackground(new Color(85, 85, 125));

        // Título
        JLabel etiquetaTitulo = crearEtiqueta("Registro de Usuario");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaTitulo.setForeground(Color.WHITE);
        panelSuperior.add(etiquetaTitulo, crearConstraintCentrado(0, 0, 3, 1));

        // Instrucciones
        JLabel instrucciones = crearEtiqueta("Complete los siguientes campos:");
        instrucciones.setForeground(Color.WHITE);
        panelSuperior.add(instrucciones, crearConstraint(1, 0, 3, 1));

        // Etiquetas
        JLabel nombreLabel = crearEtiqueta("Nombre de usuario:");
        JLabel correoLabel = crearEtiqueta("Correo electrónico:");
        JLabel contrasenaLabel = crearEtiqueta("Contraseña:");
        JLabel confirmarLabel = crearEtiqueta("Confirmar contraseña:");
        JLabel rolLabel = crearEtiqueta("Rol:");

        for (JLabel lbl : new JLabel[]{nombreLabel, correoLabel, contrasenaLabel, confirmarLabel, rolLabel}) {
            lbl.setForeground(Color.WHITE);
        }

        // Campos
        campoNombre = crearCampoTxt(15);
        campoCorreo = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        campoConfirmar = new JPasswordField(15);
        comboRol = new JComboBox<>(new String[]{"Administrador", "Estudiante"});

        // Agregar componentes
        panelSuperior.add(nombreLabel, crearConstraint(2, 0, 1, 1));
        panelSuperior.add(campoNombre, crearConstraint(2, 1, 2, 1));

        panelSuperior.add(correoLabel, crearConstraint(3, 0, 1, 1));
        panelSuperior.add(campoCorreo, crearConstraint(3, 1, 2, 1));

        panelSuperior.add(contrasenaLabel, crearConstraint(4, 0, 1, 1));
        panelSuperior.add(campoContrasena, crearConstraint(4, 1, 2, 1));

        panelSuperior.add(confirmarLabel, crearConstraint(5, 0, 1, 1));
        panelSuperior.add(campoConfirmar, crearConstraint(5, 1, 2, 1));

        panelSuperior.add(rolLabel, crearConstraint(6, 0, 1, 1));
        panelSuperior.add(comboRol, crearConstraint(6, 1, 2, 1));

        // Botón Registrar
        botonRegistro = crearBoton("Registrar", null, 16, 10);
        botonRegistro.setPreferredSize(new Dimension(180, 40));
        botonRegistro.setMnemonic(KeyEvent.VK_R);
        getRootPane().setDefaultButton(botonRegistro);

        panelSuperior.add(botonRegistro, crearConstraintCentrado(7, 0, 3, 1));
    }

    @Override
    protected void agregarEventos() {
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

            new RegistroControlador().procesarRegistro(nombre,contra,correo,idRol,this);
            System.out.printf("Registrando: %s, %s, Rol: %d%n", nombre, correo, idRol);

            mostrarMensaje("¡Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        };

        botonRegistro.addActionListener(registrar);
    }

    @Override
    public JPanel getContenido() {
        return null;
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroVentana().setVisible(true));
    }
}