package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.cyberpath.modelo.entidades.ejercicios.Opcion;
import org.cyberpath.modelo.entidades.ejercicios.Pregunta;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
///MOVER VELOCIDAD CON LA QUE SE DICEN LAS OPCION, VELOCIDAD CON LA QUE SE PUEDE RESPONDER (EL PEQUEÑO VACIO QUE QUEDA) Y
public class ContenidoPracticoControlador {
    public static Boolean banderaTeoria = false;
    private static EntradaAudioControlador sttControlador;
    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

    public static void procesarAccesibilidad(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        String[] sttListaOpciones = {"regresar", "ejercicios", "escuchar ejercicios"};
        if (VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("Estás en la ventana de práctica. ¿Qué deseas hacer? regresar a la ventana anterior o escuchar la lista de ejercicios?", 7);
            String opcion = sttControlador.esperarPorPalabrasClave(sttListaOpciones);
            if (Objects.equals(opcion, "regresar")) {
                menu.regresar();
                SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
            } else {
                mostrarEjercicios(subtema, menu);
            }
        }
    }

    private static void mostrarEjercicios(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        List<Ejercicio> ejercicios = subtema.getEjercicios();
        if (ejercicios.isEmpty()) {
            ttsControlador.hablar("No hay ejercicios disponibles para este subtema.", 5);
            return;
        }

        ttsControlador.hablar("A continuación, se enumerarán los ejercicios disponibles.", 5);
        ArrayList<String> sttListaEjercicios = new ArrayList<>();
        for (Ejercicio ejercicio : ejercicios) {
            ttsControlador.hablar(ejercicio.getInstrucciones(), 3);
            sttListaEjercicios.add(ejercicio.getInstrucciones().toLowerCase());
        }

        ttsControlador.hablar("¿Cuál ejercicio deseas realizar?", 5);
        String nombreEjercicio = sttControlador.esperarPorPalabrasClave(sttListaEjercicios.toArray(new String[0])).toLowerCase();

        for (Ejercicio ejercicio : ejercicios) {
            if (nombreEjercicio.equals(ejercicio.getInstrucciones().toLowerCase())) {
                ejecutarEjercicio(ejercicio, menu);
                break;
            }
        }
    }

    private static void ejecutarEjercicio(Ejercicio ejercicio, MenuPrincipalVentana menu) throws Exception {
        if (ejercicio.getTipo().getId() == 1) {
            ttsControlador.hablar(ejercicio.getInstrucciones(), 5);
            ttsControlador.hablar("Por favor, proporciona tu respuesta.", 5);

            String respuesta = sttControlador.esperarPorPalabrasClave(new String[]{});
            boolean esCorrecta = validarRespuestaEjercicio(ejercicio, respuesta);

            if (esCorrecta) {
                ttsControlador.hablar("¡Respuesta correcta!", 3);
            } else {
                String correcta = ejercicio.getPreguntas().get(0).getOpciones().stream()
                        .filter(Opcion::getEs_correcta)
                        .map(Opcion::getTexto)
                        .findFirst()
                        .orElse("Desconocida");
                ttsControlador.hablar("Respuesta incorrecta. La respuesta correcta es: " + correcta, 5);
                int opcion = mostrarDialogoConfirmacionModerno(
                        "¿Quieres consultar la teoría relacionada?",
                        "Respuesta Incorrecta"
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    menu.mostrarContenidoTeorico(ejercicio.getSubtema());
                }
            }
        } else if (ejercicio.getTipo().getId() == 2) {
            ejecutarCuestionario(ejercicio, menu);
        }
    }

    private static boolean validarRespuestaEjercicio(Ejercicio ejercicio, String respuesta) {
        return Objects.equals(ejercicio.getPreguntas().get(0).getOpciones().get(0).getTexto(), respuesta);
    }

    private static void ejecutarCuestionario(Ejercicio ejercicio, MenuPrincipalVentana menu) throws Exception {
        List<Pregunta> preguntas = ejercicio.getPreguntas();
        AtomicInteger correctas = new AtomicInteger();
        AtomicInteger incorrectas = new AtomicInteger();
        StringBuilder resumenIncorrectas = new StringBuilder();

        for (Pregunta pregunta : preguntas) {
            ttsControlador.hablar(pregunta.getEnunciado(), 5);
            ArrayList<String> opcionesTexto = new ArrayList<>();
            String[] li = {"a", "b", "c", "d"};

            // Asegúrate de que el número de opciones no exceda 4
            int numOpciones = Math.min(pregunta.getOpciones().size(), li.length);

            for (int aux = 0; aux < numOpciones; aux++) {
                Opcion opcion = pregunta.getOpciones().get(aux);
                ttsControlador.hablar(li[aux] + ". " + opcion.getTexto(), 3);
                opcionesTexto.add(opcion.getTexto());
            }

            ttsControlador.hablar("¿Cuál es tu respuesta? a, b, c o d", 5);
            String seleccion = sttControlador.esperarPorPalabrasClave(li);

            int indiceSeleccion = -1;
            for (int i = 0; i < li.length; i++) {
                if (li[i].equals(seleccion)) {
                    indiceSeleccion = i;
                    break;
                }
            }

            boolean esCorrecta = false;
            if (indiceSeleccion != -1 && indiceSeleccion < pregunta.getOpciones().size()) {
                esCorrecta = pregunta.getOpciones().get(indiceSeleccion).getEs_correcta();
            }

            if (esCorrecta) {
                correctas.getAndIncrement();
                ttsControlador.hablar("¡Respuesta correcta!", 3);
            } else {
                incorrectas.getAndIncrement();
                String correcta = pregunta.getOpciones().stream()
                        .filter(Opcion::getEs_correcta)
                        .map(Opcion::getTexto)
                        .findFirst()
                        .orElse("Desconocida");
                resumenIncorrectas.append("Pregunta: ").append(pregunta.getEnunciado())
                        .append("\nRespuesta correcta: ").append(correcta).append("\n\n");
                ttsControlador.hablar("Respuesta incorrecta. La respuesta correcta es: " + correcta, 5);
            }
        }


        String resumen = "Resumen:\nCorrectas: " + correctas + "\nIncorrectas: " + incorrectas + "\n\n" + resumenIncorrectas + "¿Quieres consultar la teoría, diga sí o no?";
        ttsControlador.hablar(resumen, 10);
        if (sttControlador.entradaAfirmacionNegacion()) {
            banderaTeoria = true;
            menu.mostrarContenidoTeorico(ejercicio.getSubtema());
            ContenidoTeoricoControlador.procesarAccesibilidad(ejercicio.getSubtema(), menu);
        } else {
            banderaTeoria = false;
            menu.regresar();
            ContenidoPracticoControlador.procesarAccesibilidad(ejercicio.getSubtema(), menu);
        }

    }

    private static int mostrarDialogoConfirmacionModerno(String mensaje, String titulo) {
        // Implementación del diálogo de confirmación
        return JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }
}
