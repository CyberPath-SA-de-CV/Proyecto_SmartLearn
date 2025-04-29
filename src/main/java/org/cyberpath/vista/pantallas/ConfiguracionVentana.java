package org.cyberpath.vista.pantallas;

import org.cyberpath.modelo.baseDeDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.componentesR.PlantillaVentanaBase;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class ConfiguracionVentana extends PlantillaVentanaBase {

    public ConfiguracionVentana() {
        super("Configuración", 1200, 800); // Puedes ajustar tamaño a tu gusto
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal = crearPanelDegradadoDecorativo(); // Fondo con degradado
        panelPrincipal.setLayout(new BorderLayout());

        JPanel contenido = crearPanelTransparenteConPadding(40, 100, 40, 100);

        JPanel barraSuperior = crearBarraSuperior("Usuario: " + VariablesGlobales.usuario.getNombre());
        contenido.add(barraSuperior);
        contenido.add(Box.createVerticalStrut(30));

        // Título con logo
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon iconoLogo = new ImageIcon("img/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel labelTexto = crearTituloCentrado("Configuración del sistema");
        labelTexto.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);
        contenido.add(panelTituloConLogo);
        contenido.add(Box.createVerticalStrut(40));

        // Panel de opciones
        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox activarVoz = crearCheckBoxEstilizado("Activar Voz", VariablesGlobales.reproduccionGlobalAudio);
        activarVoz.addActionListener(e -> VariablesGlobales.reproduccionGlobalAudio = activarVoz.isSelected());

        JCheckBox ejemploCheck2 = crearCheckBoxEstilizado("Opción 2", false);
        JCheckBox ejemploCheck3 = crearCheckBoxEstilizado("Opción 3", false);

        opciones.add(activarVoz);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(ejemploCheck2);
        opciones.add(Box.createVerticalStrut(15));
        opciones.add(ejemploCheck3);
        opciones.add(Box.createVerticalStrut(30));

        // Botón salir
        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonSalir.setBackground(new Color(220, 53, 69));
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setFocusPainted(false);
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setMaximumSize(new Dimension(200, 40));
        botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonSalir.addActionListener(e -> dispose());

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

    @Override
    protected void agregarEventos() {
        // Los listeners ya están integrados directamente
    }

    private JCheckBox crearCheckBoxEstilizado(String texto, boolean seleccionado) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setSelected(seleccionado);
        checkBox.setOpaque(false);
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        checkBox.setForeground(Color.WHITE);
        checkBox.setFocusPainted(false);
        checkBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return checkBox;
    }

    public static void main(String[] args) {
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);
        VariablesGlobales.usuario = usuarioDao.findById(1);
        SwingUtilities.invokeLater(() -> new ConfiguracionVentana().setVisible(true));
    }
}