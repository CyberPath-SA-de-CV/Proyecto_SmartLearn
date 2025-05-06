package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;

import javax.swing.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_OPCION")
public class Opcion extends Entidad {
    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "es_correcta", nullable = false)
    private Boolean es_correcta;

    public static final DaoImpl<Opcion> opcionDao = new DaoImpl<>(Opcion.class);

    public static Boolean agregar(Pregunta pregunta, String texto, Boolean esCorrecta) {
        try {
            Opcion opcion = new Opcion();
            opcion.setPregunta(pregunta);
            opcion.setTexto(texto);
            opcion.setEs_correcta(esCorrecta);
            pregunta.agregarOpcion(opcion);
            opcionDao.guardar(opcion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar opción: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(Integer id, Pregunta pregunta, String texto, Boolean esCorrecta) {
        Opcion opcion = (Opcion) buscarElemento(opcionDao, id);
        if (opcion != null) {
            opcion.setPregunta(pregunta);
            opcion.setTexto(texto);
            opcion.setEs_correcta(esCorrecta);
            opcion.getPregunta().actualizarOpcion(opcion);
            opcionDao.actualizar(opcion);
            return true;
        }
        return false;
    }

    public static Boolean eliminar(Integer id) {
        Opcion opcion = (Opcion) buscarElemento(opcionDao, id);
        if (opcion != null) {
            opcion.getPregunta().eliminarOpcion(opcion);
            opcionDao.eliminar(opcion);
            return true;
        }
        return false;
    }
}