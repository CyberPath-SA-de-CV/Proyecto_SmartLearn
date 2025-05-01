package org.cyberpath.modelo.entidades.usuario;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cyberpath.modelo.baseDeDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_USUARIO")
public class Usuario extends Entidad {
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "discapacidad")
    private String discapacidad;

    @Column(name = "modo_audio")
    private Boolean modoAudio;


    public static final DaoImpl<Usuario> usuarioDao = new DaoImpl<>(Usuario.class);

    public static boolean validarCredenciales(String nombre, String contrasena){
        List<Usuario> list = usuarioDao.findAll();
        for (Usuario usuario : list){
            if(Objects.equals(usuario.getNombre(), nombre) && Objects.equals(usuario.getContrasena(), contrasena)){
                VariablesGlobales.usuario = usuario;
                return true;
            }
        }
        Salidas.errorInicioSesion();
        return false;
    }

    public static String realizarVista(){
        List<Usuario> lista = usuarioDao.findAll();
        System.out.println("tamaño: " + lista.size());
        String cadena = "";
        for (Usuario usuario : lista) {
            cadena += usuario;
            cadena += "\n";
        }
        return cadena;
    }

    public static Boolean agregarUsuario(String nombre, String contrasena, String correo, int idRol) {
        try /*(Session session = HibernateUtil.getSessionFactory().openSession()) */ {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setContrasena(contrasena);
            usuario.setCorreo(correo);
            usuario.rol.setId(idRol); // Asignar relación

            usuarioDao.guardar(usuario);

            VariablesGlobales.usuario = usuario;
            System.out.println("Usuario registrado exitosamente.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Boolean actualizarUsuario(Usuario usuario){
        usuarioDao.actualizar(usuario);
        VariablesGlobales.usuario = usuario;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(realizarVista());
    }
}
