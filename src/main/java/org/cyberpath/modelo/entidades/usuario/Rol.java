package org.cyberpath.modelo.entidades.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.vista.pantallas.cuenta.RegistroVentana;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_ROL")
public class Rol extends Entidad {
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

/*    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;*/

    private static final String contrasena = "123";

    public static Boolean registroRolVerificacion(Integer id_rol) {
        if ( Objects.equals(RolEnum.ADMINISTRADOR, RolEnum.getId(id_rol)) ){
            return RegistroVentana.pedirContrasenaRol();
        } else {
            return true;
        }
    }
}
