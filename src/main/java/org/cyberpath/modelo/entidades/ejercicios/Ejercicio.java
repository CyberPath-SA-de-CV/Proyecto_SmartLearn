package org.cyberpath.modelo.entidades.ejercicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.contenido.Contenido;
import org.cyberpath.modelo.entidades.usuario.Usuario;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_EJERCICIO")
public class Ejercicio extends Entidad {

    @ManyToOne
    @JoinColumn(name = "id_contenido")
    private Contenido contenido;

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoEjercicio tipo;

    @ManyToOne
    @JoinColumn(name = "creado_por")
    private Usuario creador;

    @Column(name = "fecha_creacion")
    private String fecha_creacion;
}