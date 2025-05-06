package org.cyberpath.modelo.entidades.ejercicios;

import java.util.Objects;

public enum TipoEjercicioEnum {
    EJERCICIO(TipoEjercicio.tipoEjercicioDao.findById(1), "ejercicio"),
    CUESTIONARIO(TipoEjercicio.tipoEjercicioDao.findById(2), "cuestionario");

    private TipoEjercicio tipoEjericio;
    private String tipoString;

    TipoEjercicioEnum(TipoEjercicio tipoEjericio, String tipoString) {
        this.tipoEjericio = tipoEjericio;
        this.tipoString = tipoString;
    }

    public static TipoEjercicio getTipoEjercicio(String tipo){
        if(Objects.equals(EJERCICIO.tipoString, tipo)){
            return EJERCICIO.tipoEjericio;
        } else if (Objects.equals(CUESTIONARIO.tipoString, tipo)){
            return CUESTIONARIO.tipoEjericio;
        } else {
            return null;
        }
    }
}
