package org.cyberpath.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import org.cyberpath.controlador.InicioSesionControlador;

public class InicioSesionVentanta extends JFrame{
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JCheckBox checkboxAccesibilidad;
    private JButton botonIngresar;

    public InicioSesionVentanta(){
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        campoUsuario = new JTextField(15);
        campoContrasena = new JPasswordField(15);
        //checkboxAccesibilidad = new JCheckBox("Modo accesible"); REMPLAZAR ESTO POR LA PREGUNTA EN LA VENTANA DE COMIENZO
        botonIngresar = new JButton("Ingresar");

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("Usuario:"));
        panel.add(campoUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoContrasena);
        //panel.add(checkboxAccesibilidad);
        panel.add(botonIngresar);

        add(panel);

        // Controlador
        botonIngresar.addActionListener(e -> {
            String usuario = campoUsuario.getText();
            String contrasena = new String(campoContrasena.getPassword());
            //boolean modoAccesible = checkboxAccesibilidad.isSelected();

            new InicioSesionControlador().procesarInicio(usuario, contrasena,this);
        });
    }
}
