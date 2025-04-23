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
@Table(name = "TBL_SUBTEMA")
public class Subtema extends Entidad {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    //@ManyToOne(fetch = FetchType.LAZY)
    /*@JoinColumn*/@Column(name = "id_tema", nullable = false)
    private Integer id_tema;

    //@OneToMany(fetch = FetchType.LAZY)
    @Column(name = "id_contenido", nullable = false)
    private Integer id_contenido;
}
