package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_PREGUNTA")
public class Pregunta extends Entidad {

    @ManyToOne
    @JoinColumn(name = "id_ejercicio", nullable = false)
    private Ejercicio ejercicio;

    @Column(name = "enunciado", nullable = false)
    private String enunciado;

    @OneToMany(mappedBy = "pregunta", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcion> opciones = new ArrayList<>();

    public static final DaoImpl<Pregunta> preguntaDao = new DaoImpl<>(Pregunta.class);

    public static Boolean agregar(Ejercicio ejercicio, String enunciado) {
        try {
            Pregunta pregunta = new Pregunta();
            pregunta.setEjercicio(ejercicio);
            pregunta.setEnunciado(enunciado);
            ejercicio.agregarPregunta(pregunta);
            preguntaDao.guardar(pregunta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar pregunta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Integer id, Ejercicio ejercicio, String enunciado) {
        Pregunta pregunta = (Pregunta) buscarElemento(preguntaDao, id);
        if (pregunta != null) {
            pregunta.setEjercicio(ejercicio);
            pregunta.setEnunciado(enunciado);
            pregunta.getEjercicio().agregarPregunta(pregunta);
            preguntaDao.actualizar(pregunta);
            return true;
        }
        return false;
    }

    public static Boolean eliminar(Integer id) {
        Pregunta pregunta = (Pregunta) buscarElemento(preguntaDao, id);
        if (pregunta != null) {
            pregunta.getEjercicio().eliminarPregunta(pregunta);
            preguntaDao.eliminar(pregunta);
            return true;
        }
        return false;
    }

    public void agregarOpcion(Opcion opcion) {
        opciones.add(opcion);
        opcion.setPregunta(this);
    }

    public void eliminarOpcion(Opcion opcion) {
        opciones.remove(opcion);
        opcion.setPregunta(null);
    }

    public void actualizarOpcion(Opcion opcionActualizada) {
        opciones.removeIf(opcion -> opcion.getId().equals(opcionActualizada.getId()));
        opciones.add(opcionActualizada);
        opcionActualizada.setPregunta(this);
    }
}