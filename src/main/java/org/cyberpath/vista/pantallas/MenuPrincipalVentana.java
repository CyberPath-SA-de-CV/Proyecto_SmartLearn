package org.cyberpath.vista.pantallas;

import org.cyberpath.modelo.baseDeDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.componentesR.PlantillaVentanaBase;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.componentesR.ComponentesReutilizables.*;

public class MenuPrincipalVentana extends PlantillaVentanaBase {

    public MenuPrincipalVentana() {
        super("Menú Principal", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal = crearPanelDegradadoDecorativo();  // Fondo con gradiente
        panelPrincipal.setLayout(new BorderLayout());

        JPanel contenido = crearPanelTransparenteConPadding(40, 100, 40, 100);

        // Barra superior con nombre de usuario
        JPanel barraSuperior = crearBarraSuperior("Usuario: " + VariablesGlobales.usuario.getNombre());
        contenido.add(barraSuperior);
        contenido.add(Box.createVerticalStrut(30));

        // Panel con logo + título
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon iconoLogo = new ImageIcon("Img/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel labelTexto = crearTituloCentrado("¡Bienvenido a SmartLearn!");
        labelTexto.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);
        contenido.add(panelTituloConLogo);

        // Botones del menú principal
        contenido.add(crearBotonEstilizado("Materias", "Img/iconos/icono_materias.png", e -> {System.out.println("Sección materias");}));

        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearBotonEstilizado("Configuración", "Img/iconos/icono_config.png", e -> {
            System.out.println("Sección Configuración");
            new ConfiguracionVentana().setVisible(true);
        }));

        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearBotonEstilizado("Cerrar sesión", "Img/iconos/icono_salir.png", e -> {
            System.out.println("Cerrando sesión...");
            new PantallaInicio().setVisible(true);
            dispose();
        }));

        contenido.add(Box.createVerticalGlue());

        // Scroll pane para despliegue visual elegante
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
        // Los eventos ya están integrados directamente en los botones
    }

    public static void main(String[] args) {
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);
        VariablesGlobales.usuario = usuarioDao.findById(1);
        SwingUtilities.invokeLater(() -> new MenuPrincipalVentana().setVisible(true));
    }
}
