package org.cyberpath.util;

public class Salidas {

    //LOGS

    public static final String registroError = "Error al registrar el usuario: ";
    public static final String contrasenaRol = "Digite la contrasena para Administradores";
    public static final String contrasenaRolError = "Contrasena incorrecta";

    public static final String errorInicioSesion = "Usuario o Contraseña Incorrecta";
    public static void errorInicioSesion() {
        System.out.println("Usuario o Contraseña Incorrecta");
    }
    public static final String errorActualizarUsuario = "Problema al actualizar el usuario";

    public static void errorDato() {
        System.out.println("No es un dato valido");
    }


    //AUDIO

    public static String menu =
            "Acontinuación se presenta el menú de ventanas, si desea cambiar de ventana seleccione en voz alta una de las opciones, si no, diga la frase, no cambiar, " +
            "uno, Menú principal, " +
            "dos, Configuración Cuenta, " +
            "tres, Configuración Accesibilidad" +
            "cuatro, cerrar sesión"
    ;
}