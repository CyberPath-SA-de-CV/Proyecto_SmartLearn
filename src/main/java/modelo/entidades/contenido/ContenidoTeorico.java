package modelo.entidades.contenido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_CONTENIDO_TEORICO")//
public class ContenidoTeorico extends Contenido{
    @Column(name = "texto")
    private String texto;

    @Column(name = "tiene_audio")
    private boolean audio;
}
