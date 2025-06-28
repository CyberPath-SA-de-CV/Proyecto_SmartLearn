package org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "TBL_USUARIO_MATERIA")
public class UsuarioMateria extends Entidad {
    public static DaoImpl<UsuarioMateria> usuarioMateriaDao = new DaoImpl<>(UsuarioMateria.class);

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
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

    public static Boolean eliminar(Materia materia) {
        for(UsuarioMateria usuarioMateria : usuarioMateriaDao.findAll()){
            if(Objects.equals(usuarioMateria.getUsuario().getId(), VariablesGlobales.usuario.getId())
               && Objects.equals(usuarioMateria.getMateria().getId(), materia.getId())){

                usuarioMateriaDao.eliminar(usuarioMateria);

                VariablesGlobales.usuario.eliminarInscripcion(usuarioMateria);
                materia.eliminarInscripcion(usuarioMateria);
                return true;
            }
        }
        return false;
    }

    public static List<Materia> obtenerMateriasInscritasPorUsuario(int idUsuario) {
        try (Session session = HibernateUtil.getSession()) {
            Usuario usuario = session.get(Usuario.class, idUsuario);
            Hibernate.initialize(usuario.getMateriasInscritas());

            return usuario.getMateriasInscritas().stream()
                    .map(inscripcion -> {
                        Materia materia = inscripcion.getMateria();
                        Hibernate.initialize(materia);
                        return materia;
                    })
                    .collect(Collectors.toList());
        }
    }

}