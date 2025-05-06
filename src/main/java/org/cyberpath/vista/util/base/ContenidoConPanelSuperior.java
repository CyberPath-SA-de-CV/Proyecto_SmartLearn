package org.cyberpath.vista.util.base;


import lombok.Getter;
import org.cyberpath.controlador.PantallasControlador;
import org.cyberpath.controlador.PantallasEnum;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearFecha;
import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearPanelDegradadoDecorativo;

public class ContenidoConPanelSuperior extends JPanel {

    @Getter
    private final JComponent panelContenido;
    @Getter
    private final JPanel panelSuperior;

    public final String  POR_DEFECTO = "VENTANAS";
    public final String  MENU_PRINCIPAL = "Menu Principal";
    public final String  CONFIGURACION = "Configuración del usuario";
    public final String  ACCESIBILIDAD = "Accesibilidad";
    public final String  MODIFICAR_CONTENIDO = "Modificar contenido";
    public final String  CERRAR_SESION = "Cerrar sesión";

    public ContenidoConPanelSuperior(JComponent contenidoPrincipal) {
        setLayout(new BorderLayout());

        // Panel superior reutilizable
        panelSuperior = crearPanelDegradadoDecorativo();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBorder(new EmptyBorder(15, 30, 10, 30));

        JLabel labelUsuario = new JLabel("Usuario: " + VariablesGlobales.usuario.getNombre());
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        labelUsuario.setForeground(Color.WHITE);

        JLabel labelFecha = crearFecha();

        JPanel panelInfoUsuario = new JPanel();
        panelInfoUsuario.setOpaque(false);
        panelInfoUsuario.setLayout(new BoxLayout(panelInfoUsuario, BoxLayout.Y_AXIS));
        panelInfoUsuario.add(labelUsuario);
        panelInfoUsuario.add(labelFecha);

        JComboBox<String> comboConfiguracion = getStringJComboBox();

        JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelCombo.setOpaque(false);
        panelCombo.add(comboConfiguracion);

        panelSuperior.add(panelInfoUsuario, BorderLayout.WEST);
        panelSuperior.add(panelCombo, BorderLayout.EAST);

        this.panelContenido = contenidoPrincipal;

        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);

    }

    private JComboBox<String> getStringJComboBox() {
        JComboBox<String> comboConfiguracion;
        if(Objects.equals(VariablesGlobales.usuario.getRol().getId(), 1)){
            comboConfiguracion = new JComboBox<>(new String[]{
                    POR_DEFECTO, MENU_PRINCIPAL, CONFIGURACION, ACCESIBILIDAD, MODIFICAR_CONTENIDO, CERRAR_SESION
            });
        } else {
            comboConfiguracion = new JComboBox<>(new String[]{
                    POR_DEFECTO, MENU_PRINCIPAL, CONFIGURACION, ACCESIBILIDAD, CERRAR_SESION
            });
        }


        comboConfiguracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboConfiguracion.setForeground(new Color(60, 63, 65));
        comboConfiguracion.setBackground(Color.WHITE);
        comboConfiguracion.setFocusable(false);
        comboConfiguracion.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        comboConfiguracion.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setBorder(new EmptyBorder(5, 10, 5, 10));

                if (isSelected) {
                    label.setBackground(new Color(0, 70, 140)); // Azul más oscuro
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(new Color(60, 63, 65));
                }

                return label;
            }
        });

        comboConfiguracion.addActionListener(e -> {
            try {
                manejarOpcionCombo((String) Objects.requireNonNull(comboConfiguracion.getSelectedItem()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return comboConfiguracion;
    }

    public void manejarOpcionCombo(String opcion) throws Exception {

        switch (opcion) {
            case POR_DEFECTO ->
                System.out.println(" ");
            case MENU_PRINCIPAL ->{
                Window ventanaActual = SwingUtilities.getWindowAncestor(this);
                if (ventanaActual != null) {
                    ventanaActual.dispose(); // Cierra la ventana actual
                }
                PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            }
            case CONFIGURACION ->{
                Window ventanaActual = SwingUtilities.getWindowAncestor(this);
                if (ventanaActual != null) {
                    ventanaActual.dispose(); // Cierra la ventana actual
                }
                PantallasControlador.mostrarPantalla(PantallasEnum.CONFIGURACION);
            }
            case ACCESIBILIDAD ->{
                Window ventanaActual = SwingUtilities.getWindowAncestor(this);
                if (ventanaActual != null) {
                    ventanaActual.dispose(); // Cierra la ventana actual
                }
                PantallasControlador.mostrarPantalla(PantallasEnum.ACCESIBILIDAD);
            }
            case MODIFICAR_CONTENIDO ->{
                Window ventanaActual = SwingUtilities.getWindowAncestor(this);
                if (ventanaActual != null) {
                    ventanaActual.dispose(); // Cierra la ventana actual
                }
                PantallasControlador.mostrarPantalla(PantallasEnum.MODIFICAR_CONTENIDO);
            }
            case CERRAR_SESION ->
                {
                    int confirmacion = JOptionPane.showConfirmDialog(this,
                            "¿Está seguro que quiere cerrar la sesión de " + VariablesGlobales.usuario.getNombre() + "?",
                            "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        // Cerrar ventana actual
                        Window ventanaActual = SwingUtilities.getWindowAncestor(this);
                        if (ventanaActual != null) {
                            ventanaActual.dispose(); // Cierra la ventana actual
                        }

                        // Mostrar ventana de inicio
                        new InicioVentana().setVisible(true);
                    }
                }
        }
    }

}