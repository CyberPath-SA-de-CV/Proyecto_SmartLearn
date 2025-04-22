package modelo.entidades.ejercicios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import modelo.entidades.base.Entidad;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_RESPUESTA_USUARIO")//
public class RespuestaUsuario extends Entidad {

    @Column(name = "id_intento")//
    private Integer id_intento;

    @Column(name = "id_pregunta")//
    private Integer id_pregunta;

    @Column(name = "respuesta_texto")//
    private String respuesta_texto;

    @Column(name = "correcta")//
    private Short correcta;
}
