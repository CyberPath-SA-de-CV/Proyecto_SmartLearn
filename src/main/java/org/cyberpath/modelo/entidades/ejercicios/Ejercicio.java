package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
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

    @OneToMany(mappedBy = "ejercicio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas = new ArrayList<>();

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

    public static final DaoImpl<Ejercicio> ejercicioDao = new DaoImpl<>(Ejercicio.class);

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

    public static List<Ejercicio> findBySubtema(Subtema subtema){
        List<Ejercicio> lista = new ArrayList<>();
        for(Ejercicio ejercicio : ejercicioDao.findAll()){
            if(ejercicio.getSubtema() == subtema) lista.add(ejercicio);
        }
        return lista;
    }

    @Override
    public String toString() {
        return instrucciones;
    }
}