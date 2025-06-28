package org.cyberpath.controlador.materias;

import org.cyberpath.controlador.combo.MenuPrincipalControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.util.Sistema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemaControlador {

    private static final String[] opciones_generales = {"regresar", "navegar", "navegar temas disponibles", "temas"};

    public static Tema procesarAccesibilidad(Materia materia, MenuPrincipalVentana menu) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return null;

        Sistema sistema = Sistema.getInstance();
        SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
        EntradaAudioControlador stt = EntradaAudioControlador.getInstance();

        if(materia.getProgresoGeneral() != null) tts.hablar("Estás en la ventana de temas de la materia" + materia.getNombre() + ". Con un " + materia.getProgresoGeneral() + " porciento de progreso actual ¿Qué desea hacer? ¿Regresar o navegar a un tema disponible?", 12);
        else tts.hablar("Estás en la ventana de temas de la materia" + materia.getNombre() +  "¿Qué desea hacer? ¿Regresar o navegar a un tema disponible?", 9);

        String opcion = stt.esperarPorPalabrasClave(opciones_generales);

        if (Objects.equals(opcion, "regresar")) {
            menu.regresar();
            if (PantallasControlador.menuAccesibilidad("Materias", menu)) {
                sistema.pausa(2);
                menu.mostrarTemas(MenuPrincipalControlador.procesarAccesibilidad(menu));
            }
            return null;
        }

        return seleccionarTemaPorVoz(materia, tts, stt);
    }

    private static Tema seleccionarTemaPorVoz(Materia materia, SalidaAudioControlador tts, EntradaAudioControlador stt) throws Exception {
        List<String> nombresTemas = new ArrayList<>();
        for (Tema t : materia.getTemas()) {
            tts.hablar(t.getNombre() + ".", 3);
            nombresTemas.add(t.getNombre().toLowerCase());
        }

        tts.hablar("¿A cuál tema deseas acceder?", 3);
        String nombreTema = stt.esperarPorPalabrasClave(nombresTemas.toArray(new String[0])).toLowerCase();

        for (Tema t : materia.getTemas()) {
            if (Objects.equals(nombreTema, t.getNombre().toLowerCase())) {
                return t;
            }
        }

        tts.hablar("Tema no encontrado. Regresando al menú.");
        return null;
    }
}
