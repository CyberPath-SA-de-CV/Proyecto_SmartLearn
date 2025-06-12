package org.cyberpath.util;

public class Sistema {
    private static Sistema sistema = null;

    private Sistema() {
    }

    public static Sistema getInstance() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }

    public void pausa(Integer tiempoSegundos) {
        Integer tiempoMillis = tiempoSegundos * 1000;
        try {
            Thread.sleep(tiempoMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
