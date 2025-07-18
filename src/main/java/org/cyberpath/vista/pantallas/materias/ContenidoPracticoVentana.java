package org.cyberpath.vista.pantallas.materias;


import org.cyberpath.controlador.materias.ContenidoPracticoControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioEjercicio;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.cyberpath.modelo.entidades.ejercicios.Opcion;
import org.cyberpath.modelo.entidades.ejercicios.Pregunta;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.componentes.PanelDegradado;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ContenidoPracticoVentana extends PanelDegradado {
    private static final Color BUTTON_COLOR = new Color(220, 53, 69);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 40;
    private static final int DIALOG_WIDTH = 600;
    private static final int DIALOG_HEIGHT = 300;

    public ContenidoPracticoVentana(Subtema subtema, MenuPrincipalVentana menu) {
        setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelContenido.add(createTitlePanel(subtema));
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(createExercisesPanel(subtema, menu));
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(createBackButton(menu)); // <- Ahora está dentro del scroll
        panelContenido.add(Box.createVerticalStrut(20));

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        startAccessibilityThread(subtema, menu);
    }


    private JPanel createTitlePanel(Subtema subtema) {
        JPanel panelTituloConLogo = crearPanelTituloConLogo("Práctica. Subtema | " + subtema.getNombre(), "src/main/resources/recursosGraficos/titulos/practica.jpg");
        panelTituloConLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panelTituloConLogo;
    }

    private JPanel createExercisesPanel(Subtema subtema, MenuPrincipalVentana menu) {
        JPanel panelEjercicios = new JPanel();
        panelEjercicios.setLayout(new BoxLayout(panelEjercicios, BoxLayout.Y_AXIS));
        panelEjercicios.setOpaque(false);

        List<Ejercicio> ejercicios = subtema.getEjercicios();
        for (Ejercicio ejercicio : ejercicios) {
            JButton btnEjercicio = crearBotonEstilizado(ejercicio.getInstrucciones(), null, e -> ejecutarEjercicio(ejercicio, menu));
            panelEjercicios.add(btnEjercicio);
            panelEjercicios.add(Box.createVerticalStrut(10));
        }

        return panelEjercicios;
    }

    private JButton createBackButton(MenuPrincipalVentana menu) {
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(BUTTON_FONT);
        btnRegresar.setBackground(BUTTON_COLOR);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> menu.regresar());
        return btnRegresar;
    }

    private void startAccessibilityThread(Subtema subtema, MenuPrincipalVentana menu) {
        new Thread(() -> {
            try {
                ContenidoPracticoControlador.procesarAccesibilidad(subtema, menu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void ejecutarEjercicio(Ejercicio ejercicio, MenuPrincipalVentana menu) {
        if (ejercicio.getTipo().getId() == 1) {
            showSingleAnswerDialog(ejercicio, menu);
        } else if (ejercicio.getTipo().getId() == 2) {
            ejecutarCuestionario(ejercicio, menu);
        }
        UsuarioEjercicio.agregar(VariablesGlobales.usuario, ejercicio);
    }

    private void showSingleAnswerDialog(Ejercicio ejercicio, MenuPrincipalVentana menu) {
        JDialog dialog = createDialog("Ejercicio");
        PanelDegradado panelContenido = new PanelDegradado();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblInstruccion = new JLabel("<html><body style='width: 500px'>" + ejercicio.getInstrucciones() + "</body></html>");
        lblInstruccion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtRespuesta = new JTextField();
        txtRespuesta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtRespuesta.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnEnviar = crearBotonEstilizado("Enviar Respuesta", null, e -> {
            String respuesta = txtRespuesta.getText().trim();
            boolean esCorrecta = validarRespuestaEjercicio(ejercicio, respuesta);
            dialog.dispose();
            handleResponseResult(ejercicio, menu, esCorrecta);
        });

        panelContenido.add(lblInstruccion);
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(txtRespuesta);
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(btnEnviar);

        dialog.setContentPane(panelContenido);
        dialog.setVisible(true);
    }

    private void handleResponseResult(Ejercicio ejercicio, MenuPrincipalVentana menu, boolean esCorrecta) {
        if (esCorrecta) {
            mostrarDialogo("¡Respuesta correcta!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String correcta = ejercicio.getPreguntas().get(0).getOpciones().stream()
                    .filter(Opcion::getEs_correcta)
                    .map(Opcion::getTexto)
                    .findFirst()
                    .orElse("Desconocida");
            int opcion = mostrarDialogoConfirmacionModerno(
                    "Respuesta incorrecta.\nRespuesta correcta: " + correcta + "\n\n¿Quieres consultar la teoría relacionada?",
                    "Respuesta Incorrecta"
            );
            if (opcion == JOptionPane.YES_OPTION) {
                menu.mostrarContenidoTeorico(ejercicio.getSubtema());
            }
        }
    }

    private boolean validarRespuestaEjercicio(Ejercicio ejercicio, String respuesta) {
        return Objects.equals(ejercicio.getPreguntas().get(0).getOpciones().get(0).getTexto(), respuesta);
    }

    private void ejecutarCuestionario(Ejercicio ejercicio, MenuPrincipalVentana menu) {
        List<Pregunta> preguntas = ejercicio.getPreguntas();
        AtomicInteger correctas = new AtomicInteger();
        AtomicInteger incorrectas = new AtomicInteger();
        StringBuilder resumenIncorrectas = new StringBuilder();

        for (Pregunta pregunta : preguntas) {
            JDialog dialog = createDialog("Pregunta");
            PanelDegradado panelContenido = new PanelDegradado();
            panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
            panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel lblPregunta = new JLabel("<html><body style='width: 500px'>" + pregunta.getEnunciado() + "</body></html>");
            lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblPregunta.setForeground(Color.WHITE);
            lblPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);

            ButtonGroup group = new ButtonGroup();
            JPanel panelOpciones = new JPanel();
            panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
            panelOpciones.setOpaque(false);
            JRadioButton[] botones = pregunta.getOpciones().stream().map(op -> {
                JRadioButton btn = new JRadioButton(op.getTexto());
                btn.setFocusPainted(false);
                btn.setOpaque(false);
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                group.add(btn);
                return btn;
            }).toArray(JRadioButton[]::new);

            for (JRadioButton btn : botones) {
                panelOpciones.add(btn);
            }

            JButton btnEnviar = crearBotonEstilizado("Enviar", null, e -> {
                String seleccion = Arrays.stream(botones).filter(AbstractButton::isSelected).findFirst().map(AbstractButton::getText).orElse(null);
                dialog.dispose();
                if (seleccion != null) {
                    boolean esCorrecta = pregunta.getOpciones().stream()
                            .anyMatch(o -> o.getTexto().equals(seleccion) && o.getEs_correcta());
                    if (esCorrecta) {
                        correctas.getAndIncrement();
                        mostrarDialogo("¡Respuesta correcta!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        incorrectas.getAndIncrement();
                        resumenIncorrectas.append("Pregunta: ").append(pregunta.getEnunciado())
                                .append("\nRespuesta correcta: ")
                                .append(pregunta.getOpciones().stream().filter(Opcion::getEs_correcta).findFirst().get().getTexto())
                                .append("\n\n");
                    }
                }
            });

            panelContenido.add(lblPregunta);
            panelContenido.add(Box.createVerticalStrut(20));
            panelContenido.add(panelOpciones);
            panelContenido.add(Box.createVerticalStrut(20));
            panelContenido.add(btnEnviar);

            dialog.setContentPane(panelContenido);
            dialog.setVisible(true);
        }

        String resumen = "Resumen:\nCorrectas: " + correctas + "\nIncorrectas: " + incorrectas + "\n\n" + "Preguntas incorrectas: " + "\n" + resumenIncorrectas;
        mostrarDialogo(resumen, "Resultados del Cuestionario", JOptionPane.INFORMATION_MESSAGE);
        int respuestaTeoria = mostrarDialogoConfirmacionModerno(
                "¿Quieres consultar la teoría relacionada?",
                "Retroalimentación"
        );
        if (respuestaTeoria == JOptionPane.YES_OPTION) {
            menu.mostrarContenidoTeorico(ejercicio.getSubtema());
        }
    }

    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), title, true);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        return dialog;
    }

    private void mostrarDialogo(String mensaje, String titulo, int tipo) {
        JDialog dialog = createDialog(titulo);
        PanelDegradado panel = new PanelDegradado();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear JLabel con HTML para el mensaje
        JLabel label = new JLabel("<html><body style='width: 400px'>" + mensaje.replace("\n", "<br>") + "</body></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Envolver el JLabel en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setPreferredSize(new Dimension(420, 200)); // Limita la altura visible
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton btnCerrar = crearBotonEstilizado("Cerrar", null, e -> dialog.dispose());
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnCerrar);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Centrar
        dialog.setVisible(true);
    }


    private int mostrarDialogoConfirmacionModerno(String mensaje, String titulo) {
        final int[] respuesta = {JOptionPane.NO_OPTION};

        JDialog dialog = createDialog(titulo);
        PanelDegradado panel = new PanelDegradado();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("<html><body style='width: 450px'>" + mensaje.replace("\n", "<br>") + "</body></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout());

        JButton btnSi = crearBotonEstilizado("Sí", null, e -> {
            respuesta[0] = JOptionPane.YES_OPTION;
            dialog.dispose();
        });
        btnSi.setPreferredSize(new Dimension(100, 40));
        JButton btnNo = crearBotonEstilizado("No", null, e -> {
            respuesta[0] = JOptionPane.NO_OPTION;
            dialog.dispose();
        });
        btnNo.setPreferredSize(new Dimension(100, 40));

        panelBotones.add(btnSi);
        panelBotones.add(btnNo);
        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(panelBotones);

        dialog.setContentPane(panel);
        dialog.setVisible(true);

        return respuesta[0];
    }
}
