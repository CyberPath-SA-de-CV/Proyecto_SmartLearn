package modelo.entidades.contenido;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import modelo.entidades.base.Entidad;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_CONTENIDO")
@Inheritance(strategy = InheritanceType.JOINED)
public class Contenido extends Entidad {
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "autor")
    private String autor;

    @Column(name="fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "ultima_edicion")
    private LocalDate ultimaEdicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subtema", nullable = false)
    private Integer idSubtema;
}