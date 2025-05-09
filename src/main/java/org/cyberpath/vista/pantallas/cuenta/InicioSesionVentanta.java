package org.cyberpath.vista.pantallas.cuenta;

import org.cyberpath.controlador.Usuario.InicioSesionControlador;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class InicioSesionVentanta extends JFrame {
    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;
    private JCheckBox checkboxAccesibilidad;

    public InicioSesionVentanta() {
        setTitle("Login");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursosGraficos/logos/logo.png"));
        setIconImage(icono);
        setBackground(new Color(5, 100, 110));

        campoUsuario = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        //checkboxAccesibilidad = new JCheckBox("Modo accesible"); REMPLAZAR ESTO POR LA PREGUNTA EN LA VENTANA DE COMIENZO
        // Controlador
        ActionListener login = e -> {
            String usuario = campoUsuario.getText();
            String contrasena = new String(campoContrasena.getPassword());
            //boolean modoAccesible = checkboxAccesibilidad.isSelected();

            new InicioSesionControlador().procesarInicio(usuario, contrasena, this);
        };

        JPanel panel = new PanelConRayasVerticales();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(5, 100, 110));

        JLabel usrEtiqueta = crearEtiqueta("Usuario:");
        usrEtiqueta.setFont(usrEtiqueta.getFont().deriveFont(20f));
        JLabel pswdEtiqueta = crearEtiqueta("Contraseña:");
        pswdEtiqueta.setFont(pswdEtiqueta.getFont().deriveFont(20f));

        usrEtiqueta.setForeground(Color.WHITE);
        pswdEtiqueta.setForeground(Color.WHITE);

        //Boton
        JButton botonLogin = crearBotonEstilizado("Login", null, login);
        botonLogin.setMnemonic(KeyEvent.VK_L);      // Alt + L

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
        panel.add(botonVolver, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        add(panel);
    }

}