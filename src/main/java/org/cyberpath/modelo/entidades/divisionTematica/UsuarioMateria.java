package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_USUARIO_MATERIA")
public class UsuarioMateria extends Entidad {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tema", nullable = false)
    private Tema tema;

    @Column(name = "progreso")
    private Integer progreso;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    public UsuarioMateria() {}

    public UsuarioMateria(Usuario usuario, Tema tema, Integer progreso, LocalDate fechaInscripcion) {
        this.usuario = usuario;
        this.tema = tema;
        this.progreso = progreso;
        this.fechaInscripcion = fechaInscripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}