package org.cyberpath.controlador.materias;

import org.cyberpath.controlador.combo.MenuPrincipalControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.util.Sistema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TemaControlador {

    public static Tema procesarAccesibilidad(Materia materia, MenuPrincipalVentana menu) throws Exception {
        Sistema sistema = Sistema.getInstance();
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
        String[] sttLista = {"regresar", "navegar", "navegar temas disponibles", "temas"};
        ArrayList<String> sttListaTemas = new ArrayList<>();
        if(VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("Estas en la ventana Temas. ¿Qué desea hacer? regresar a la ventana anterior. navegar a uno de los temas disponibles?", 6);
            String opcion = sttControlador.esperarPorPalabrasClave(sttLista);
            if (Objects.equals(opcion, "regresar")) {
                menu.regresar();
                if(PantallasControlador.menuAccesibilidad("Materias", menu) ){
                    sistema.pausa(2);
                    menu.mostrarTemas(MenuPrincipalControlador.procesarAccesibilidad(menu));
                }
            } else {
                ttsControlador.hablar("Acontinuación se hará una lista de todos los temas disponibles.", 5);
                for (Tema t : materia.getTemas()) {
                    ttsControlador.hablar((t.getNombre() + "."), 2);
                    sttListaTemas.add(t.getNombre().toLowerCase());
                }
                ttsControlador.hablar("¿A Cuál deseas acceder?");
                String nombreTema = sttControlador.esperarPorPalabrasClave(sttListaTemas.toArray(new String[0])).toLowerCase();
                for (Tema t : materia.getTemas()) {
                    if (Objects.equals(nombreTema, t.getNombre().toLowerCase())) return t;
                }
            }
        }
        return null;
    }
}
