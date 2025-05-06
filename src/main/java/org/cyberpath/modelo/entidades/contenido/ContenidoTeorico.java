package org.cyberpath.modelo.entidades.contenido;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;

import javax.swing.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "tbl_contenido_teorico")
public class ContenidoTeorico extends Entidad {

    public static final DaoImpl<ContenidoTeorico> contenidoTeoricoDao = new DaoImpl<>(ContenidoTeorico.class);

    @Column(name = "texto", nullable = false)
    private String texto;

    @OneToOne(mappedBy = "contenidoTeorico", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Subtema subtema;

    @Column(name = "fecha_creacion")
    private String fecha_creacion;

    @Column(name = "ultima_edicion")
    private String ultima_edicion;

    public void agregarSubtema(Subtema subtema){
        this.subtema = subtema;
        subtema.setContenidoTeorico(this);
    }

    // Métodos de negocio
    public static Boolean agregar(String texto) {
        String fechaSql = LocalDate.now().toString();
        try {
            ContenidoTeorico contenidoTeorico = new ContenidoTeorico();
            contenidoTeorico.setFecha_creacion(fechaSql);
            contenidoTeorico.setTexto(texto);
            contenidoTeoricoDao.guardar(contenidoTeorico);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar contenido teórico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    ///
    public static Boolean actualizar(Integer id, String texto) {
        String fechaSql = LocalDate.now().toString();
        ContenidoTeorico contenidoTeorico;
        contenidoTeorico = (ContenidoTeorico) buscarElemento(contenidoTeoricoDao, id);
        contenidoTeorico.setTexto(texto);
        contenidoTeorico.setUltima_edicion(fechaSql);
        contenidoTeoricoDao.actualizar(contenidoTeorico);
        return true;
    }
    public static Boolean eliminar(Integer id) {
        ContenidoTeorico contenidoTeorico;
        contenidoTeorico = (ContenidoTeorico) buscarElemento(contenidoTeoricoDao, id);
        contenidoTeoricoDao.eliminar(contenidoTeorico);
        return true;
    }

    ///
}
