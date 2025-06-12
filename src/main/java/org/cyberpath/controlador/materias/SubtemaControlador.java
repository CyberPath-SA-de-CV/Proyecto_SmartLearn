package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SubtemaControlador {
    public static void procesarAccesibilidad(Tema tema, MenuPrincipalVentana menu) throws Exception {
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

        String[] sttListaOpciones = {"regresar", "navegar", "navegar subtemas disponibles", "subtemas"};
        String[] sttListaTeoriaPractica = {"teoría", "teoria", "contenido teorico", "contenido", "práctica"};
        ArrayList<String> sttListaSubtemas = new ArrayList<>();
        Subtema subtema = new Subtema();
        if (VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("Estas en la ventana subtemas. ¿Qué desea hacer? regresar a la ventana anterior. navegar a uno de los subtemas disponibles?", 6);
            String opcion1 = sttControlador.esperarPorPalabrasClave(sttListaOpciones);
            if (Objects.equals(opcion1, "regresar")) {
                menu.regresar();
                TemaControlador.procesarAccesibilidad(tema.getMateria(), menu);///////
                //PantallasControlador.menuAccesibilidad("Tema", menu);
                //Sistema.pausa(2);
            } else {
                ttsControlador.hablar("Acontinuación se hará una lista de todos los subtemas disponibles.", 5);
                for (Subtema s : tema.getSubtemas()) {
                    ttsControlador.hablar((s.getNombre() + "."), 2);
                    sttListaSubtemas.add(s.getNombre().toLowerCase());
                }
                ttsControlador.hablar("¿A cuál deseas acceder?");
                String nombreSubtema = sttControlador.esperarPorPalabrasClave(sttListaSubtemas.toArray(new String[0])).toLowerCase();
                for (Subtema s : tema.getSubtemas()) {
                    if (Objects.equals(nombreSubtema, s.getNombre().toLowerCase())) subtema = s;
                }

                ttsControlador.hablar("¿A que sección desesas acceder? Teoría o Práctica ");
                String opcion2 = sttControlador.esperarPorPalabrasClave(sttListaTeoriaPractica);
                if (Objects.equals(opcion2, "práctica")) {
                    menu.mostrarContenidoPractico(subtema);
                } else {
                    menu.mostrarContenidoTeorico(subtema);
                }

            }
        }
    }
}
