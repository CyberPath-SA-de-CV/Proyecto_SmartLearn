package org.cyberpath.modelo.entidades.contenido;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;

import javax.swing.*;

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
    /*
    @Column(name = "tiene_audio", nullable = false)
    private boolean audio;
    */
    @OneToOne(mappedBy = "contenidoTeorico", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Subtema subtema;

    public void agregarSubtema(Subtema subtema){
        this.subtema = subtema;
        subtema.setContenidoTeorico(this);
    }

    // Métodos de negocio
    public static Boolean agregar(String texto/*, boolean tieneAudio*/) {
        try {
            ContenidoTeorico contenidoTeorico = new ContenidoTeorico();
            contenidoTeorico.setTexto(texto);
            //contenido.setAudio(tieneAudio);
            contenidoTeoricoDao.guardar(contenidoTeorico);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar contenido teórico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizar(ContenidoTeorico contenido, String nuevoTexto, boolean nuevoAudio) {
        try {
            contenido.setTexto(nuevoTexto);
            //contenido.setAudio(nuevoAudio);
            return contenidoTeoricoDao.actualizar(contenido);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar contenido teórico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean eliminar(ContenidoTeorico contenido) {
        try {
            return contenidoTeoricoDao.eliminar(contenido);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar contenido teórico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
