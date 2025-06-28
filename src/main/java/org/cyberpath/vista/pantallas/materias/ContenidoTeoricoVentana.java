package org.cyberpath.vista.pantallas.materias;

import org.cyberpath.controlador.materias.ContenidoTeoricoControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ContenidoTeoricoVentana extends PanelDegradado {

    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Color BUTTON_COLOR = new Color(220, 53, 69);
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 40;

    public ContenidoTeoricoVentana(Subtema subtema, MenuPrincipalVentana menu) {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS)); // Usar BoxLayout
        add(panelContenedor, BorderLayout.CENTER);

        JPanel panelTituloConLogo = createTitlePanel("Teoría. Subtema | " + subtema.getNombre());
        panelContenedor.add(panelTituloConLogo);

        JTextPane textoPane = createTextPane(subtema);
        JScrollPane scrollTexto = createScrollPane(textoPane);
        panelContenedor.add(scrollTexto);

        JPanel panelBoton = createButtonPanel(menu);
        panelContenedor.add(panelBoton);

        startAccessibilityThread(subtema, menu);
    }

    private JPanel createTitlePanel(String titulo) {
        JPanel panelTituloConLogo = new JPanel();
        panelTituloConLogo.setOpaque(false);
        panelTituloConLogo.setLayout(new BoxLayout(panelTituloConLogo, BoxLayout.X_AXIS));
        panelTituloConLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon iconoLogo = new ImageIcon("recursosGraficos/logos/logo_smartlearn.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel labelLogo = new JLabel(new ImageIcon(imagenEscalada));
        labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel labelTexto = crearTituloCentrado(titulo);
        labelTexto.setForeground(new Color(0, 51, 153));
        panelTituloConLogo.add(labelLogo);
        panelTituloConLogo.add(labelTexto);

        return panelTituloConLogo;
    }

    private JTextPane createTextPane(Subtema subtema) {
        String textoConImagenes = convertirTextoAHtml(subtema.getContenidoTeorico().getTexto());
        JTextPane textoPane = new JTextPane();
        textoPane.setContentType("text/html");
        textoPane.setText(textoConImagenes);
        textoPane.setEditable(false);
        textoPane.setOpaque(false);
        textoPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textoPane;
    }

    private JScrollPane createScrollPane(JTextPane textoPane) {
        JScrollPane scrollTexto = new JScrollPane(textoPane);
        scrollTexto.setOpaque(false);
        scrollTexto.getViewport().setOpaque(false);
        scrollTexto.setBorder(null);
        scrollTexto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollTexto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTexto.setPreferredSize(new Dimension(800, 400)); // Establece un tamaño preferido para el JScrollPane
        return scrollTexto;
    }

    private JPanel createButtonPanel(MenuPrincipalVentana menu) {
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.add(Box.createVerticalStrut(10));

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(BUTTON_FONT);
        btnRegresar.setBackground(BUTTON_COLOR);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> menu.regresar());

        panelBoton.add(btnRegresar);
        panelBoton.add(Box.createVerticalStrut(10));
        return panelBoton;
    }

    private void startAccessibilityThread(Subtema subtema, MenuPrincipalVentana menu) {
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
                appendImageToHtml(html, linea);
            } else {
                html.append(linea).append("<br>");
            }
        }

        html.append("</body></html>");
        return html.toString();
    }

    private void appendImageToHtml(StringBuilder html, String linea) {
        if (linea.startsWith("http")) {
            html.append("<img src='").append(linea).append("' width='200'><br>");
        } else {
            File file = new File(linea);
            html.append("<img src='file:/").append(file.getAbsolutePath().replace("\\", "/")).append("' width='200'><br>");
        }
    }

    private boolean esRutaImagen(String texto) {
        return (texto.matches("(?i).*(https?://.*\\.(png|jpg|jpeg|gif))") ||
                texto.matches("(?i).*(\\.(png|jpg|jpeg|gif))")) &&
                (texto.startsWith("http") || new File(texto).exists());
    }
}
