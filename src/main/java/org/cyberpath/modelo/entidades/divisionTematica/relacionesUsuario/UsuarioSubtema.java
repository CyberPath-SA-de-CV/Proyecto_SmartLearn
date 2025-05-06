package org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario;

import jakarta.persistence.*;
import lombok.Data;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tbl_usuario_subtema")
public class UsuarioSubtema extends Entidad {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subtema", nullable = false)
    private Subtema subtema;

    @Column(name = "progreso")
    private Integer progreso;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
}
