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
@Table(name = "TBL_CONTENIDO_PRACTICO")//
public class ContenidoPractico extends Contenido{
    @Column(name = "instrucciones")
    private String instrucciones;
}
