package org.cyberpath.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import org.cyberpath.controlador.InicioSesionControlador;
import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class InicioSesionVentanta extends JFrame{
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JCheckBox checkboxAccesibilidad;

    public InicioSesionVentanta(){
        setTitle("Login");
        setSize(450, 275);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/logo.png"));
        setIconImage(icono);

        campoUsuario = crearCampoTxt(15);
        campoContrasena = new JPasswordField(15);
        //checkboxAccesibilidad = new JCheckBox("Modo accesible"); REMPLAZAR ESTO POR LA PREGUNTA EN LA VENTANA DE COMIENZO
        // Controlador
        ActionListener accionB1 = e -> {
            String usuario = campoUsuario.getText();
            String contrasena = new String(campoContrasena.getPassword());
            //boolean modoAccesible = checkboxAccesibilidad.isSelected();

            new InicioSesionControlador().procesarInicio(usuario, contrasena, this);
        };

        JPanel panel = crearPanel(4,1);
        panel.setBackground(new Color(200,235,255));
        panel.add(crearEtiqueta("Usuario: "));
        panel.add(campoUsuario);
        panel.add(crearEtiqueta("Contraseña: "));
        panel.add(campoContrasena);
        //panel.add(checkboxAccesibilidad);
        panel.add(crearBoton("Login",accionB1, 15,20 ));

        add(panel);
    }
}
