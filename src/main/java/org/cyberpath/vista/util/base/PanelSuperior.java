package org.cyberpath.vista.util.base;

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

public class PanelSuperior extends JPanel {

    public final String POR_DEFECTO = "VENTANAS";
    public final String MENU_PRINCIPAL = "Menu Principal";
    public final String CONFIGURACION = "Configuración del usuario";
    public final String ACCESIBILIDAD = "Accesibilidad";
    public final String MODIFICAR_CONTENIDO = "Modificar contenido";
    public final String CERRAR_SESION = "Cerrar sesión";

    public PanelSuperior() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel panelInterno = crearPanelDegradadoDecorativo();
        panelInterno.setLayout(new BorderLayout());
        panelInterno.setBorder(new EmptyBorder(15, 30, 10, 30));

        JLabel labelUsuario = new JLabel("Usuario: " + VariablesGlobales.usuario.getNombre());
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        labelUsuario.setForeground(Color.WHITE);

        JLabel labelFecha = crearFecha();

        JPanel panelInfoUsuario = new JPanel();
        panelInfoUsuario.setOpaque(false);
        panelInfoUsuario.setLayout(new BoxLayout(panelInfoUsuario, BoxLayout.Y_AXIS));
        panelInfoUsuario.add(labelUsuario);
        panelInfoUsuario.add(labelFecha);

        JComboBox<String> comboConfiguracion = crearComboConfiguracion();

        JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelCombo.setOpaque(false);
        panelCombo.add(comboConfiguracion);

        panelInterno.add(panelInfoUsuario, BorderLayout.WEST);
        panelInterno.add(panelCombo, BorderLayout.EAST);

        add(panelInterno, BorderLayout.CENTER);
    }

    private JComboBox<String> crearComboConfiguracion() {
        JComboBox<String> comboConfiguracion;
        if (Objects.equals(VariablesGlobales.usuario.getRol().getId(), 1)) {
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
                    label.setBackground(new Color(0, 70, 140));
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
                manejarOpcionCombo((String) comboConfiguracion.getSelectedItem());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return comboConfiguracion;
    }

    private void manejarOpcionCombo(String opcion) throws Exception {
        switch (opcion) {
            case POR_DEFECTO -> System.out.println(" ");
            case MENU_PRINCIPAL -> PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            case CONFIGURACION -> PantallasControlador.mostrarPantalla(PantallasEnum.CONFIGURACION);
            case ACCESIBILIDAD -> PantallasControlador.mostrarPantalla(PantallasEnum.ACCESIBILIDAD);
            case MODIFICAR_CONTENIDO -> PantallasControlador.mostrarPantalla(PantallasEnum.MODIFICAR_CONTENIDO);
            case CERRAR_SESION -> {
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro que quiere cerrar la sesión de " + VariablesGlobales.usuario.getNombre() + "?",
                        "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    Window ventanaActual = SwingUtilities.getWindowAncestor(this);
                    if (ventanaActual != null) ventanaActual.dispose();
                    new InicioVentana().setVisible(true);
                }
            }
        }
    }
}
