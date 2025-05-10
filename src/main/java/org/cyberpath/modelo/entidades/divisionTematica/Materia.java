package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "TBL_MATERIA")
public class Materia extends Entidad {
    public static final DaoImpl<Materia> materiaDao = new DaoImpl<>(Materia.class);

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "materia", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Tema> temas = new ArrayList<>();

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioMateria> usuarioMaterias = new ArrayList<>();

    public static Boolean agregar(String nombre) {
        try {
            Materia materia = new Materia();
            materia.setNombre(nombre);
            materiaDao.guardar(materia);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar materia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Integer id, String nombre) {
        Materia materia = (Materia) buscarElemento(materiaDao, id);
        if (materia != null) {
            materia.setNombre(nombre);
            materiaDao.actualizar(materia);
            return true;
        }
        return false;
    }

    public static Boolean eliminar(Integer id) {
        Materia materia = (Materia) buscarElemento(materiaDao, id);
        if (materia != null) {
            materiaDao.eliminar(materia);
            return true;
        }
        return false;
    }

    public void agregarTema(Tema tema) {
        temas.add(tema);
        tema.setMateria(this);
    }

    public void eliminarTema(Tema tema) {
        temas.remove(tema);
        tema.setMateria(null);
    }

    public void actualizarTema(Tema temaActualizado) {
        temas.removeIf(tema -> tema.getId().equals(temaActualizado.getId()));
        temas.add(temaActualizado);
        temaActualizado.setMateria(this);
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}