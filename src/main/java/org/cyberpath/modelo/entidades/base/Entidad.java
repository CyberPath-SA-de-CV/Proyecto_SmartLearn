package org.cyberpath.modelo.entidades.base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;


@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Entidad {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

}
