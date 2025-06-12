package org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario;

import jakarta.persistence.*;
import lombok.Data;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "tbl_usuario_ejercicio")
public class UsuarioEjercicio extends Entidad {
    public static final DaoImpl<UsuarioEjercicio> usuarioEjercicioDao = new DaoImpl<>(UsuarioEjercicio.class);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ejercicio", nullable = false)
    private Ejercicio ejercicio;
    @Column(name = "fecha_resuelto")
    private LocalDate fechaResuelto;

    public static Boolean agregar(Usuario usuario, Ejercicio ejercicio) {
        try {
            UsuarioEjercicio usuarioEjercicio = new UsuarioEjercicio();
            usuarioEjercicio.setUsuario(usuario);
            usuarioEjercicio.setEjercicio(ejercicio);
            usuarioEjercicio.setFechaResuelto(LocalDate.now());
            for(UsuarioEjercicio usuarioEjercicioAux : usuarioEjercicioDao.findAll()){
                if(Objects.equals(usuarioEjercicioAux.getEjercicio().getId(), ejercicio.getId()) && Objects.equals(usuarioEjercicioAux.getUsuario().getId(), usuario.getId())) return true;
            }
            usuario.agregarEjercicios(usuarioEjercicio);
            ejercicio.agregarEjercicios(usuarioEjercicio);
            usuarioEjercicioDao.guardar(usuarioEjercicio);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar relacion Usuario Materia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
