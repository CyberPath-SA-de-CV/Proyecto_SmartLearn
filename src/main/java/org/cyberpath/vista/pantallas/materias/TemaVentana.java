package org.cyberpath.vista.pantallas.materias;

import org.cyberpath.controlador.materias.TemaControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearBotonEstilizado;
import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearPanelTituloConLogo;

public class TemaVentana extends PanelDegradado {

    public TemaVentana(Materia materia, MenuPrincipalVentana menu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        agregarEspaciadoVertical(20);
        agregarPanelTitulo(materia);
        agregarEspaciadoVertical(20);
        agregarBotonesTemas(materia, menu);
        agregarEspaciadoVertical(20);
        agregarBotonBajarMateria(materia, menu);
        agregarEspaciadoVertical(20);
        agregarBotonRegresar(menu);
        iniciarAccesibilidad(materia, menu);
    }

    private void agregarPanelTitulo(Materia materia) {
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Temas | " + materia.getNombre(), "src/main/resources/recursosGraficos/titulos/temas.jpg");
        add(panelTituloConLogo);
    }

    private void agregarBotonesTemas(Materia materia, MenuPrincipalVentana menu) {
        for (Tema tema : materia.getTemas()) {
            JButton btnTema = crearBotonEstilizado(tema.getNombre(), null, e -> menu.mostrarSubtemas(tema));
            add(btnTema);
            add(Box.createVerticalStrut(5));
        }
    }

    private void agregarBotonBajarMateria(Materia materia, MenuPrincipalVentana menu){
        JButton btnBajarMateria = crearBotonEstilizado("Desinscribir Materia", null, e -> {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea desinscribir ésta materia?",
                    "Desinscribir Materia", JOptionPane.YES_NO_OPTION);
            if(opcion == JOptionPane.YES_OPTION){
                UsuarioMateria.eliminar(materia);
                menu.regresar();
                try {
                    PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(btnBajarMateria);
    }

    private void agregarBotonRegresar(MenuPrincipalVentana menu) {
        JButton btnRegresar = new JButton("Regresar");
        configurarBotonRegresar(btnRegresar, menu);
        add(btnRegresar);
    }

    private void configurarBotonRegresar(JButton btnRegresar, MenuPrincipalVentana menu) {
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegresar.setBackground(new Color(220, 53, 69));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(200, 40));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e ->{
            menu.regresar();
            try {
                PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void iniciarAccesibilidad(Materia materia, MenuPrincipalVentana menu) {
        new Thread(() -> {
            try {
                menu.mostrarSubtemas(TemaControlador.procesarAccesibilidad(materia, menu));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void agregarEspaciadoVertical(int espacio) {
        add(Box.createVerticalStrut(espacio));
    }
}
