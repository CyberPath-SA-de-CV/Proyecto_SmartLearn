package org.cyberpath.controlador.pantallas;

import org.cyberpath.controlador.combo.MenuPrincipalControlador;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.AccesibilidadVentana;
import org.cyberpath.vista.pantallas.combo.ConfiguracionVentana;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.pantallas.combo.ModificarContenidoVentana;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.pantallas.materias.InscribirMateriasVentana;

import javax.swing.*;
import java.awt.*;

public class PantallasControlador {

    private static final String[] STT_LISTA = {
            "menú principal", "configuración cuenta", "configuración accesibilidad", "cerrar sesión", "no cambiar"
    };

    private static JPanel panelContenedor;

    public static void asignarContenedor(JPanel panel) {
        panelContenedor = panel;
    }

    public static void cambiarContenido(JPanel nuevoContenido) {
        if (panelContenedor == null) {
            throw new IllegalStateException("Panel contenedor no ha sido asignado.");
        }
        panelContenedor.removeAll();
        panelContenedor.setLayout(new BorderLayout());
        panelContenedor.add(nuevoContenido, BorderLayout.CENTER);
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    public static void mostrarPantalla(PantallasEnum pantalla) throws Exception {
        switch (pantalla) {
            case MENU_PRINCIPAL ->
                    cambiarContenido(new MenuPrincipalVentana().getContenido());
            case ACCESIBILIDAD ->
                    cambiarContenido(new AccesibilidadVentana().getContenido());
            case CONFIGURACION ->
                    cambiarContenido(new ConfiguracionVentana().getContenido());
            case MODIFICAR_CONTENIDO ->
                    cambiarContenido(new ModificarContenidoVentana().getContenido());
            case INSCRIBIR_MATERIA ->
                    cambiarContenido(new InscribirMateriasVentana().getContenido());
            default ->
                    throw new IllegalArgumentException("Pantalla desconocida: " + pantalla);
        }
    }

    public static Boolean menuAccesibilidad(String nombreVentana, Window window) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return false;

        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

        String inicioCadena = "Estás en la ventana " + nombreVentana + ".";
        ttsControlador.hablar(inicioCadena + Salidas.menu, 13);

        String comando = sttControlador.esperarPorPalabrasClave(STT_LISTA);

        switch (comando) {
            case "menú principal" -> mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            case "configuración cuenta" -> mostrarPantalla(PantallasEnum.CONFIGURACION);
            case "configuración accesibilidad" -> mostrarPantalla(PantallasEnum.ACCESIBILIDAD);
            case "cerrar sesión" -> {
                ttsControlador.hablar("¿Seguro que desea cerrar sesión? Diga sí o no.");
                boolean confirmar = sttControlador.entradaAfirmacionNegacion();
                if (confirmar) {
                    cerrarTodasLasVentanas();
                    abrirVentanaInicio();
                }
            }
            case "no cambiar" -> {
                return true;
            }
        }
        return false;
    }

    private static void cerrarTodasLasVentanas() {
        for (Window w : Window.getWindows()) {
            if (w.isDisplayable()) {
                w.dispose();
            }
        }
    }

    private static void abrirVentanaInicio() {
        SwingUtilities.invokeLater(() -> {
            try {
                new InicioVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

