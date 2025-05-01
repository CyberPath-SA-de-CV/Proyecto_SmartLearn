package org.cyberpath.vista.pantallas;

import org.cyberpath.modelo.baseDeDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.componentesR.PlantillaVentanaBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class ConfiguracionPantalla extends PlantillaVentanaBase {
    private JPanel panelConfig;
    private JButton botonNombre;
    private JButton botonContrasena;
    private JButton botonCorreo;

    public ConfiguracionPantalla() {
        super("Configuración de Usuario", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void inicializarComponentes() {
        panelConfig = crearPanelDegradadoDecorativo();
        panelConfig.setLayout(new BorderLayout());

        JPanel contenido = crearPanelTransparenteConPadding(60, 100, 60, 100);

        JLabel titulo = crearTituloCentrado("¿Qué dato desea cambiar del usuario?");
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(40));

        botonNombre = crearBotonEstilizado("Cambiar nombre", null, null);
        botonContrasena = crearBotonEstilizado("Cambiar contraseña",null, null);
        botonCorreo = crearBotonEstilizado("Cambiar correo", null, null);

        botonNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenido.add(botonNombre);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(botonContrasena);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(botonCorreo);

        contenido.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(contenido,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        panelConfig.add(scroll, BorderLayout.CENTER);
    }

    @Override
    protected void agregarEventos() {
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);;
        botonNombre.addActionListener(e -> {
            String nuevoNombre = JOptionPane.showInputDialog(this,
                    "Ingrese el nuevo nombre de usuario:", VariablesGlobales.usuario.getNombre());
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty() && nuevoNombre!=VariablesGlobales.usuario.getNombre()) {
                VariablesGlobales.usuario.setNombre(nuevoNombre.trim());
                boolean actualizado = usuarioDao.actualizar(VariablesGlobales.usuario);
                if (actualizado) {
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
                }

                if (nuevaContrasena.isBlank()) {
                    JOptionPane.showMessageDialog(this, "La nueva contraseña no puede estar vacía.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (nuevaContrasena.equals(contrasenaActualGuardada)) {
                    JOptionPane.showMessageDialog(this, "La nueva contraseña no puede ser igual a la actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VariablesGlobales.usuario.setContrasena(nuevaContrasena);
                boolean actualizado = usuarioDao.actualizar(VariablesGlobales.usuario);

                if (actualizado) {
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

                // Validación básica de correo electrónico
                if (nuevoCorreo.isEmpty() || !nuevoCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(this, "Correo inválido. Intente con un formato correcto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nuevoCorreo.equals(VariablesGlobales.usuario.getCorreo())) {
                    JOptionPane.showMessageDialog(this, "El nuevo correo es igual al actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VariablesGlobales.usuario.setCorreo(nuevoCorreo);
                boolean actualizado = usuarioDao.actualizar(VariablesGlobales.usuario);

                if (actualizado) {
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
        return panelConfig;
    }

    public static void main(String[] args) {
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);
        VariablesGlobales.usuario = usuarioDao.findById(1);
        JFrame configuracion = new JFrame("Configuración");
        configuracion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configuracion.setExtendedState(JFrame.MAXIMIZED_BOTH);
        configuracion.getContentPane().add(new ConfiguracionPantalla().getContenido());
        configuracion.setVisible(true);
    }
}