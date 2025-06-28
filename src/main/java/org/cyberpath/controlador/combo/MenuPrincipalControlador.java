package org.cyberpath.controlador.combo;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuPrincipalControlador {

    private static final String[] OPCIONES_VOZ = {
            "materias", "lista materias", "materias inscritas", "lista de materias", "lista de materias inscritas",
            "inscribir materias", "inscribir una materia", "inscribir",
            "repetir", "repetir el menu", "menu"
    };

    public static Materia procesarAccesibilidad(JFrame window) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return null;

        EntradaAudioControlador stt = EntradaAudioControlador.getInstance();
        SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
        DaoImpl<Usuario> usuarioDao = new DaoImpl<>();
        List<Materia> materiasInscritas = usuarioDao.obtenerMateriasInscritasPorUsuario(VariablesGlobales.usuario.getId());

        tts.hablar("¿Qué deseas hacer? Puedes escuchar la lista de materias inscritas, inscribir una materia o repetir el menú.", 6);
        String opcion = stt.esperarPorPalabrasClave(OPCIONES_VOZ).toLowerCase().trim();

        if (opcionListaMaterias(opcion)) {
            if (materiasInscritas.isEmpty()) {
                tts.hablar("No tienes materias inscritas aún.", 2);
                return null;
            }
            return manejarListaMaterias(stt, tts, materiasInscritas);
        }
        else if (opcionRepetirMenu(opcion)) {
            boolean continuar = PantallasControlador.menuAccesibilidad("Materias", window);
            if (continuar) {
                return procesarAccesibilidad(window);
            }
            return null;
        }

        PantallasControlador.mostrarPantalla(PantallasEnum.INSCRIBIR_MATERIA);
        return null;
    }

    private static boolean opcionListaMaterias(String opcion) {
        return opcion.equals("materias") ||
                opcion.equals("lista materias") ||
                opcion.equals("lista de materias") ||
                opcion.equals("materias inscritas") ||
                opcion.equals("lista de materias inscritas");
    }
//EL ERROR DE NUL PORCIENTO SUCEDE CUANDO CARGA PRIMERO EL CONTROLADOR QUE LA PARTE DE VENTANA
    private static boolean opcionRepetirMenu(String opcion) {
        return opcion.equals("repetir") ||
                opcion.equals("repetir el menu") ||
                opcion.equals("menu");
    }

    private static Materia manejarListaMaterias(EntradaAudioControlador stt, SalidaAudioControlador tts, List<Materia> materiasInscritas) throws Exception {
        ArrayList<String> listaNombres = new ArrayList<>();
        tts.hablar("Aquí está la lista de tus materias inscritas:", 5);

        for (Materia materia : materiasInscritas) {
            String nombre = materia.getNombre().toLowerCase().trim();
            tts.hablar(materia.getNombre() + ".", 3);
            listaNombres.add(nombre);
        }

        tts.hablar("¿A cuál deseas acceder?");
        String nombreMateria = stt.esperarPorPalabrasClave(listaNombres.toArray(new String[0])).toLowerCase().trim();

        for (Materia materia : materiasInscritas) {
            if (Objects.equals(nombreMateria, materia.getNombre().toLowerCase().trim())) {
                return materia;
            }
        }
        return null;
    }
}
