package org.cyberpath.controlador;

import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.vista.pantallas.MenuPrincipalVentana;

import javax.swing.*;

public class RegistroControlador {
    public void procesarRegistro(String nombre, String contrasena, String correo, Integer idRol,JFrame ventanaActual){
        if(Usuario.agregarUsuario(nombre,contrasena,correo,idRol)){
            ventanaActual.dispose();
            new MenuPrincipalVentana().setVisible(true);
        }  else {
<<<<<<< HEAD
        JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
        }
    }
}
=======
            JOptionPane.showMessageDialog(ventanaActual, Salidas.errorInicioSesion);
        }
    }
}
>>>>>>> 3c12c289a94bd8099422906a985e982ec15a8c0c
