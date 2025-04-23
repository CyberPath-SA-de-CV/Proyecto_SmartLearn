package org.cyberpath.vista.pantallas;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalVentana extends JFrame {
    public MenuPrincipalVentana() {
        setTitle("Menú Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnContenido = new JButton("Ver contenidos");
        JButton btnEjercicios = new JButton("Realizar ejercicios");
        JButton btnSalir = new JButton("Salir");

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(btnContenido);
        panel.add(btnEjercicios);
        panel.add(btnSalir);

        add(panel);

        if (true) { //MODIFICAR
            //AudioController.reproducir("Menú principal. Opciones: Ver contenidos, Realizar ejercicios, Salir.");
            System.out.println("Se reproduce audio");
        }

        btnSalir.addActionListener(e -> System.exit(0));
    }
}
