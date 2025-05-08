package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.Data;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "TBL_USUARIO_MATERIA")
public class UsuarioMateria extends Entidad {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @Column(name = "progreso")
    private Integer progreso;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    public UsuarioMateria() {}

    public UsuarioMateria(Usuario usuario, Materia materia, Integer progreso, LocalDate fechaInscripcion) {
        this.usuario = usuario;
        this.materia = materia;
        this.progreso = progreso;
        this.fechaInscripcion = fechaInscripcion;
    }
}