package org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario;

import jakarta.persistence.*;
import lombok.Data;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tbl_estadistica_usuario")
public class EstadisticaUsuario extends Entidad {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "total_ejercicios_resueltos")
    private Integer ejerciciosResueltos;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @Column(name = "promedio_calificacion")
    private Double promCalificacion;
}
