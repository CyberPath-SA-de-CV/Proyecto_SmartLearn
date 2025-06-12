package org.cyberpath.vista.util.componentes;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class ComponentesReutilizables extends JFrame {

    /// *** Función crearPanel
    /// Crea un panel con el número de filas y columnas especificadas en los parámetros
    public static JPanel crearPanel() {
        return new JPanel(new GridBagLayout());
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
    public static JPanel crearPanelDegradadoDecorativo(String titulo, String rutaImagen) {
        return new JPanel(new BorderLayout()) {
            {
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JPanel panelTitulo = new JPanel();
                panelTitulo.setOpaque(false);
                panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));

                // Imagen circular (si se proporciona la ruta)
                if (rutaImagen != null && !rutaImagen.isBlank()) {
                    JLabel imagenLabel = new JLabel(crearIconoCircular(rutaImagen, 150));
                    panelTitulo.add(imagenLabel);
                }

                // Título estilizado
                JLabel lblTitulo = new JLabel(titulo);
                lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 35));
                lblTitulo.setForeground(Color.WHITE);
                panelTitulo.add(lblTitulo);

                add(panelTitulo, BorderLayout.NORTH);
            }

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

    public static JPanel crearPanelTituloConLogo(String titulo){
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));

        ImageIcon iconoLogo = new ImageIcon("recursosGraficos/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel labelTexto = crearTituloCentrado(titulo);
        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);

        return panelTituloConLogo;
    }
    public static JPanel crearPanelTituloConLogo(String titulo, String rutaImagenCircular) {
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));

        // Imagen circular proporcionada
        if (rutaImagenCircular != null && !rutaImagenCircular.isBlank()) {
            ImageIcon iconoCircular = crearIconoCircular(rutaImagenCircular, 150);
            if (iconoCircular != null) {
                JLabel labelCircular = new JLabel(iconoCircular);
                labelCircular.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
                panelTituloConLogo.add(labelCircular);
            }
        }

        // Logo SmartLearn
        ImageIcon iconoLogo = new ImageIcon("recursosGraficos/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        panelTituloConLogo.add(labelLogo);

        // Título centrado
        JLabel labelTexto = crearTituloCentrado(titulo);
        labelTexto.setFont(new Font("Segoe UI", Font.BOLD, 35));
        panelTituloConLogo.add(labelTexto);

        return panelTituloConLogo;
    }


    /// Panel transparente con padding
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

        JLabel usuario = new JLabel("Usuario " + textoUsuario);
        usuario.setFont(new Font("Segoe UI", Font.BOLD, 17));
        usuario.setForeground(Color.WHITE);
        usuario.setBorder(new EmptyBorder(2, 5, 3, 0));
        barra.add(usuario, BorderLayout.EAST);

        return barra;
    }


    public static JButton crearBoton(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(19, 36, 81));
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


    // Metodo estático que crea un Botón con estilo suave y esquinas redondeadas
    public static JButton crearBotonEstilizado(String texto, String iconoRuta, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));  // Aumentamos el tamaño de la fuente
        boton.setBackground(new Color(255, 255, 255, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30)); // Más espacio en los bordes
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajustar el tamaño preferido para los botones
        boton.setPreferredSize(new Dimension(10, 40));
        boton.setMaximumSize(new Dimension(750, 40));

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

    public static JButton crearBotonSalirAPantallaPrincipal(){
        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonSalir.setBackground(new Color(220, 53, 69));
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setFocusPainted(false);
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setMaximumSize(new Dimension(200, 40));
        botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botonSalir.addActionListener(e -> {
            try {
                PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return botonSalir;
    }

    /// *** Función crearEtiqueta (1)
    /// Crea una etiqueta recibiendo el texto y la posición en coordenadas de esta ***
    public static JLabel crearEtiqueta(String texto, Integer x, Integer y) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 12));
        etiqueta.setLocation(x, y);
        return etiqueta;
    }

    ///  **** Crear etiqueta(2)
    /// Crea una etiqueta centrada tomando como parámetro tan solo el texto de esta
    public static JLabel    crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 12));
        return etiqueta;
    }

    /// Etiqueta Fecha
    public static JLabel crearFecha() {
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy"));
        JLabel etiquetaFecha = new JLabel(fechaHoy);
        etiquetaFecha.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        etiquetaFecha.setForeground(Color.WHITE);
        etiquetaFecha.setBorder(new EmptyBorder(2, 0, 3, 5));
        return etiquetaFecha;
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

    /// *** Función crearCampoTxt
    /// Crea un campo de texto recibiendo como parámetro el tamaño de este
    public static JTextField crearCampoTxt(Integer tamano) {
        JTextField campoTxt = new JTextField(tamano);
        campoTxt.setFont(new Font("Arial", Font.PLAIN, 12));
        return campoTxt;
    }

    public static GridBagConstraints crearConstraint(int fila, int columna, int ancho, int alto, int margen) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.insets = new Insets(10, margen, 10, margen);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    public static GridBagConstraints crearConstraintCentrado(int fila, int columna, int ancho, int alto, int margen) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.insets = new Insets(10, margen, 10, margen);
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

    public static JScrollPane crearScrollPaneTransparente(JComponent componente) {
        JScrollPane scroll = new JScrollPane(componente,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        scroll.getHorizontalScrollBar().setUnitIncrement(20);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        return scroll;
    }

    public static GridBagConstraints crearConstraintBotonAncho(int fila, int columna, int anchura, int altura, int margen) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = columna;
        c.gridy = fila;
        c.gridwidth = anchura;
        c.gridheight = altura;
        c.insets = new Insets(10, margen, 10, margen);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        return c;
    }

    public static ImageIcon crearIconoCircular(String rutaImagen, int diametro) {
        try {
            BufferedImage original = ImageIO.read(new File(rutaImagen));
            BufferedImage circular = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circular.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, diametro, diametro);
            g2.setClip(clip);
            g2.drawImage(original, 0, 0, diametro, diametro, null);
            g2.dispose();

            return new ImageIcon(circular);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
