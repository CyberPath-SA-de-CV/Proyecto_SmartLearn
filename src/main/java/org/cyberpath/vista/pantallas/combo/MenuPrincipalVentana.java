package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.controlador.combo.MenuPrincipalControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioEjercicio;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Sistema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.materias.ContenidoPracticoVentana;
import org.cyberpath.vista.pantallas.materias.ContenidoTeoricoVentana;
import org.cyberpath.vista.pantallas.materias.SubtemaVentana;
import org.cyberpath.vista.pantallas.materias.TemaVentana;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class MenuPrincipalVentana extends PlantillaBaseVentana {
    private static final Sistema sistema = Sistema.getInstance();
    private final Stack<PanelHistorial> historial = new Stack<>();
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MenuPrincipalVentana() throws Exception {
        super("Menú Principal", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        iniciarAccesibilidad();
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(19);
        VariablesGlobales.usuario = ejemplo;

        SwingUtilities.invokeLater(() -> {
            try {
                new MenuPrincipalVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void inicializarComponentes() throws Exception {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        getPanelCentral().add(mainPanel, BorderLayout.CENTER);
        mostrarMenuMaterias();
    }

    @Override
    protected void agregarEventos() {
        // Puedes añadir eventos generales aquí si los necesitas
    }

    @Override
    public JPanel getContenido() {
        return mainPanel;
    }

    public JPanel getPanelContenedor() {
        return super.getPanelCentral();
    }

    private void iniciarAccesibilidad() {
        new Thread(() -> {
            try {
                if (PantallasControlador.menuAccesibilidad("Materias", this)) {
                    mostrarTemas(MenuPrincipalControlador.procesarAccesibilidad(this));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void mostrarMenuMaterias() {
        JPanel panelMaterias = crearPanelDegradadoDecorativo("Materias", "src/main/resources/recursosGraficos/titulos/materias.jpg");
        JPanel panelBotones = crearPanelBotones();
        JButton btnInscribirMateria = crearBotonEstilizado("Inscribir Materia", null, e -> {
            try {
                PantallasControlador.mostrarPantalla(PantallasEnum.INSCRIBIR_MATERIA);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnInscribirMateria);
        panelBotones.add(Box.createVerticalStrut(20));

        List<Materia> materiasInscritas = new DaoImpl<Usuario>().obtenerMateriasInscritasPorUsuario(VariablesGlobales.usuario.getId());
        if (!materiasInscritas.isEmpty()) {
            for (Materia materia : materiasInscritas) {
                agregarMateria(panelBotones, materia);

            }
        } else {
            JLabel mensaje = crearTituloCentrado("No hay materias inscritas aún");
            mensaje.setFont(new Font("Segoe UI", Font.ITALIC, 22));
            panelBotones.add(mensaje);
            panelBotones.add(Box.createVerticalStrut(5));
        }

        panelMaterias.add(panelBotones, BorderLayout.CENTER);
        mainPanel.add(panelMaterias, "Materias");
        cardLayout.show(mainPanel, "Materias");
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        return panelBotones;
    }

    private void agregarMateria(JPanel panelBotones, Materia materia) {
        UsuarioMateria usuarioMateria = obtenerInscripcion(materia);
        if (usuarioMateria != null) {
            int totalEjercicios = contarEjercicios(materia);
            int ejerciciosRealizados = contarEjerciciosRealizados(materia);
            double progreso = calcularProgreso(totalEjercicios, ejerciciosRealizados);

            DecimalFormat formato = new DecimalFormat("#.#");
            String redondeado = formato.format(progreso);
            materia.setProgresoGeneral(redondeado);

            JButton btnMateria = crearBotonEstilizado(materia.getNombre(), null, e -> mostrarTemas(materia));
            panelBotones.add(btnMateria);

            BarraProgresoTransparente progressBar = new BarraProgresoTransparente(0, 100);
            progressBar.setValue((int) progreso);
            progressBar.setStringPainted(true);
            progressBar.setMaximumSize(new Dimension(750, 30));
            panelBotones.add(progressBar);
            panelBotones.add(Box.createVerticalStrut(10));
        }
    }

    private UsuarioMateria obtenerInscripcion(Materia materia) {
        for (UsuarioMateria inscripcion : materia.getInscripciones()) {
            if (inscripcion.getUsuario().getId().equals(VariablesGlobales.usuario.getId())) {
                return inscripcion;
            }
        }
        return null;
    }

    private int contarEjercicios(Materia materia) {
        int totalEjercicios = 0;
        for (Ejercicio ejercicio : Ejercicio.ejercicioDao.findAll()) {
            if (Objects.equals(ejercicio.getSubtema().getTema().getMateria().getId(), materia.getId())) {
                totalEjercicios++;
            }
        }
        return totalEjercicios;
    }

    private int contarEjerciciosRealizados(Materia materia) {
        int ejerciciosRealizados = 0;
        for (UsuarioEjercicio usuarioEjercicio : VariablesGlobales.usuario.getEjercicios()) {
            if (Objects.equals(usuarioEjercicio.getEjercicio().getSubtema().getTema().getMateria().getId(), materia.getId())) {
                ejerciciosRealizados++;
            }
        }
        return ejerciciosRealizados;
    }

    private double calcularProgreso(int totalEjercicios, int ejerciciosRealizados) {
        return totalEjercicios != 0 ? (double) ejerciciosRealizados / totalEjercicios * 100 : 0;
    }

    public void mostrarTemas(Materia materia) {
        if (materia != null) {
            TemaVentana temaVentana = new TemaVentana(materia, this);
            String nombre = "Temas_" + historial.size();
            historial.push(new PanelHistorial(temaVentana, nombre));
            mainPanel.add(temaVentana, nombre);
            cardLayout.show(mainPanel, nombre);
        }
    }

    public void mostrarSubtemas(Tema tema) {
        SubtemaVentana subtemaVentana = new SubtemaVentana(tema, this);
        String nombre = "Subtemas_" + historial.size();
        historial.push(new PanelHistorial(subtemaVentana, nombre));
        mainPanel.add(subtemaVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarContenidoTeorico(Subtema subtema) {
        ContenidoTeoricoVentana contenidoTeoricoVentana = new ContenidoTeoricoVentana(subtema, this);
        String nombre = "ContenidoT_" + historial.size();
        historial.push(new PanelHistorial(contenidoTeoricoVentana, nombre));
        mainPanel.add(contenidoTeoricoVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarContenidoPractico(Subtema subtema) {
        ContenidoPracticoVentana contenidoPracticoVentana = new ContenidoPracticoVentana(subtema, this);
        String nombre = "ContenidoP_" + historial.size();
        historial.push(new PanelHistorial(contenidoPracticoVentana, nombre));
        mainPanel.add(contenidoPracticoVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void regresar() {
        if (!historial.isEmpty()) {
            PanelHistorial panelActual = historial.pop();
            mainPanel.remove(panelActual.panel);

            if (!historial.isEmpty()) {
                cardLayout.show(mainPanel, historial.peek().nombre);
            } else {
                cardLayout.show(mainPanel, "Materias");
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    private static class PanelHistorial {
        JPanel panel;
        String nombre;

        PanelHistorial(JPanel panel, String nombre) {
            this.panel = panel;
            this.nombre = nombre;
        }
    }

    public class BarraProgresoTransparente extends JProgressBar {
        public BarraProgresoTransparente(int min, int max) {
            super(min, max);
            setOpaque(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Bordes suaves

            int arc = 20; // Radio del redondeo
            int width = getWidth();
            int height = getHeight();
            int progressWidth = (int) (width * getPercentComplete());

            // Fondo redondeado semitransparente
            g2.setColor(new Color(0, 122, 204, 80));
            g2.fillRoundRect(0, 0, width, height, arc, arc);

            // Barra de progreso redondeada (recorte para que no sobresalga)
            g2.setClip(new RoundRectangle2D.Double(0, 0, progressWidth, height, arc, arc));
            g2.setColor(new Color(0, 204, 102, 180));
            g2.fillRoundRect(0, 0, width, height, arc, arc); // Dibuja sobre toda la barra, pero se recorta
            g2.setClip(null); // Elimina el recorte

            // Texto del progreso centrado
            if (isStringPainted()) {
                String texto = getString();
                FontMetrics fm = g2.getFontMetrics();
                int textoAncho = fm.stringWidth(texto);
                int textoAlto = fm.getAscent();
                int x = (width - textoAncho) / 2;
                int y = (height + textoAlto) / 2 - 2;

                g2.setColor(new Color(0, 0, 0, 200));
                g2.drawString(texto, x, y);
            }

            g2.dispose();
        }
    }
}
