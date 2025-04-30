package org.cyberpath.vista.pantallas;

import org.cyberpath.controlador.ControladorDePantallas;
import org.cyberpath.modelo.baseDeDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class AccesibilidadVentana {

    private JPanel panelPrincipal;

    public AccesibilidadVentana() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelPrincipal = crearPanelDegradadoDecorativo();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel contenido = crearPanelTransparenteConPadding(40, 100, 40, 100);

        JPanel barraSuperior = crearBarraSuperior("Usuario: " + VariablesGlobales.usuario.getNombre());
        contenido.add(barraSuperior);
        contenido.add(Box.createVerticalStrut(30));

        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));

        ImageIcon iconoLogo = new ImageIcon("img/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel labelTexto = crearTituloCentrado("Configuración del sistema");
        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);
        contenido.add(panelTituloConLogo);
        contenido.add(Box.createVerticalStrut(40));

        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox activarVoz = crearCheckBox("Activar Voz", VariablesGlobales.reproduccionGlobalAudio);
        activarVoz.addActionListener(e -> VariablesGlobales.reproduccionGlobalAudio = activarVoz.isSelected());

        JCheckBox modoAccesible = crearCheckBox("Opción 2", false); // Cambiar si es necesario

        opciones.add(activarVoz);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(modoAccesible);
        opciones.add(Box.createVerticalStrut(30));

        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonSalir.setBackground(new Color(220, 53, 69));
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setFocusPainted(false);
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setMaximumSize(new Dimension(200, 40));
        botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botonSalir.addActionListener(e -> ControladorDePantallas.mostrarPantalla(ControladorDePantallas.PANTALLA_MENU_PRINCIPAL));

        opciones.add(botonSalir);
        contenido.add(opciones);
        contenido.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contenido,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getContenido() {
        return panelPrincipal;
    }

    // Úsalo solo para pruebas independientes
    public static void main(String[] args) {
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);
        VariablesGlobales.usuario = usuarioDao.findById(1);

        JFrame ventana = new JFrame("Configuración");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.getContentPane().add(new AccesibilidadVentana().getContenido());
        ventana.setVisible(true);
    }
}
