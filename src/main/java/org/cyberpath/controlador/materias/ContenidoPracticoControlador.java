package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioEjercicio;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.cyberpath.modelo.entidades.ejercicios.Opcion;
import org.cyberpath.modelo.entidades.ejercicios.Pregunta;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ContenidoPracticoControlador {

    public static boolean banderaTeoria = false;

    private static final SalidaAudioControlador tts = SalidaAudioControlador.getInstance();
    private static final EntradaAudioControlador stt;
    private static final String[] opcionesIniciales = {"regresar", "ejercicios", "escuchar ejercicios"};

    static {
        try {
            stt = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el controlador de entrada de audio", e);
        }
    }

    public static void procesarAccesibilidad(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return;

        tts.hablar("Estás en la ventana de práctica. ¿Qué deseas hacer? Regresar a la ventana anterior o escuchar la lista de ejercicios?", 7);
        String opcion = stt.esperarPorPalabrasClave(opcionesIniciales);

        if ("regresar".equals(opcion)) {
            menu.regresar();
            SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
        } else {
            mostrarEjercicios(subtema, menu);
        }
    }

    private static void mostrarEjercicios(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        List<Ejercicio> ejercicios = subtema.getEjercicios();

        if (ejercicios.isEmpty()) {
            tts.hablar("No hay ejercicios disponibles para este subtema.", 5);
            return;
        }

        tts.hablar("A continuación, se enumerarán los ejercicios disponibles.", 5);

        List<String> nombresEjercicios = new ArrayList<>();
        for (Ejercicio ejercicio : ejercicios) {
            String instruccion = ejercicio.getInstrucciones().toLowerCase();
            tts.hablar(instruccion, 3);
            nombresEjercicios.add(instruccion);
        }

        tts.hablar("¿Cuál ejercicio deseas realizar?", 4);
        String nombreSeleccionado = stt.esperarPorPalabrasClave(nombresEjercicios.toArray(new String[0])).toLowerCase();

        for (Ejercicio ejercicio : ejercicios) {
            if (nombreSeleccionado.equals(ejercicio.getInstrucciones().toLowerCase())) {
                tts.hablar("A continuación se leerá el enunciado de la pregunta y sus posibles respuestas. Al final, escoja una de ellas.");
                ejecutarEjercicio(ejercicio, menu);
                break;
            }
        }
    }

    private static void ejecutarEjercicio(Ejercicio ejercicio, MenuPrincipalVentana menu) throws Exception {
        int tipo = ejercicio.getTipo().getId();

        if (tipo == 1) {
            ejecutarEjercicioSimple(ejercicio, menu);
        } else if (tipo == 2) {
            ejecutarCuestionario(ejercicio, menu);
        }

        UsuarioEjercicio.agregar(VariablesGlobales.usuario, ejercicio);
    }

    private static void ejecutarEjercicioSimple(Ejercicio ejercicio, MenuPrincipalVentana menu) throws Exception {
        tts.hablar(ejercicio.getInstrucciones(), 5);
        tts.hablar("Por favor, proporciona tu respuesta.", 5);

        String respuesta = stt.esperarPorPalabrasClave(new String[]{}); // No hay opciones predefinidas

        boolean correcta = validarRespuestaLibre(ejercicio, respuesta);

        if (correcta) {
            tts.hablar("¡Respuesta correcta!", 3);
        } else {
            String respuestaCorrecta = obtenerRespuestaCorrecta(ejercicio.getPreguntas().get(0));
            tts.hablar("Respuesta incorrecta. La respuesta correcta es: " + respuestaCorrecta, 5);

            int opcion = mostrarDialogoConfirmacion("¿Quieres consultar la teoría relacionada?", "Respuesta Incorrecta");
            if (opcion == JOptionPane.YES_OPTION) {
                menu.mostrarContenidoTeorico(ejercicio.getSubtema());
            }
        }
    }

    private static boolean validarRespuestaLibre(Ejercicio ejercicio, String respuesta) {
        return Objects.equals(
                ejercicio.getPreguntas().get(0).getOpciones().get(0).getTexto(),
                respuesta
        );
    }

    private static void ejecutarCuestionario(Ejercicio ejercicio, MenuPrincipalVentana menu) throws Exception {
        List<Pregunta> preguntas = ejercicio.getPreguntas();
        AtomicInteger correctas = new AtomicInteger();
        AtomicInteger incorrectas = new AtomicInteger();
        StringBuilder resumen = new StringBuilder();

        String[] letras = {"a", "b", "c", "d"};

        for (Pregunta pregunta : preguntas) {
            tts.hablar(pregunta.getEnunciado(), 1);//PENDIENTE DE REVISIÓN---

            int limiteOpciones = Math.min(pregunta.getOpciones().size(), letras.length);
            for (int i = 0; i < limiteOpciones; i++) {
                tts.hablar(letras[i] + ". " + pregunta.getOpciones().get(i).getTexto(), 3);
            }

            tts.hablar("¿Cuál es tu respuesta? a, b, c o d", 5);
            String seleccion = stt.esperarPorPalabrasClave(letras);

            int indice = Arrays.asList(letras).indexOf(seleccion);
            boolean correcta = (indice >= 0 && indice < pregunta.getOpciones().size()) &&
                    pregunta.getOpciones().get(indice).getEs_correcta();

            if (correcta) {
                correctas.incrementAndGet();
                tts.hablar("¡Respuesta correcta!", 2);
            } else {
                incorrectas.incrementAndGet();
                String correctaTexto = obtenerRespuestaCorrecta(pregunta);
                resumen.append("Pregunta: ").append(pregunta.getEnunciado()).append("\n")
                        .append("Respuesta correcta: ").append(correctaTexto).append("\n\n");
                tts.hablar("Respuesta incorrecta. La respuesta correcta es: " + correctaTexto, 5);
            }
        }

        String resumenFinal = String.format(
                "Resumen:\nCorrectas: %d\nIncorrectas: %d\n\n%s¿Quieres consultar la teoría? Diga sí o no.",
                correctas.get(), incorrectas.get(), resumen
        );

        tts.hablar(resumenFinal, 10);

        if (stt.entradaAfirmacionNegacion()) {
            banderaTeoria = true;
            menu.mostrarContenidoTeorico(ejercicio.getSubtema());
        } else {
            banderaTeoria = false;
            menu.regresar();
            procesarAccesibilidad(ejercicio.getSubtema(), menu);
        }
    }

    private static String obtenerRespuestaCorrecta(Pregunta pregunta) {
        return pregunta.getOpciones().stream()
                .filter(Opcion::getEs_correcta)
                .map(Opcion::getTexto)
                .findFirst()
                .orElse("Desconocida");
    }

    private static int mostrarDialogoConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }
}
