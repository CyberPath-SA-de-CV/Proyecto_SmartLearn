package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.ejercicios.Pregunta;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_OPCION")
public class Opcion extends Entidad {

    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "es_correcta", nullable = false)
    private Short es_correcta;
}