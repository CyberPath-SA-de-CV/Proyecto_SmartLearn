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
@Table(name = "TBL_TIPO_EJERCICIO")//
public class TipoEjercicio extends Entidad {

    @Column(name = "nombre")//
    private String nombre;
}
