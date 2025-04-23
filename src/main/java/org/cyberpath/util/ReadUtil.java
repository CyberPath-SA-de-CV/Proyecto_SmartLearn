package org.cyberpath.util;
import java.util.Scanner;

public class ReadUtil {
    private Scanner scanner;
    private static ReadUtil readUtil;

    private ReadUtil() {
        scanner = new Scanner( System.in );
    }
    public Scanner getScanner() {
        return scanner;
    }
    public static ReadUtil getInstance( ) {
        if(readUtil==null) {
            readUtil = new ReadUtil();
        }
        return readUtil;
    }

    public static String read(String mensaje) {
        System.out.print(mensaje);
        return getInstance( ).getScanner( ).nextLine();
    }
    public static Integer readInt(String mensaje, Integer min, Integer max) {
        String valor = null;
        Integer aux = null;

        while (true) {
            valor = read(mensaje);
            if (valor != null && !valor.isEmpty()) {
                try {
                    aux = Integer.valueOf(valor);
                    if ( (aux != null) && ((aux >= min) && (aux <= max)) ) {
                        return aux;
                    }
                } catch (Exception e) {
                }
            }
            Salidas.errorDato();
        }
    }
    public static Integer readInt(String mensaje) {
        String valor = null;
        Integer aux = null;

        while (true) {
            valor = read(mensaje);
            if (valor != null && !valor.isEmpty()) {
                try {
                    aux = Integer.valueOf(valor);
                    if ( (aux != null) ) {
                        return aux;
                    }
                } catch (Exception e) {
                }
            }
            Salidas.errorDato();
        }
    }
    public static Double readDouble(String mensaje) {
        String valor = null;
        Double aux = null;

        while (true) {
            valor = read(mensaje);
            if (valor != null && !valor.isEmpty()) {
                try {
                    aux = Double.valueOf(valor);
                    if ( aux != null ) {
                        return aux;
                    }
                } catch (Exception e) {
                }
            }
            Salidas.errorDato();
        }
    }

    public static Integer stringAEntero( String cadena ) {
        try {
            return Integer.valueOf( cadena );
        }
        catch (Exception e) {
        }
        return null;
    }
}