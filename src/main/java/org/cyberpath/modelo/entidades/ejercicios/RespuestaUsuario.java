package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_RESPUESTA_USUARIO")
public class RespuestaUsuario extends Entidad {

    @ManyToOne
    @JoinColumn(name = "id_intento", nullable = false)
    private IntentoEjercicio intento; //

    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta; //

    @Column(name = "respuesta_texto")
    private String respuestaTexto; //

    @Column(name = "correcta", nullable = false)
    private Boolean correcta; //

}
