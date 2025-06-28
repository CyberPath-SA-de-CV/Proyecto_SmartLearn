package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubtemaControlador {

    public static void procesarAccesibilidad(Tema tema, MenuPrincipalVentana menu) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return;

        SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
        EntradaAudioControlador stt = EntradaAudioControlador.getInstance();

        String[] opcionesGenerales = {"regresar", "navegar", "navegar subtemas disponibles", "subtemas"};
        String[] opcionesSeccion = {"teoría", "teoria", "contenido teórico", "contenido", "práctica"};

        tts.hablar("Estás en la ventana de subtemas. ¿Qué desea hacer? Regresar o navegar a un subtema disponible.", 6);
        String eleccion = stt.esperarPorPalabrasClave(opcionesGenerales);

        if (Objects.equals(eleccion, "regresar")) {
            menu.regresar();
            TemaControlador.procesarAccesibilidad(tema.getMateria(), menu);
            return;
        }

        List<String> nombresSubtemas = new ArrayList<>();
        for (Subtema subtema : tema.getSubtemas()) {
            tts.hablar(subtema.getNombre() + ".", 3);
            nombresSubtemas.add(subtema.getNombre().toLowerCase());
        }

        tts.hablar("¿A cuál deseas acceder?");
        String nombreSubtema = stt.esperarPorPalabrasClave(nombresSubtemas.toArray(new String[0])).toLowerCase();

        Subtema subtemaSeleccionado = obtenerSubtemaSeleccionado(nombreSubtema, tema.getSubtemas());

        if (subtemaSeleccionado == null) {
            tts.hablar("Subtema no encontrado. Regresando al menú anterior.");
            menu.regresar();
            return;
        }

        tts.hablar("¿A qué sección deseas acceder? ¿Teoría o Práctica?");
        String seccion = stt.esperarPorPalabrasClave(opcionesSeccion);

        if (seccion.equalsIgnoreCase("práctica")) {
            menu.mostrarContenidoPractico(subtemaSeleccionado);
        } else {
            menu.mostrarContenidoTeorico(subtemaSeleccionado);
        }
    }

    private static Subtema obtenerSubtemaSeleccionado(String nombre, List<Subtema> listaSubtemas) {
        for (Subtema s : listaSubtemas) {
            if (s.getNombre().equalsIgnoreCase(nombre)) {
                return s;
            }
        }
        return null;
    }
}
