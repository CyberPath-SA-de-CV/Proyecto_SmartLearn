package org.cyberpath.vista.componentesR;

import org.hibernate.sql.ast.SqlTreeCreationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


///***  Función crearBoton
/// - Crea un botón con color, tamaño y tipo de letra predeterminados
/// - Tiene como parámetros el texto del botón, el ActionListener y las coordenadas de la posición del botón ***
public abstract class ComponentesReutilizables extends JFrame {
    public static JButton crearBoton(String texto, ActionListener accion, Integer x, Integer y){
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(19,36,81));
        boton.setForeground(Color.WHITE);
        boton.addActionListener(accion);
        boton.setBounds(x,y,150,40);
        return boton;
    }

    /// *** Función crearEtiqueta (1)
    /// Crea una etiqueta recibiendo el texto y la posición en coordenadas de esta ***
    public static JLabel crearEtiqueta(String texto, Integer x, Integer y){
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 12));
        etiqueta.setLocation(x,y);
        return etiqueta;
    }

    ///  **** Crear etiqueta(2)
    /// Crea una etiqueta tomando como parámetro tan solo el texto de esta
    public static JLabel crearEtiqueta(String texto){
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 12));
        return etiqueta;
    }

    /// *** Función crearCampoTxt
    /// Crea un campo de texto recibiendo como parámetro el tamaño de este
    public static JTextField crearCampoTxt(Integer tamano){
        JTextField campoTxt = new JTextField(tamano);
        campoTxt.setFont(new Font("Arial", Font.PLAIN, 12));
        return campoTxt;
    }

    /// *** Función crearCampoTxt(2)
    /// Crea un campo de texto del tamaño especificado como primer paramétro, en las coordenadas dadas como parámetros
    public static JTextField crearCampoTxt(Integer tamano, Integer x, Integer y){
        JTextField campoTxt = new JTextField(tamano);
        campoTxt.setLocation(x,y);
        campoTxt.setFont(new Font("Arial", Font.PLAIN, 12));
        return campoTxt;
    }

    /// *** Función crearPanel
    /// Crea un panel con el número de filas y columnas especificadas en los parámetros
    public static JPanel crearPanel(Integer filas, Integer columnas){
        return new JPanel(new GridLayout(filas,columnas));
    }
}
