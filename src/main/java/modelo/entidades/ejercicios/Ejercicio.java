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
@Table(name = "TBL_EJERCICIO")//
public class Ejercicio extends Entidad {
    @Column(name = "id_contenido")
    private Integer id_contenido;

    @Column(name = "id_tipo")//
    private Integer id_tipo;

    @Column(name = "descripcion")//
    private String descripcion;

    @Column(name = "creado_por")//
    private Integer creado_por;

    @Column(name = "fecha_creacion")//
    private String fecha_creacion;
}
