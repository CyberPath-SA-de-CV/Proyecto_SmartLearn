package org.cyberpath.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;
import org.cyberpath.vista.componentesR.PlantillaVentanaBase;

/**
 * PantallaInicio
 * Implementa la pantalla de bienvenida principal usando la plantilla base.
 */
public class PantallaInicio extends PlantillaVentanaBase {

    private JButton botonLogin;
    private JButton botonRegistro;

    public PantallaInicio() {
        super("Smart-Learn", 400, 250);
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal = crearPanel();
        panelPrincipal.setBackground(new Color(230, 230, 230));

        // Etiqueta de bienvenida
        JLabel mensaje = crearEtiqueta("Bienvenido a Smart Learn¡");
        mensaje.setFont(new Font("Arial", Font.BOLD, 18));
        mensaje.setForeground(new Color(40, 40, 40));
        panelPrincipal.add(mensaje, crearConstraintCentrado(0, 0, 3, 1));

        // Botones
        botonLogin = crearBoton("Login", null, 16, 10);
        botonLogin.setMnemonic(KeyEvent.VK_L);
        botonLogin.setPreferredSize(new Dimension(180, 40));

        botonRegistro = crearBoton("Registrar Usuario", null, 16, 10);
        botonRegistro.setMnemonic(KeyEvent.VK_R);
        botonRegistro.setPreferredSize(new Dimension(180, 40));

        panelPrincipal.add(botonLogin, crearConstraintCentrado(1, 1, 2, 1));
        panelPrincipal.add(botonRegistro, crearConstraintCentrado(2, 1, 2, 1));

        // Tecla Enter activa el botón Login
        getRootPane().setDefaultButton(botonLogin);
    }

    @Override
    protected void agregarEventos() {
        ActionListener accionLogin = e -> {
            new InicioSesionVentanta().setVisible(true);
            System.out.println("Entrando a Login");
            dispose();
        };

        ActionListener accionRegistro = e -> {
            new RegistroUsr().setVisible(true);
            System.out.println("Entrando a Registro Usr");
            dispose();
        };

        botonLogin.addActionListener(accionLogin);
        botonRegistro.addActionListener(accionRegistro);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicio().setVisible(true));
    }
}