package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.vista.util.base.ContenidoConPanelSuperior;
import org.cyberpath.vista.util.materias.ContenidoVentana;
import org.cyberpath.vista.util.materias.SubtemaVentana;
import org.cyberpath.vista.util.materias.TemaVentana;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearBotonEstilizado;
import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearPanelDegradadoDecorativo;

public class MenuPrincipalVentana extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MenuPrincipalVentana() {
        super("Menú Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        ContenidoConPanelSuperior panelConEncabezado = new ContenidoConPanelSuperior(mainPanel);
        getContentPane().add(panelConEncabezado, BorderLayout.CENTER);

        mostrarMenuMaterias();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipalVentana().setVisible(true));
    }

    private void mostrarMenuMaterias() {
        JPanel panelMaterias = crearPanelDegradadoDecorativo("Materias");
        panelMaterias.setLayout(new BoxLayout(panelMaterias, BoxLayout.Y_AXIS));

        java.util.List<Materia> materias = Materia.materiaDao.findAll();
        for (Materia materia : materias) {
            JButton btnMateria = crearBotonEstilizado(materia.getNombre(), null, e -> mostrarTemas(materia));
            panelMaterias.add(btnMateria);
            panelMaterias.add(Box.createVerticalStrut(5));
        }

        mainPanel.add(panelMaterias, "Materias");
        cardLayout.show(mainPanel, "Materias");
    }

    private void mostrarTemas(Materia materia) {
        TemaVentana temaVentana = new TemaVentana(materia, this);
        mainPanel.add(temaVentana, "Temas");
        cardLayout.show(mainPanel, "Temas");
    }

    public void mostrarSubtemas(Tema tema) {
        SubtemaVentana subtemaVentana = new SubtemaVentana(tema, this);
        mainPanel.add(subtemaVentana, "Subtemas");
        cardLayout.show(mainPanel, "Subtemas");
    }

    public void mostrarContenido(Subtema subtema) {
        ContenidoVentana contenidoVentana = new ContenidoVentana(subtema, this);
        mainPanel.add(contenidoVentana, "Contenido");
        cardLayout.show(mainPanel, "Contenido");
    }

    public void regresar() {
        cardLayout.previous(mainPanel);
    }
}