package org.cyberpath.modelo.entidades.contenido;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_CONTENIDO")
public class Contenido extends Entidad {

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "ultima_edicion")
    private LocalDate ultimaEdicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subtema", nullable = false)
    private Subtema subtema;


}
