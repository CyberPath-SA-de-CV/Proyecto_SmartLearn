package org.cyberpath.modelo.entidades.base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Entidad {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;


    /*
    public abstract void alta();
    public abstract void modificacion();
    public abstract void baja();
    public abstract void vista();

     */



}
