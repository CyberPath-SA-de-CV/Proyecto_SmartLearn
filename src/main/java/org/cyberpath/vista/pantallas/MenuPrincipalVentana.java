package org.cyberpath.vista.pantallas;

import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.componentesR.ComponentesReutilizables;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalVentana extends ComponentesReutilizables {

    public MenuPrincipalVentana() {
        setTitle("Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel fondo = crearPanelDegradadoDecorativo();
        fondo.setLayout(new BorderLayout());

        JPanel contenido = crearPanelTransparenteConPadding(40, 100, 40, 100);

        JPanel barraSuperior = crearBarraSuperior("Usuario: " + VariablesGlobales.usuario.getNombre());
        contenido.add(barraSuperior);
        contenido.add(Box.createVerticalStrut(30));

        // Crear panel horizontal para el logo + título
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon iconoLogo = new ImageIcon("img/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));  // Espacio entre logo y texto
        JLabel labelTexto = crearTituloCentrado("¡Bienvenido a SmartLearn!");
        labelTexto.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);
        contenido.add(panelTituloConLogo);


        contenido.add(crearBotonEstilizado("Materias", "img/iconos/icono_materias.png", e -> {
            System.out.println("Sección materias");
        }));
        contenido.add(Box.createVerticalStrut(25));
        contenido.add(crearBotonEstilizado("Configuración", "img/iconos/icono_config.png", e ->{
            System.out.println("Seccion Configuración");
            new ConfiguracionVentana().setVisible(true);
        }));
        contenido.add(Box.createVerticalStrut(25));
        contenido.add(crearBotonEstilizado("Cerrar sesión", "img/iconos/icono_salir.png", e -> {
            System.out.println("Volviendo");
            new PantallaInicio().setVisible(true);
            dispose();
        }));
        contenido.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contenido,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        fondo.add(scrollPane, BorderLayout.CENTER);
        setContentPane(fondo);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipalVentana::new);
    }
}
