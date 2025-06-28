package org.cyberpath.modelo.entidades.usuario;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioEjercicio;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_USUARIO")
public class Usuario extends Entidad {
    public static final DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol = new Rol();

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "discapacidad")
    private String discapacidad;

    @Column(name = "modo_audio")
    private Boolean modoAudio = false;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioMateria> materiasInscritas = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEjercicio> ejercicios;

    public static Boolean agregar(String nombre, String contrasena, String correo, int id_rol) {
        try {
            Rol rolExistente = new DaoImpl<>(Rol.class).findById(id_rol);
            if (rolExistente == null) {
                throw new IllegalArgumentException("El rol con ID " + id_rol + " no existe en la base de datos.");
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setContrasena(contrasena);
            usuario.setCorreo(correo);
            usuario.setRol(rolExistente); // aquí asignas el objeto gestionado

            usuarioDao.guardar(usuario);

            VariablesGlobales.usuario = usuario;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Salidas.registroError + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Usuario usuario) {
        try {
            usuarioDao.actualizar(usuario);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Salidas.errorActualizarUsuario + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean eliminar(){return true;}

    ///---

    public void agregarInscripcion(UsuarioMateria inscripcion) {
        materiasInscritas.add(inscripcion);
        inscripcion.setUsuario(this);
    }

    public void eliminarInscripcion(UsuarioMateria inscripcion) {
        materiasInscritas.remove(inscripcion);
        inscripcion.setUsuario(null);
    }

    public void actualizarInscripcion(UsuarioMateria inscripcion) {
        materiasInscritas.removeIf(inscAux -> inscAux.getId().equals(inscripcion.getId()));
        materiasInscritas.add(inscripcion);
        inscripcion.setUsuario(this);
    }

    public void agregarEjercicios(UsuarioEjercicio usuarioEjercicio) {
        ejercicios.add(usuarioEjercicio);
        usuarioEjercicio.setUsuario(this);
    }

    public void eliminarEjercicios(UsuarioEjercicio usuarioEjercicio) {
        ejercicios.remove(usuarioEjercicio);
        usuarioEjercicio.setUsuario(null);
    }

    public void actualizarEjercicios(UsuarioEjercicio usuarioEjercicio) {
        ejercicios.removeIf(ejercicioAux -> ejercicioAux.getId().equals(usuarioEjercicio.getId()));
        ejercicios.add(usuarioEjercicio);
        usuarioEjercicio.setUsuario(this);
    }

    public static boolean validarCredenciales(String nombre, String contrasena) {
        List<Usuario> list = usuarioDao.findAll();
        for (Usuario usuario : list) {
            if (Objects.equals(usuario.getNombre(), nombre) && Objects.equals(usuario.getContrasena(), contrasena)) {
                VariablesGlobales.usuario = usuario;
                return true;
            }
        }
        return false;
    }

}
