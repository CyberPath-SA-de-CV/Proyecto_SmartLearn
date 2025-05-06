package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;

import javax.swing.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TBL_TIPO_EJERCICIO")
public class TipoEjercicio extends Entidad {
    public static DaoImpl<TipoEjercicio> tipoEjercicioDao = new DaoImpl<>(TipoEjercicio.class);

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Override
    public String toString() {
        return nombre;
    }
}