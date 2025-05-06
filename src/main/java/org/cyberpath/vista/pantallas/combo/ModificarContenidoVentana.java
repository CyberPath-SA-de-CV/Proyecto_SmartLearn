package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.ContenidoConPanelSuperior;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ModificarContenidoVentana extends PlantillaBaseVentana {

    private JPanel contenidoPrincipal;

    public ModificarContenidoVentana() {
        super("Configuración", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void inicializarComponentes() {
        contenidoPrincipal = crearPanelDegradadoDecorativo("Configuración");
        contenidoPrincipal.setLayout(new BoxLayout(contenidoPrincipal, BoxLayout.Y_AXIS));

        JPanel panelTituloConLogo = crearPanelTituloConLogo("Menú Principal");
        contenidoPrincipal.add(panelTituloConLogo);

        contenidoPrincipal.add(Box.createVerticalStrut(40));
        contenidoPrincipal.add(crearBotonEstilizado(
                "Nombre de las materias inscritas",
                "Img/iconos/icono_materias.png",
                e -> System.out.println("Mostrar materias inscritas")
        ));
        contenidoPrincipal.add(Box.createVerticalGlue());

        JScrollPane scrollContenido = crearScrollPaneTransparente(contenidoPrincipal);
        establecerContenidoConPanelSuperior(scrollContenido);
    }

    @Override
    protected void agregarEventos() {
        // Eventos pendientes
    }

    @Override
    public JPanel getContenido() {
        return contenidoPrincipal;
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(4);
        VariablesGlobales.usuario = ejemplo;
        SwingUtilities.invokeLater(() -> new ModificarContenidoVentana().setVisible(true));
    }
}
