package org.cyberpath.vista.componentesR;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


///***  Función crearBoton
/// - Crea un botón con color, tamaño y tipo de letra predeterminados
/// - Tiene como parámetros el texto del botón, el ActionListener y las coordenadas de la posición del botón ***
public abstract class ComponentesReutilizables extends JFrame {
    public static JButton crearBotonByCoords(String texto, ActionListener accion, Integer x, Integer y){
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(19,36,81));
        boton.setForeground(Color.WHITE);
        boton.addActionListener(accion);
        boton.setBounds(x,y,150,40);
        return boton;
    }

    public static JButton crearBoton(String texto, ActionListener accion){
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(19,36,81));
        boton.setForeground(Color.WHITE);
        boton.addActionListener(accion);
        return boton;
    }

    public static JButton crearBoton(String texto, ActionListener accion, int tamFuente, int altoPadding) {
        JButton boton = new JButton(texto);
        boton.addActionListener(accion);
        boton.setFont(new Font("Arial", Font.PLAIN, tamFuente));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(70, 130, 180)); // Azul suave
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(altoPadding, 20, altoPadding, 20));
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
    /// Crea una etiqueta centrada tomando como parámetro tan solo el texto de esta
    public static JLabel crearEtiqueta(String texto){
        JLabel etiqueta = new JLabel(texto,SwingConstants.CENTER);
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
    public static JPanel crearPanel(){
        return new JPanel(new GridBagLayout());
    }



    public static GridBagConstraints crearConstraint(int fila, int columna, int ancho, int alto) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    public static GridBagConstraints crearConstraintCentrado(int fila, int columna, int ancho, int alto) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    /// *** Función crearBotonConIcono
    /// Crea un botón estético con ícono, color personalizado y fuente bonita
    public static JButton crearBotonConIcono(String texto, String rutaIcono, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setBackground(Color.WHITE);
        boton.setForeground(new Color(30, 42, 82));
        boton.setFocusPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(220, 50));
        boton.setHorizontalAlignment(SwingConstants.LEFT);

        try {
            java.net.URL urlIcono = ComponentesReutilizables.class.getResource("/" + rutaIcono);
            if (urlIcono != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlIcono);
                Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                boton.setIcon(new ImageIcon(imagenRedimensionada));
                boton.setIconTextGap(15);
            } else {
                System.err.println("No se encontró el ícono: " + rutaIcono);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el ícono: " + rutaIcono);
        }

        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 42, 82), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        boton.addActionListener(accion);
        return boton;
    }

    /// Etiqueta Fecha
    public static JLabel crearFecha(){
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy"));
        JLabel etiquetaFecha = new JLabel(fechaHoy);
        etiquetaFecha.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        etiquetaFecha.setForeground(Color.WHITE);
        etiquetaFecha.setBorder(new EmptyBorder(2, 0, 3, 5));
        return etiquetaFecha;
    }

    /// Panel con fondo decorativo degradado y líneas
    public static JPanel crearPanelDegradadoDecorativo() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                Color color1 = new Color(30, 42, 82);
                Color color2 = new Color(60, 80, 120);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(255, 255, 255, 25));
                for (int x = 0; x < getWidth(); x += 60) {
                    g2d.drawLine(x, 0, x, getHeight());
                }
            }
        };
    }

    // Panel transparente con padding
    public static JPanel crearPanelTransparenteConPadding(int top, int left, int bottom, int right) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(top, left, bottom, right));
        return panel;
    }

    // Barra superior con fecha y etiqueta de usuario
    public static JPanel crearBarraSuperior(String textoUsuario) {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);
        barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel fecha = crearFecha();
        barra.add(fecha, BorderLayout.WEST);

        JLabel usuario = new JLabel(textoUsuario);
        usuario.setFont(new Font("Segoe UI", Font.BOLD, 17));
        usuario.setForeground(Color.WHITE);
        usuario.setBorder(new EmptyBorder(2, 5, 3, 0));
        barra.add(usuario, BorderLayout.EAST);

        return barra;
    }

    // Título grande centrado
    public static JLabel crearTituloCentrado(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 35, 30)); // Más espacio en los bordes

        return titulo;
    }

    // Botón con estilo suave y esquinas redondeadas
    public static JButton crearBotonEstilizado(String texto, String iconoRuta, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));  // Aumentamos el tamaño de la fuente
        boton.setBackground(new Color(255, 255, 255, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Más espacio en los bordes
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajustar el tamaño preferido para los botones
        boton.setPreferredSize(new Dimension(100, 70));  // Aumentamos el tamaño del botón

        // Ajustamos el tamaño del texto para que se ajuste dentro del botón
        boton.setHorizontalAlignment(SwingConstants.CENTER);  // Asegura que el texto esté centrado
        boton.setVerticalAlignment(SwingConstants.CENTER);    // Asegura que el texto esté centrado verticalmente
        boton.setText("<html><center>" + texto + "</center></html>"); // Aseguramos que el texto se ajuste si es necesario

        // 👉 Cargar y escalar el icono si se proporciona una ruta válida
        if (iconoRuta != null && !iconoRuta.isEmpty()) {
            ImageIcon iconoOriginal = new ImageIcon(iconoRuta);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
            boton.setHorizontalTextPosition(SwingConstants.LEFT); // Texto a la derecha del icono
            boton.setIconTextGap(15); // Espacio entre icono y texto
        }

        // Personalización visual
        boton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });

        // Hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(255, 255, 255, 120));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(255, 255, 255, 60));
            }
        });

        if (accion != null) {
            boton.addActionListener(accion);
        }

        return boton;
    }

    public static JButton crearBoton(String texto, ActionListener accion){
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(19,36,81));
        boton.setForeground(Color.WHITE);
        boton.addActionListener(accion);
        return boton;
    }


    /// *** Función crearPanel
    /// Crea un panel con el número de filas y columnas especificadas en los parámetros
    public static JPanel crearPanel(){
        return new JPanel(new GridBagLayout());
    }



    public static GridBagConstraints crearConstraint(int fila, int columna, int ancho, int alto) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    public static GridBagConstraints crearConstraintCentrado(int fila, int columna, int ancho, int alto) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    public static JCheckBox crearCheckBox(String texto, boolean seleccionadoPorDefecto) {
        JCheckBox checkBox = new JCheckBox(texto, seleccionadoPorDefecto) {
            @Override
            protected void paintComponent(Graphics g) {
                if (isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 60)); // fondo semitransparente
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };

        checkBox.setOpaque(false);
        checkBox.setForeground(Color.WHITE); // Texto blanco
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setFocusPainted(false);
        checkBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return checkBox;
    }

    public static JCheckBox crearCheckboxEstilizado(String texto, ItemListener listener) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
        checkBox.setForeground(Color.WHITE);
        checkBox.setOpaque(false);
        checkBox.setFocusPainted(false);
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        checkBox.addItemListener(listener);

        // Personalización adicional opcional: cambiar ícono si se desea
        // UIManager.put("CheckBox.icon", new CustomIcon());

        return checkBox;
    }



}
