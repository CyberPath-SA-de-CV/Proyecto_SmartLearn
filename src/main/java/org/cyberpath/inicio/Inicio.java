package org.cyberpath.inicio;

import org.cyberpath.vista.pantallas.inicio.SplashVentana;

public class Inicio {
    public static void main(String[] args) throws Exception {
        SplashVentana splash = new SplashVentana();
        splash.iniciarCarga(); // Lanza la carga y luego abre la ventana principal
    }
}
