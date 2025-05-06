package org.cyberpath.modelo.entidades.base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cyberpath.modelo.baseDatos.dao.DaoInterface;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;


@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Entidad {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected static Entidad buscarElemento(DaoImpl<? extends Entidad> DaoImpl, Integer id){
        Entidad entidad = null;
        entidad = DaoImpl.findById(id);

        if(entidad == null){
            System.out.println("Error | Elemento no encontrado");
            return null;
        } else {
            return entidad;
        }
    }
}
