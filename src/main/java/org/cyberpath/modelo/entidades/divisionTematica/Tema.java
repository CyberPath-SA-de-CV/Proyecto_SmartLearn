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
@NoArgsConstructor  // Para JPA
@AllArgsConstructor // Para crear fácilmente objetos completos
@Entity
@Table(name = "TBL_TEMA")
public class Tema extends Entidad {
    public static final DaoImpl<Tema> temaDao = new DaoImpl<>(Tema.class);

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    @ToString.Exclude  // Evita recursión infinita en logs
    private Materia materia;

    @OneToMany(mappedBy = "tema", fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude  // Evita recursión infinita en logs
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
            JOptionPane.showMessageDialog(null, "Error al agregar tema" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public static Boolean actualizar(/*Pendinete*/) {
        throw new UnsupportedOperationException("Método actualizar aún no implementado");
    }
    public static Boolean eliminar(/*Pendinete*/) {
        throw new UnsupportedOperationException("Método eliminar aún no implementado");
    }

    public void agregarSubtema(Subtema subtema) {
        subtemas.add(subtema);
        subtema.setTema(this);
    }
    public void eliminarSubtema(Subtema subtema) {
        subtemas.remove(subtema);
        subtema.setTema(null);
    }
}