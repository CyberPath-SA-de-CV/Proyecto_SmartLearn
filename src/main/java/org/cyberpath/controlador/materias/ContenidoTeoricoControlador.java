package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.pantallas.materias.SubtemaVentana;

import java.io.IOException;
import java.util.Objects;

public class ContenidoTeoricoControlador {
    public static void procesarAccesibilidad(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
        String[] sttListaOpciones1 = {"regresar", "reproducir"};

        if (VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("Estas en la ventana teoría. ¿Qué desea hacer? regresar a la ventana anterior. reproducir la teoría?", 7);
            String opcion1 = sttControlador.esperarPorPalabrasClave(sttListaOpciones1);
            if (Objects.equals(opcion1, "regresar")) {
                menu.regresar();
                SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
            } else {
                ttsControlador.hablar("En cualquier momento puede decir las palabra volver a empezar o regresar para volver a la ventana anterior", 6);
                reproducirTeoria(subtema, menu);
            }
        }
    }
    public static void reproducirTeoria(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        String[] sttListaOpciones2 = {"pausar", "reanudar", "volver", "volver a empezar", "regresar"};
        EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
        SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

        ttsControlador.hablar(subtema.getContenidoTeorico().getTexto());
        switch (sttControlador.esperarPorPalabrasClave(sttListaOpciones2)) {
            case "volver", "volver a empezar" -> {
                reproducirTeoria(subtema, menu);
            }
            case "regresar" -> {
                menu.regresar();
                if(ContenidoPracticoControlador.banderaTeoria){
                    ContenidoPracticoControlador.procesarAccesibilidad(subtema, menu);
                    ContenidoPracticoControlador.banderaTeoria = false;
                } else {
                    SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
                }
            }
        }
    }
}
