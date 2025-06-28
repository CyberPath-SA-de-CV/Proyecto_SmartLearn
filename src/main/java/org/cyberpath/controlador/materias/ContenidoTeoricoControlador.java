package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;

import java.io.IOException;
import java.util.Objects;

public class ContenidoTeoricoControlador {

    private static final SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
    private static final EntradaAudioControlador sttControlador;

    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el controlador de entrada de audio", e);
        }
    }

    private static final String[] opcionesIniciales = {"volver", "reproducir"};
    private static final String[] opcionesDuranteReproduccion = {"pausar", "empezar de nuevo", "repetir", "repetir párrafo", "volver", "salir"};
    private static final String[] opcionesDurantePausa = {"continuar", "volver"};

    public static void procesarAccesibilidad(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        while(true) {
            if (!VariablesGlobales.auxModoAudio) return;

            ttsControlador.hablar("Estás en la ventana de teoría. ¿Qué deseas hacer? Volver a la ventana anterior o reproducir la teoría?", 7);
            String opcion = sttControlador.esperarPorPalabrasClave(opcionesIniciales);

            if (Objects.equals(opcion, "volver")) {
                menu.regresar();
                SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
                return;
            } else {
                ttsControlador.hablar("En cualquier momento puedes usar los comandos, pausar, empezar de nuevo o volver.", 6);
                reproducirTeoria(subtema, menu);
            }
        }
    }

    public static void reproducirTeoria(Subtema subtema, MenuPrincipalVentana menu) throws Exception {
        String texto = subtema.getContenidoTeorico().getTexto();
        String[] parrafos = texto.split("\\.\\s+");

        int i = 0;
        boolean enReproduccion = true;

        while (i < parrafos.length && enReproduccion) {
            String parrafo = parrafos[i];
            ttsControlador.hablar(parrafo);

            int numPalabras = parrafo.trim().split("\\s+").length;
            int tiempoEspera = Math.max(3, (int) (1.5 + (numPalabras * 0.5)));

            System.out.println(tiempoEspera + " segundos para continuar");

            String comando = sttControlador.esperarPorPalabrasClaveConTimeout(opcionesDuranteReproduccion, tiempoEspera);

            if (comando == null) {
                i++;
                continue;
            }

            switch (comando) {
                case "pausar":
                    ttsControlador.hablar("Reproducción pausada. Di 'continuar' para seguir o 'volver' para salir.", 5);
                        comando = sttControlador.esperarPorPalabrasClaveConTimeout(opcionesDurantePausa, 30);
                        if ("continuar".equals(comando)) break;
                        if ("volver".equals(comando)) {
                            menu.regresar();
                            if (ContenidoPracticoControlador.banderaTeoria) {
                                ContenidoPracticoControlador.banderaTeoria = false;
                                ContenidoPracticoControlador.procesarAccesibilidad(subtema, menu);
                            } else {
                                SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
                            }
                            return;
                        }
                    break;

                case "repetir":
                case "repetir párrafo":
                    break;

                case "empezar de nuevo":
                    i = 0;
                    continue;

                case "volver":
                case "salir":
                    menu.regresar();
                    if (ContenidoPracticoControlador.banderaTeoria) {
                        ContenidoPracticoControlador.banderaTeoria = false;
                        ContenidoPracticoControlador.procesarAccesibilidad(subtema, menu);
                    } else {
                        SubtemaControlador.procesarAccesibilidad(subtema.getTema(), menu);
                    }
                    return;

                default:
                    i++;
                    break;
            }
        }

        ttsControlador.hablar("Has llegado al final del contenido teórico.");
    }



}

