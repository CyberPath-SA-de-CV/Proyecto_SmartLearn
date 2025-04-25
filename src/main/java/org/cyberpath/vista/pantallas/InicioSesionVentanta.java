package org.cyberpath.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import org.cyberpath.controlador.InicioSesionControlador;
import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class InicioSesionVentanta extends JFrame{
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JCheckBox checkboxAccesibilidad;

    public InicioSesionVentanta(){
        setTitle("Login");
        setSize(300, 190);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logos/logo.png"));
        setIconImage(icono);
        setBackground(new Color(5,100,110));


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

        JPanel panel = crearPanel();
        panel.setBackground(new Color(5,100,110));

        JLabel usrEtiqueta = crearEtiqueta("Usuario:");
        JLabel pswdEtiqueta = crearEtiqueta("Contraseña:");

        usrEtiqueta.setForeground(Color.WHITE);
        pswdEtiqueta.setForeground(Color.WHITE);

        //Boton
        JButton botonLogin = crearBoton("Login", login);
        botonLogin.setMnemonic(KeyEvent.VK_L);      // Alt + L

        panel.add(usrEtiqueta, crearConstraint(0, 0, 1, 1));
        panel.add(campoUsuario, crearConstraint(0, 1, 2, 1));

        panel.add(pswdEtiqueta, crearConstraint(1, 0, 1, 1));
        panel.add(campoContrasena, crearConstraint(1, 1, 2, 1));

        panel.add(botonLogin, crearConstraintCentrado(2, 0, 3, 1));

        add(panel);
    }

    public static void main(String[] args) {
        new InicioSesionVentanta().setVisible(true);
    }
}
