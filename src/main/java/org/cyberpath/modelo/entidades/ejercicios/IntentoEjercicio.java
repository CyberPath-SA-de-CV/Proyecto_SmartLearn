package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_INTENTO_EJERCICIO")//
public class IntentoEjercicio extends Entidad {
    @Column(name = "id_usuario")
    private Integer id_contenido;

    @Column(name = "id_ejercicio")//
    private Integer id_ejericio;

    @Column(name = "fecha")//
    private String fecha;
}
