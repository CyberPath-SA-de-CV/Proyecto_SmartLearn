package org.cyberpath.vista.pantallas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.crearBoton;

public class MenuPrincipalVentana extends JFrame {
    public MenuPrincipalVentana() {
        setTitle("Menú Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ActionListener accionContenidos = e -> {};//Falta implementar
        ActionListener accionEjercicios = e -> {};//Falta implementar
        JButton btnSalir = new JButton("Salir");

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(crearBoton("Ver contenidos", accionContenidos));
        panel.add(crearBoton("Realizar ejercicios", accionEjercicios));
        panel.add(btnSalir);

        add(panel);

        if (true) { //MODIFICAR
            //AudioController.reproducir("Menú principal. Opciones: Ver contenidos, Realizar ejercicios, Salir.");
            System.out.println("Se reproduce audio");
        }

        btnSalir.addActionListener(e -> System.exit(0));
    }
}
