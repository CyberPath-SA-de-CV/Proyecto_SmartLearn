package org.cyberpath.util;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

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

    public static void imprimirImagen() throws IOException {
        BufferedImage imagen = ImageIO.read(new File("src/main/resources/recursosGraficos/iconos/imgIconImp/img_1.jpg"));
        String caracteres = "@%#*+=-:. "; // del más oscuro al más claro

        for (int y = 0; y < imagen.getHeight(); y += 4) { // Salto vertical más grande para compensar proporción
            for (int x = 0; x < imagen.getWidth(); x += 2) {
                Color pixel = new Color(imagen.getRGB(x, y));
                int gris = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                int indice = gris * (caracteres.length() - 1) / 255;
                System.out.print(caracteres.charAt(indice));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        imprimirImagen();
    }
}