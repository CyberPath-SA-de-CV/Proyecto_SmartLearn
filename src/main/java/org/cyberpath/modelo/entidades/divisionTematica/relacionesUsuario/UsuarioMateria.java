package org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import javax.swing.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TBL_USUARIO_MATERIA")
public class UsuarioMateria extends Entidad {
    public static DaoImpl<UsuarioMateria> usuarioMateriaDao = new DaoImpl<>(UsuarioMateria.class);

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    public UsuarioMateria() {
    }

    public static Boolean agregar(Usuario usuario, Materia materia) {
        try {
            UsuarioMateria usuarioMateria = new UsuarioMateria();
            usuarioMateria.setUsuario(usuario);
            usuarioMateria.setMateria(materia);
            usuarioMateria.setFechaInscripcion(LocalDate.now());
            usuario.agregarInscripcion(usuarioMateria);
            materia.agregarInscripcion(usuarioMateria);
            usuarioMateriaDao.guardar(usuarioMateria);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar relacion Usuario Materia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}