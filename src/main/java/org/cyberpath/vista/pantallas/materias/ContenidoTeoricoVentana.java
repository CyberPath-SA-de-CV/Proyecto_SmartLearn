package org.cyberpath.vista.pantallas.materias;

import org.cyberpath.controlador.materias.ContenidoTeoricoControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearPanelTituloConLogo;

public class ContenidoTeoricoVentana extends PanelDegradado {
    public ContenidoTeoricoVentana(Subtema subtema, MenuPrincipalVentana menu) {
        setLayout(new BorderLayout());  // <--- CAMBIO CLAVE
        setOpaque(false);

        // Panel que pinta fondo blanco translúcido
        JPanel panelContenedor = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255, 255, 255, 210));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        panelContenedor.setOpaque(false);
        panelContenedor.setLayout(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        add(panelContenedor, BorderLayout.CENTER);  // <--- Expande todo el espacio disponible

        // Panel superior (título)
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Teoría. Subtema | " + subtema.getNombre());
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTituloConLogo.setLayout(new BorderLayout()); // Centrado forzado

        // Centrar y colorear el título si es un JLabel
        for (Component comp : panelTituloConLogo.getComponents()) {
            if (comp instanceof JLabel label) {
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(new Color(0, 51, 153)); // Azul fuerte
                label.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Tamaño y negrita
            }
        }

        panelContenedor.add(panelTituloConLogo, BorderLayout.NORTH);

        // Panel central con scroll solo para el texto
        String textoConImagenes = convertirTextoAHtml(subtema.getContenidoTeorico().getTexto());
        JTextPane textoPane = new JTextPane() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;  // evita barra horizontal
            }
        };
        textoPane.setContentType("text/html");
        textoPane.setText(textoConImagenes);
        textoPane.setEditable(false);
        textoPane.setOpaque(false);
        textoPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textoPane.setCaretPosition(0);
        textoPane.revalidate();
        textoPane.repaint();


        // ScrollPane SOLO para texto
        JScrollPane scrollTexto = new JScrollPane(textoPane);
        scrollTexto.setOpaque(false);
        scrollTexto.getViewport().setOpaque(false);
        scrollTexto.setBorder(null);
        scrollTexto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollTexto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Solución: permitir scroll con la rueda del mouse aunque no esté directamente sobre el scroll
        scrollTexto.addMouseWheelListener(e -> scrollTexto.getParent().dispatchEvent(e));

        panelContenedor.add(scrollTexto, BorderLayout.CENTER);

        // Panel del botón al sur
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.add(Box.createVerticalStrut(10));

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegresar.setBackground(new Color(220, 53, 69));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(200, 40));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> menu.regresar());

        panelBoton.add(btnRegresar);
        panelBoton.add(Box.createVerticalStrut(10));

        panelContenedor.add(panelBoton, BorderLayout.SOUTH);  // <--- Botón fuera del scroll

        // Accesibilidad
        new Thread(() -> {
            try {
                ContenidoTeoricoControlador.procesarAccesibilidad(subtema, menu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private String convertirTextoAHtml(String textoPlano) {
        StringBuilder html = new StringBuilder("<html><body style='font-family: Serif; font-size: 16px;'>");

        for (String linea : textoPlano.split("\n")) {
            linea = linea.trim();
            if (esRutaImagen(linea)) {
                if (linea.startsWith("http")) {
                    html.append("<img src='").append(linea).append("' width='200'><br>");
                } else {
                    File file = new File(linea);
                    html.append("<img src='file:/").append(file.getAbsolutePath().replace("\\", "/")).append("' width='200'><br>");
                }
            } else {
                html.append(linea).append("<br>");
            }
        }

        html.append("</body></html>");
        return html.toString();
    }

    private boolean esRutaImagen(String texto) {
        return (texto.matches("(?i).*(https?://.*\\.(png|jpg|jpeg|gif))") ||
                texto.matches("(?i).*(\\.(png|jpg|jpeg|gif))")) &&
                (texto.startsWith("http") || new File(texto).exists());
    }
}
