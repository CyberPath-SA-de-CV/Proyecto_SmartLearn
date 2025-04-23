package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_TEMA")
public class Tema extends Entidad {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "id_materia", nullable = false)
    private Integer id_materia;
}