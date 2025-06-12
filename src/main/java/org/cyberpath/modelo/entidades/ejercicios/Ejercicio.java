package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioEjercicio;
import org.cyberpath.modelo.entidades.usuario.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TBL_EJERCICIO")
public class Ejercicio extends Entidad {
    public static final DaoImpl<Ejercicio> ejercicioDao = new DaoImpl<>(Ejercicio.class);
    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoEjercicio tipo;
    @Column(name = "descripcion")
    private String descripcion = " ";
    @ManyToOne
    @JoinColumn(name = "id_subtema")
    private Subtema subtema;
    @ManyToOne
    @JoinColumn(name = "creado_por")
    private Usuario creador;
    @Column(name = "fecha_creacion")
    private String fecha_creacion;
    @Column(name = "instrucciones")
    private String instrucciones;
    @OneToMany(mappedBy = "ejercicio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "ejercicio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEjercicio> usuarios;


    public static Boolean agregar(TipoEjercicio tipo, Usuario creador, String instrucciones, Subtema subtema) {
        String fechaSql = LocalDate.now().toString();
        try {
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setTipo(tipo);
            ejercicio.setCreador(creador);
            ejercicio.setFecha_creacion(fechaSql);
            ejercicio.setInstrucciones(instrucciones);
            ejercicio.setSubtema(subtema);
            subtema.agregarEjercicio(ejercicio);
            ejercicioDao.guardar(ejercicio);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar ejercicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Integer id, TipoEjercicio tipo, Usuario creador, String fechaCreacion, String instrucciones) {
        Ejercicio ejercicio = (Ejercicio) buscarElemento(ejercicioDao, id);
        if (ejercicio != null) {
            ejercicio.setTipo(tipo);
            ejercicio.setCreador(creador);
            ejercicio.setFecha_creacion(fechaCreacion);
            ejercicio.setInstrucciones(instrucciones);
            ejercicio.getSubtema().actualizarEjercicio(ejercicio);
            ejercicioDao.actualizar(ejercicio);
            return true;
        }
        return false;
    }

    public static Boolean eliminar(Integer id) {
        Ejercicio ejercicio = (Ejercicio) buscarElemento(ejercicioDao, id);
        if (ejercicio != null) {
            ejercicio.getSubtema().eliminarEjercicio(ejercicio);
            ejercicioDao.eliminar(ejercicio);
            return true;
        }
        return false;
    }

    public static List<Ejercicio> findBySubtema(Subtema subtema) {
        List<Ejercicio> lista = new ArrayList<>();
        for (Ejercicio ejercicio : ejercicioDao.findAll()) {
            if (ejercicio.getSubtema() == subtema) lista.add(ejercicio);
        }
        return lista;
    }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
        pregunta.setEjercicio(this);
    }
    public void eliminarPregunta(Pregunta pregunta) {
        preguntas.remove(pregunta);
        pregunta.setEjercicio(null);
    }
    public void actualizarPregunta(Pregunta preguntaActualizada) {
        preguntas.removeIf(pregunta -> pregunta.getId().equals(preguntaActualizada.getId()));
        preguntas.add(preguntaActualizada);
        preguntaActualizada.setEjercicio(this);
    }

    public void agregarEjercicios(UsuarioEjercicio usuarioEjercicio) {
        usuarios.add(usuarioEjercicio);
        usuarioEjercicio.setEjercicio(this);
    }
    public void eliminarEjercicios(UsuarioEjercicio usuarioEjercicio) {
        usuarios.remove(usuarioEjercicio);
        usuarioEjercicio.setEjercicio(null);
    }
    public void actualizarEjercicios(UsuarioEjercicio usuarioEjercicio) {
        usuarios.removeIf(ejercicioAux -> ejercicioAux.getId().equals(usuarioEjercicio.getId()));
        usuarios.add(usuarioEjercicio);
        usuarioEjercicio.setEjercicio(this);
    }

    @Override
    public String toString() {
        return instrucciones;
    }
}