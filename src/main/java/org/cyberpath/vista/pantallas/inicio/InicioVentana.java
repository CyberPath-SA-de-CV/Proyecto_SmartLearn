package org.cyberpath.vista.pantallas.inicio;

import org.cyberpath.vista.pantallas.cuenta.InicioSesionVentanta;
import org.cyberpath.vista.pantallas.cuenta.RegistroVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class InicioVentana extends JFrame {

    private JPanel panelPrincipal;
    private JButton botonLogin;
    private JButton botonRegistro;
    private JButton botonSalirApp;

    public InicioVentana() {
        super("Smart-Learn");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        inicializarComponentes();
        agregarEventos();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InicioVentana().setVisible(true));
    }

    private void inicializarComponentes() {
        panelPrincipal = new PanelConRayasVerticales();
        panelPrincipal.setLayout(new GridBagLayout());

        // Etiqueta de bienvenida
        JLabel mensaje = crearEtiqueta("¡Bienvenido a Smart Learn!");
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mensaje.setForeground(new Color(185, 222, 245));
        panelPrincipal.add(mensaje, crearConstraintBotonAncho(0, 0, 3, 1, 100));

        // Botón Login
        botonLogin = crearBotonEstilizado("Iniciar Sesion", null, null);
        panelPrincipal.add(botonLogin, crearConstraintBotonAncho(1, 0, 3, 1, 100));

        // Botón Registro
        botonRegistro = crearBotonEstilizado("Registrar Usuario", null, null);
        panelPrincipal.add(botonRegistro, crearConstraintBotonAncho(2, 0, 3, 1, 100));

        // Botón Salir
        botonSalirApp = crearBotonEstilizado("Salir de la App", null, null);
        panelPrincipal.add(botonSalirApp, crearConstraintBotonAncho(3, 0, 3, 1, 100));

        getRootPane().setDefaultButton(botonLogin);
        setContentPane(panelPrincipal);
    }

    private void agregarEventos() {
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
}
