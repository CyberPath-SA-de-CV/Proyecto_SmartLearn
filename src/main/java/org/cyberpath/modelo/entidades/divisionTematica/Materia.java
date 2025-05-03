package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;

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

    public List<Tema> getTemas() {
        return temas;
    }

    public static Boolean agregar(String nombre) {
        try {
            Materia materia = new Materia();
            materia.setNombre(nombre);
            materiaDao.guardar(materia);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar materia" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public static Boolean actualizar(/*Pendinete*/) {return true;/*Pendiente*/}
    public static Boolean eliminar(/*Pendinete*/) {return true;/*Pendiente*/}


    public void agregarTema(Tema tema) {
        temas.add(tema);
        tema.setMateria(this);
    }
    public void eliminarTema(Tema tema) {
        temas.remove(tema);
        tema.setMateria(null);
    }
}
