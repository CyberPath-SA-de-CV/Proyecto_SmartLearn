package org.cyberpath.controlador.combo;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuPrincipalControlador {
    public static Materia procesarAccesibilidad(Window window) throws Exception {
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
        String[] sttLista = {"materias", "lista materias", "materias inscritas", "lista de materias inscritas", "inscribir materias", "inscribir una materia", "inscribir", "repetir", "repetir el menu", "menu"};
        List<Materia> materiasInscritas = new DaoImpl<Usuario>().obtenerMateriasInscritasPorUsuario(VariablesGlobales.usuario.getId());
        ArrayList<String> sttListaMaterias = new ArrayList<>();
        if (VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("¿Qué desea hacer? escuchar lista de materias incritas. incribir una materia. repetir el menú.", 6);
            String opcion = sttControlador.esperarPorPalabrasClave(sttLista);
            if (Objects.equals(opcion, "materias") || Objects.equals(opcion, "lista materias") || Objects.equals(opcion, "materias incritas") || Objects.equals(opcion, "lista de materias inscritas")) {
                if (!materiasInscritas.isEmpty()) {
                    ttsControlador.hablar("Acontinuación se hará una lista de todas las materias incritas.", 5);
                    for (Materia materia : materiasInscritas) {
                        ttsControlador.hablar((materia.getNombre() + "."), 2);
                        sttListaMaterias.add(materia.getNombre().toLowerCase().trim());
                    }
                    ttsControlador.hablar("¿A Cuál deseas acceder?");
                    String nombreMateria = sttControlador.esperarPorPalabrasClave(sttListaMaterias.toArray(new String[0])).toLowerCase().trim();
                    for (Materia materia : materiasInscritas) {
                        if (Objects.equals(nombreMateria, materia.getNombre().toLowerCase().trim())) return materia;
                    }
                } else {
                    ttsControlador.hablar("No hay materias inscritas aún", 3);
                }
            } else if (Objects.equals(opcion, "repetir") || Objects.equals(opcion, "repetir el menu") || Objects.equals(opcion, "menu")) {
                PantallasControlador.menuAccesibilidad("Materias", window);
                procesarAccesibilidad(window);
            } else {
                PantallasControlador.mostrarPantalla(PantallasEnum.INSCRIBIR_MATERIA);
            }
        }
        return null;
    }

}
