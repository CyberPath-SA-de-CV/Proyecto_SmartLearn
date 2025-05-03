package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.vista.util.base.ContenidoConPanelSuperior;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;
import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearScrollPaneTransparente;

public class ModificarContenidoVentana extends PlantillaBaseVentana {

    public ModificarContenidoVentana() {
        super("Configuración", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void inicializarComponentes() { //Pendiente que flojera ahorita...
        JPanel contenido = crearPanelDegradadoDecorativo("Configuración");
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JPanel panelTituloConLogo = crearPanelTituloConLogo("Menu Principal");
        contenido.add(panelTituloConLogo);

        contenido.add(Box.createVerticalStrut(40));
        contenido.add(crearBotonEstilizado("Nombre de las materias inscritas", "Img/iconos/icono_materias.png",
                e -> System.out.println("Mostrar materias inscritas")));
        contenido.add(Box.createVerticalGlue());

        JScrollPane scrollContenido = crearScrollPaneTransparente(contenido);

        ContenidoConPanelSuperior panelConSuperior = new ContenidoConPanelSuperior(scrollContenido);
        getPanelContenedor().add(panelConSuperior, BorderLayout.CENTER);
    }

    @Override
    protected void agregarEventos() {

    }

    @Override
    public JPanel getContenido() {
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModificarContenidoVentana().setVisible(true));
    }
}
