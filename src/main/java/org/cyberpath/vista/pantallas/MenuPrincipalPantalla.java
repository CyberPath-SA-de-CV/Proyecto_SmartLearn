package org.cyberpath.vista.pantallas;

import org.cyberpath.controlador.ControladorDePantallas;
import org.cyberpath.modelo.baseDeDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.componentesR.PlantillaVentanaBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static org.cyberpath.util.VariablesGlobales.panelSuperior;
import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class MenuPrincipalPantalla extends PlantillaVentanaBase {

    private JPanel panelPrincipal;

    public MenuPrincipalPantalla() {
        super("Menú Principal", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal = crearPanelDegradadoDecorativo();
        panelPrincipal.setLayout(new BorderLayout());

        // ---------- Panel superior: datos del usuario + combo configuración ----------
        panelSuperior = crearPanelDegradadoDecorativo();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBorder(new EmptyBorder(10, 30, 10, 30));

        JLabel labelUsuario = new JLabel("Usuario: " + VariablesGlobales.usuario.getNombre());
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        labelUsuario.setForeground(Color.WHITE);

        JLabel labelFecha = crearFecha();

        JPanel panelInfoUsuario = new JPanel();
        panelInfoUsuario.setOpaque(false);
        panelInfoUsuario.setLayout(new BoxLayout(panelInfoUsuario, BoxLayout.Y_AXIS));
        panelInfoUsuario.add(labelUsuario);
        panelInfoUsuario.add(labelFecha);

        JComboBox<String> comboConfiguracion = new JComboBox<>(new String[]{
                "Configuración", "Configuración del usuario", "Accesibilidad", "Cerrar sesión"
        });
        comboConfiguracion.addActionListener(e -> manejarOpcionCombo((String) comboConfiguracion.getSelectedItem()));

        JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelCombo.setOpaque(false);
        panelCombo.add(comboConfiguracion);

        panelSuperior.add(panelInfoUsuario, BorderLayout.WEST);
        panelSuperior.add(panelCombo, BorderLayout.EAST);

        // ---------- Contenido principal ----------
        JPanel contenido = crearPanelTransparenteConPadding(40, 100, 40, 100);
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelLogo = new JLabel(new ImageIcon(
                new ImageIcon("Img/logos/logo_smartlearn.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)
        ));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        JLabel labelTexto = crearTituloCentrado("¡Bienvenido a SmartLearn!");

        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);
        contenido.add(panelTituloConLogo);

        contenido.add(Box.createVerticalStrut(40));

        contenido.add(crearBotonEstilizado("Nombre de las materias inscritas", "Img/iconos/icono_materias.png",
                e -> System.out.println("Mostrar materias inscritas")));

        contenido.add(Box.createVerticalGlue());

        JScrollPane scrollContenido = crearScrollPaneTransparente(contenido);

        // ---------- Integración en panel principal ----------
        panelPrincipal.add(scrollContenido, BorderLayout.CENTER);

        // Se agrega al contenedor interno de la ventana base
        getPanelContenedor().add(panelPrincipal, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void manejarOpcionCombo(String opcion) {
        switch (opcion) {
            case "Configuración del usuario":
                ControladorDePantallas.mostrarPantalla(ControladorDePantallas.PANTALLA_CONFIGURACION);
                break;
            case "Accesibilidad":
                ControladorDePantallas.mostrarPantalla(ControladorDePantallas.PANTALLA_ACCESIBILIDAD);
                break;
            case "Cerrar sesión":
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro que quiere cerrar la sesión de " + VariablesGlobales.usuario.getNombre() + "?",
                        "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    new PantallaInicio().setVisible(true);
                    dispose();
                }
                break;
            default:
                break;
        }
    }

    private JScrollPane crearScrollPaneTransparente(JComponent componente) {
        JScrollPane scroll = new JScrollPane(componente,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        return scroll;
    }

    public JPanel getContenido() {
        return panelPrincipal;
    }

    public static void main(String[] args) {
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);
        VariablesGlobales.usuario = usuarioDao.findById(1);
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalPantalla ventana = new MenuPrincipalPantalla();
            ControladorDePantallas.asignarContenedor(ventana.getPanelContenedor());
            ControladorDePantallas.mostrarPantalla(ControladorDePantallas.PANTALLA_MENU_PRINCIPAL);
            ventana.setVisible(true);
        });
    }
}