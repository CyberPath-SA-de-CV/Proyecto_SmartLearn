package org.cyberpath.modelo.entidades.usuario;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.UsuarioMateria;
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
