package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.controlador.combo.ConfiguracionControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ConfiguracionVentana extends PlantillaBaseVentana {

    private JPanel contenidoPrincipal;

    public ConfiguracionVentana() throws Exception {
        super("Configuración de Usuario", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarAccesibilidad();
    }

    private void inicializarAccesibilidad() {
        new Thread(() -> {
            try {
                if (PantallasControlador.menuAccesibilidad("Configuración", this)) {
                    ConfiguracionControlador.procesarAccesibilidad(this);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    protected void inicializarComponentes() {
        contenidoPrincipal = crearPanelDegradadoDecorativo("Configuración", "src/main/resources/recursosGraficos/titulos/configuracion.jpg");

        JPanel panelTitulo = crearPanelTitulo();
        JPanel panelBotones = crearPanelBotonesConfiguracion();

        JPanel panelInterno = new JPanel();
        panelInterno.setOpaque(false);
        panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));

        panelInterno.add(Box.createVerticalStrut(20));
        panelInterno.add(panelTitulo);
        panelInterno.add(Box.createVerticalStrut(30));
        panelInterno.add(panelBotones);
        panelInterno.add(Box.createVerticalGlue());

        contenidoPrincipal.add(panelInterno, BorderLayout.CENTER);
        getPanelCentral().add(contenidoPrincipal);
    }

    @Override
    protected void agregarEventos() {

    }

    private JPanel crearPanelTitulo(){
        JPanel titulo = new JPanel();
        JLabel labelTexto = crearTituloCentrado("¿Qué dato desea cambiar del usuario?");
        titulo.add(labelTexto);
        titulo.setOpaque(false);
        titulo.setLayout(new BoxLayout(titulo, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        return titulo;
    }

    private JPanel crearPanelBotonesConfiguracion() {
        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonNombre = crearBotonEstilizado("Cambiar nombre", null, e -> cambiarNombre());
        JButton botonContrasena = crearBotonEstilizado("Cambiar contraseña", null, e -> cambiarContrasena());
        JButton botonCorreo = crearBotonEstilizado("Cambiar correo", null, e -> cambiarCorreo());

        botonNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        botones.add(botonNombre);
        botones.add(Box.createVerticalStrut(20));
        botones.add(botonContrasena);
        botones.add(Box.createVerticalStrut(20));
        botones.add(botonCorreo);

        botones.setAlignmentX(Component.CENTER_ALIGNMENT);

        return botones;
    }

    private void cambiarNombre() {
        String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de usuario:", VariablesGlobales.usuario.getNombre());
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty() && !nuevoNombre.equals(VariablesGlobales.usuario.getNombre())) {
            VariablesGlobales.usuario.setNombre(nuevoNombre.trim());
            if (Usuario.actualizar(VariablesGlobales.usuario)) {
                JOptionPane.showMessageDialog(this, "Nombre actualizado con éxito.");
            } else {
                mostrarError("Hubo un error al actualizar el nombre.");
            }
        }
    }

    private void cambiarContrasena() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JPasswordField campoActual = new JPasswordField();
        JPasswordField campoNueva = new JPasswordField();

        panel.add(new JLabel("Contraseña actual:"));
        panel.add(campoActual);
        panel.add(new JLabel("Nueva contraseña:"));
        panel.add(campoNueva);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION) {
            String actual = new String(campoActual.getPassword());
            String nueva = new String(campoNueva.getPassword());
            String guardada = VariablesGlobales.usuario.getContrasena();

            if (!actual.equals(guardada)) {
                mostrarError("La contraseña actual es incorrecta.");
            } else if (nueva.isBlank()) {
                mostrarAdvertencia("La nueva contraseña no puede estar vacía.");
            } else if (nueva.equals(guardada)) {
                mostrarAdvertencia("La nueva contraseña no puede ser igual a la actual.");
            } else {
                VariablesGlobales.usuario.setContrasena(nueva);
                if (Usuario.actualizar(VariablesGlobales.usuario)) {
                    JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
                } else {
                    mostrarError("Error al actualizar la contraseña.");
                }
            }
        }
    }

    private void cambiarCorreo() {
        String nuevoCorreo = JOptionPane.showInputDialog(this, "Ingrese el nuevo correo:", VariablesGlobales.usuario.getCorreo());
        if (nuevoCorreo != null) {
            nuevoCorreo = nuevoCorreo.trim();
            if (nuevoCorreo.isEmpty() || !nuevoCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                mostrarError("Correo inválido. Intente con un formato correcto.");
            } else if (nuevoCorreo.equals(VariablesGlobales.usuario.getCorreo())) {
                mostrarAdvertencia("El nuevo correo es igual al actual.");
            } else {
                VariablesGlobales.usuario.setCorreo(nuevoCorreo);
                if (Usuario.actualizar(VariablesGlobales.usuario)) {
                    JOptionPane.showMessageDialog(this, "Correo actualizado con éxito.");
                } else {
                    mostrarError("Error al actualizar el correo.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada.");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public JPanel getContenido() {
        return contenidoPrincipal;
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(33);
        VariablesGlobales.usuario = ejemplo;
        SwingUtilities.invokeLater(() -> {
            try {
                new ConfiguracionVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
