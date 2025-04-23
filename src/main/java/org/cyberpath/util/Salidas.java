package org.cyberpath.util;

public class Salidas {
    //Menus
    public static String menuPENDIENTE_PRINCIPAL = (
        "Bienvenido al programa!\n" +
        "SELECCIONA UNA OPCION\n" +
        "1.- \n" +
        "2.- \n" +
        "3.- "
    );
    public static String menuPENDIENTE_COPIAR_PEGAR = (
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
    public static String leerPENDIENTE = "";

    //Peticion de entrada
    public static String seleccionarOpcion = "Selecciona una opción: ";

    //Inicio Sesión
    public static void errorInicioSesion() {
        System.out.println("Usuario o Contraseña Incorrecta");
    }
    public static String errorInicioSesion = "Usuario o Contraseña Incorrecta";

    //Mensajes generales
    public static void terminarPrograma(){
        System.out.println("¡Hasta luego!");
        //System.out.println("No olvides cerrar la ventana si aún sigue abierta");
    }

    //CUESTIONARIOS

    //Errores Generales
    public static void opcionInvalida() {
        System.out.println("La opción seleccionada es inválida");
    }
    public static String elementoNoEncontrado = "No se encontró el elemento";
    public static void errorDato() {
        System.out.println("No es un dato valido");
    }


}