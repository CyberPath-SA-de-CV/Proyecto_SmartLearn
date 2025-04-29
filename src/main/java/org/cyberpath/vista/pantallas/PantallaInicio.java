package org.cyberpath.vista.pantallas;

import javax.swing.*;
import java.awt.*;
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
    private JButton botonSalirApp;

    public PantallaInicio() {
        super("Smart-Learn", 450, 300);
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal = crearPanel();
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setLayout(new GridBagLayout());

        // Etiqueta de bienvenida estilizada
        JLabel mensaje = crearEtiqueta("¡Bienvenido a Smart Learn!");
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 22));
        mensaje.setForeground(new Color(30, 30, 30));
        panelPrincipal.add(mensaje, crearConstraintCentrado(0, 0, 3, 1));

        // Botón Login con estilo azul bonito
        botonLogin = crearBoton("Iniciar Sesión", null, 16, 10);
        botonLogin.setMnemonic(KeyEvent.VK_L);
        botonLogin.setBackground(new Color(70, 130, 180)); // Azul bonito
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);
        botonLogin.setPreferredSize(new Dimension(200, 40));
        panelPrincipal.add(botonLogin, crearConstraintCentrado(1, 1, 2, 1));

        // Botón Registro con estilo azul bonito
        botonRegistro = crearBoton("Registrar Usuario", null, 16, 10);
        botonRegistro.setMnemonic(KeyEvent.VK_R);
        botonRegistro.setBackground(new Color(70, 130, 180)); // Azul bonito
        botonRegistro.setForeground(Color.WHITE);
        botonRegistro.setFocusPainted(false);
        botonRegistro.setPreferredSize(new Dimension(200, 40));
        panelPrincipal.add(botonRegistro, crearConstraintCentrado(2, 1, 2, 1));

        // Botón Salir App con estilo azul bonito
        botonSalirApp = crearBoton("Salir de la App", null, 14, 10);
        botonSalirApp.setBackground(new Color(70, 130, 180)); // Azul bonito
        botonSalirApp.setForeground(Color.WHITE);
        botonSalirApp.setFocusPainted(false);
        botonSalirApp.setPreferredSize(new Dimension(180, 35));
        panelPrincipal.add(botonSalirApp, crearConstraintCentrado(3, 1, 2, 1));

        // Enter activa el botón Login
        getRootPane().setDefaultButton(botonLogin);
    }

    @Override
    protected void agregarEventos() {
        botonLogin.addActionListener(e -> {
            new InicioSesionVentanta().setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicio().setVisible(true));
    }
}
