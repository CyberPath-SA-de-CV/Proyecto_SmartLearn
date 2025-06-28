package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.hibernate.Session;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_TEMA")
public class Tema extends Entidad {
    public static final DaoImpl<Tema> temaDao = new DaoImpl<>(Tema.class);

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_materia", nullable = false)
    @ToString.Exclude
    private Materia materia;

    @OneToMany(mappedBy = "tema", fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Subtema> subtemas = new ArrayList<>();

    public static Boolean agregar(String nombre, Materia materia) {
        try {
            Tema tema = new Tema();
            tema.setNombre(nombre);
            materia.agregarTema(tema);
            temaDao.guardar(tema);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar tema: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Integer id, String nombre) {
        Tema tema = (Tema) buscarElemento(temaDao, id);
        if (tema != null) {
            tema.setNombre(nombre);
            tema.getMateria().actualizarTema(tema);
            temaDao.actualizar(tema);
            return true;
        }
        return false;
    }

    public static Boolean actualizar(Integer id, Materia materia) {
        Tema tema;
        tema = (Tema) buscarElemento(temaDao, id);
        assert tema != null;
        tema.getMateria().eliminarTema(tema);
        materia.agregarTema(tema);
        temaDao.actualizar(tema);
        return true;
    }

    public static Boolean eliminar(Integer id) {
        Tema tema = (Tema) buscarElemento(temaDao, id);
        if (tema != null) {
            temaDao.eliminar(tema);
            tema.getMateria().eliminarTema(tema);
            return true;
        }
        return false;
    }

    public void agregarSubtema(Subtema subtema) {
        subtemas.add(subtema);
        subtema.setTema(this);
    }

    public void eliminarSubtema(Subtema subtema) {
        subtema.setTema(null);
    }

    public void actualizarSubtema(Subtema subtemaActualizado) {
        subtemas.removeIf(subtema -> subtema.getId().equals(subtemaActualizado.getId()));
        subtemas.add(subtemaActualizado);
        subtemaActualizado.setTema(this);
    }

    public static List<Tema> findByMateria(Materia materia) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Tema t WHERE t.materia = :materia", Tema.class)
                    .setParameter("materia", materia)
                    .getResultList();
        }
    }


    @Override
    public String toString() {
        return nombre;
    }
}