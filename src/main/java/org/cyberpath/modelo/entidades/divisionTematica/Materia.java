package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_MATERIA")
public class Materia extends Entidad {
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL)
    private List<Tema> temas;
}
