package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_PREGUNTA")//
public class Pregunta extends Entidad {

    @Column(name = "id_ejercicio")//
    private Integer id_ejercicio;

    @Column(name = "enunciado")//
    private String enunciado;
}
