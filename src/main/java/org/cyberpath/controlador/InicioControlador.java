package org.cyberpath.controlador;

import org.cyberpath.util.VariablesGlobales;

import java.io.IOException;

import static org.cyberpath.controlador.SalidaAudioControlador.hablar;

public class InicioControlador {

    public static void procesarInicio() throws Exception {
        String mensaje = "¿Desea continuar con el modo de audio activado?, se escuchará así acompañándolo durante todo el programa, exprese su respuesta que, puede ser sí o no, en voz alta por favor";

        // Hablar el mensaje en un hilo separado
        Thread hiloHablar = new Thread(() -> {
            hablar(mensaje);
        });
        hiloHablar.start();

        // Mientras tanto, escuchar al usuario
        VariablesGlobales.auxModoAudio = EntradaAudioControlador.entradaAfirmacionNegacion();
    }


}