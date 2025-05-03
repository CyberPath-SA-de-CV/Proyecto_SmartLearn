package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.ContenidoConPanelSuperior;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ConfiguracionVentana extends PlantillaBaseVentana {
    private JButton botonNombre;
    private JButton botonContrasena;
    private JButton botonCorreo;

    public ConfiguracionVentana() {
        super("Configuración de Usuario", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfiguracionVentana().setVisible(true));

    }

    @Override
    protected void inicializarComponentes() {
        JPanel contenido = crearPanelDegradadoDecorativo("Configuración");
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel titulo = crearTituloCentrado("¿Qué dato desea cambiar del usuario?");
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(40));

        botonNombre = crearBotonEstilizado("Cambiar nombre", null, null);
        botonContrasena = crearBotonEstilizado("Cambiar contraseña", null, null);
        botonCorreo = crearBotonEstilizado("Cambiar correo", null, null);

        botonNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonSalir = crearBotonSalirAPantallaPrincipal();

        contenido.add(botonNombre);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(botonContrasena);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(botonCorreo);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(botonSalir);
        contenido.add(Box.createVerticalGlue());

        JScrollPane scrollContenido = crearScrollPaneTransparente(contenido);

        ContenidoConPanelSuperior panelConSuperior = new ContenidoConPanelSuperior(scrollContenido);
        getPanelContenedor().add(panelConSuperior, BorderLayout.CENTER);
    }

    @Override
    protected void agregarEventos() {
        botonNombre.addActionListener(e -> {
            String nuevoNombre = JOptionPane.showInputDialog(this,
                    "Ingrese el nuevo nombre de usuario:", VariablesGlobales.usuario.getNombre());
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty() && !Objects.equals(nuevoNombre, VariablesGlobales.usuario.getNombre())) {
                VariablesGlobales.usuario.setNombre(nuevoNombre.trim());

                if (Usuario.actualizar(VariablesGlobales.usuario)) {
                    JOptionPane.showMessageDialog(this, "Nombre actualizado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el nombre.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonContrasena.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            JPasswordField campoActual = new JPasswordField();
            JPasswordField campoNueva = new JPasswordField();

            panel.add(new JLabel("Contraseña actual:"));
            panel.add(campoActual);
            panel.add(new JLabel("Nueva contraseña:"));
            panel.add(campoNueva);

            int opcion = JOptionPane.showConfirmDialog(this, panel,
                    "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion == JOptionPane.OK_OPTION) {
                String contrasenaActualIngresada = new String(campoActual.getPassword());
                String nuevaContrasena = new String(campoNueva.getPassword());
                String contrasenaActualGuardada = VariablesGlobales.usuario.getContrasena();

                if (!contrasenaActualIngresada.equals(contrasenaActualGuardada)) {
                    JOptionPane.showMessageDialog(this, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (nuevaContrasena.isBlank()) {
                    JOptionPane.showMessageDialog(this, "La nueva contraseña no puede estar vacía.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (nuevaContrasena.equals(contrasenaActualGuardada)) {
                    JOptionPane.showMessageDialog(this, "La nueva contraseña no puede ser igual a la actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VariablesGlobales.usuario.setContrasena(nuevaContrasena);
                if (Usuario.actualizar(VariablesGlobales.usuario)) {
                    JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la contraseña. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonCorreo.addActionListener(e -> {
            String nuevoCorreo = JOptionPane.showInputDialog(this,
                    "Ingrese el nuevo correo:", VariablesGlobales.usuario.getCorreo());

            if (nuevoCorreo != null) {
                nuevoCorreo = nuevoCorreo.trim();

                if (nuevoCorreo.isEmpty() || !nuevoCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(this, "Correo inválido. Intente con un formato correcto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (nuevoCorreo.equals(VariablesGlobales.usuario.getCorreo())) {
                    JOptionPane.showMessageDialog(this, "El nuevo correo es igual al actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VariablesGlobales.usuario.setCorreo(nuevoCorreo);
                if (Usuario.actualizar(VariablesGlobales.usuario)) {
                    JOptionPane.showMessageDialog(this, "Correo actualizado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el correo. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Operación cancelada.");
            }
        });

    }

    @Override
    public JPanel getContenido() {
        return null;
    }
}