package org.cyberpath.util;

public class Salidas {
    //Menus
    public static final String menuPENDIENTE_PRINCIPAL = (
        "Bienvenido al programa!\n" +
        "SELECCIONA UNA OPCION\n" +
        "1.- \n" +
        "2.- \n" +
        "3.- "
    );
    public static final String menuPENDIENTE_COPIAR_PEGAR = (
        "---- Menú PENDIENTE ----\n" +
        "¿Qué operación desea realizar?:\n" +
        "1. \n" +
        "2. \n" +
        "3. \n" +
        "4. \n" +
        "5. \n" +
        "6. \n" +
        "7. \n" +
        "8. \n" +
        "9. "
    );

    //Lecturas
    public static final String leerPENDIENTE = "";

    //Peticion de entrada
    public static final String seleccionarOpcion = "Selecciona una opción: ";
    //Registro Usuario
    public static final String registroExitoso = "Usuario registrado exitosamente.";
    public static final String registroError = "Error al registrar el usuario: ";
    public static final String contrasenaRol = "Digite la contrasena para Administradores";
    public static final String contrasenaRolError = "Contrasena incorrecta";
    public static String elementoNoEncontrado = "No se encontró el elemento";
    //Inicio Sesión
    public static final String errorInicioSesion = "Usuario o Contraseña Incorrecta";
    public static void errorInicioSesion() {
        System.out.println("Usuario o Contraseña Incorrecta");
    }
    //Errores con Operaciones
    public static final String errorActualizarUsuario = "Problema al actualizar el usuario";
    //Inscripcion de materias
    public static final String inscribirMateria = "Está seguro que quiere inscribir la materia: ";


    //CUESTIONARIOS

    //Mensajes generales
    public static void terminarPrograma() {
        System.out.println("¡Hasta luego!");
        //System.out.println("No olvides cerrar la ventana si aún sigue abierta");
    }

    //Errores Generales
    public static void opcionInvalida() {
        System.out.println("La opción seleccionada es inválida");
    }

    public static void errorDato() {
        System.out.println("No es un dato valido");
    }
}