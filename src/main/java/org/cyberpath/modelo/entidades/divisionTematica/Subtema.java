package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.contenido.ContenidoTeorico;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.hibernate.Session;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_SUBTEMA")
public class Subtema extends Entidad {

    public static final DaoImpl<Subtema> subtemaDao = new DaoImpl<>(Subtema.class);

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tema", nullable = false)
    private Tema tema;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contenido", nullable = false, unique = true)
    private ContenidoTeorico contenidoTeorico;

    @OneToMany(mappedBy = "subtema", fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public static Boolean agregar(String nombre, Tema tema, ContenidoTeorico contenidoTeorico) {
        try {
            Subtema subtema = new Subtema();
            subtema.setNombre(nombre);
            subtema.setTema(tema);
            subtema.setContenidoTeorico(contenidoTeorico);
            tema.agregarSubtema(subtema);
            contenidoTeorico.agregarSubtema(subtema);
            subtemaDao.guardar(subtema);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar subtema: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Integer id, String nombre) {
        Subtema subtema = (Subtema) buscarElemento(subtemaDao, id);
        if (subtema != null) {
            subtema.setNombre(nombre);
            subtema.getTema().actualizarSubtema(subtema);
            subtemaDao.actualizar(subtema);
            return true;
        }
        return false;
    }

    public static Boolean actualizar(Integer id, Tema tema) {
        Subtema subtema = (Subtema) buscarElemento(subtemaDao, id);
        if (subtema != null) {
            subtema.getTema().eliminarSubtema(subtema);
            tema.agregarSubtema(subtema);
            subtemaDao.actualizar(subtema);
            return true;
        }
        return false;
    }

    public static Boolean eliminar(Integer id) {
        Subtema subtema = (Subtema) buscarElemento(subtemaDao, id);
        if (subtema != null) {
            subtema.getTema().eliminarSubtema(subtema);
            subtemaDao.eliminar(subtema);
            return true;
        }
        return false;
    }
    public static Boolean eliminar(Subtema subtema) {
        if (subtema != null) {
            subtemaDao.eliminar(subtema);
            subtema.getTema().eliminarSubtema(subtema);
            return true;
        }
        return false;
    }

    public void agregarEjercicio(Ejercicio ejercicio) {
        ejercicios.add(ejercicio);
        ejercicio.setSubtema(this);
    }

    public void eliminarEjercicio(Ejercicio ejercicio) {
        ejercicios.remove(ejercicio);
        ejercicio.setSubtema(null);
    }

    public void actualizarEjercicio(Ejercicio ejercicioActualizado) {
        ejercicios.removeIf(ejercicio -> ejercicio.getId().equals(ejercicioActualizado.getId()));
        ejercicios.add(ejercicioActualizado);
        ejercicioActualizado.setSubtema(this);
    }

    public static List<Subtema> findByTema(Tema tema) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Subtema s WHERE s.tema = :tema", Subtema.class)
                    .setParameter("tema", tema)
                    .getResultList();
        }
    }


    @Override
    public String toString() {
        return nombre;
    }
}