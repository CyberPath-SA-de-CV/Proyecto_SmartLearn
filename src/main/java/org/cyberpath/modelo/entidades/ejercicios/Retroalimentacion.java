package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_RETROALIMENTACION")
public class Retroalimentacion extends Entidad {

    @Column(name = "id_intento", nullable = false)
    private Integer id_intento;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;
}