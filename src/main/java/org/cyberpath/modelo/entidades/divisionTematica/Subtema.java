package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.contenido.ContenidoTeorico;

import javax.swing.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_SUBTEMA")
public class Subtema extends Entidad {

    public static final DaoImpl<Subtema> subtemaDao = new DaoImpl<>(Subtema.class);

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tema", nullable = false)
    @ToString.Exclude
    private Tema tema;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contenido_teorico", nullable = false, unique = true)
    @ToString.Exclude
    private ContenidoTeorico contenidoTeorico;

    /// Método para agregar subtema
    public static Boolean agregar(String nombre, Tema tema, ContenidoTeorico contenidoTeorico) {
        try {
            Subtema subtema = new Subtema();
            subtema.setNombre(nombre);
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

    // Método para actualizar un subtema
    public static Boolean actualizar(Subtema subtema, String nuevoNombre, Tema nuevoTema, ContenidoTeorico nuevoContenidoTeorico) {
        try {
            subtema.setNombre(nuevoNombre);
            subtema.setTema(nuevoTema);
            subtema.setContenidoTeorico(nuevoContenidoTeorico);
            return subtemaDao.actualizar(subtema);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar subtema: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método para eliminar un subtema
    public static Boolean eliminar(Subtema subtema) {
        try {
            return subtemaDao.eliminar(subtema);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar subtema: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
