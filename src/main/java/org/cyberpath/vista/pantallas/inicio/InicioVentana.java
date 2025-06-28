package org.cyberpath.vista.pantallas.inicio;

import org.cyberpath.controlador.inicio.InicioControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.cuenta.InicioSesionVentanta;
import org.cyberpath.vista.pantallas.cuenta.RegistroVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearBotonEstilizado;
import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearEtiqueta;
import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.crearConstraintBotonAncho;

public class InicioVentana extends JFrame {

    private JPanel panelPrincipal;
    private JButton botonLogin;
    private JButton botonRegistro;
    private JButton botonSalirApp;

    public InicioVentana() throws Exception {
        super("Smart-Learn");
        System.out.println(VariablesGlobales.auxModoAudio);
        configurarVentana();
        inicializarComponentes();
        agregarEventos();
        iniciarHiloAccesibilidad();
    }

    private void configurarVentana() {
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() throws Exception {
        panelPrincipal = new PanelConRayasVerticales();
        panelPrincipal.setLayout(new GridBagLayout());

        agregarImagenDecorativa();
        agregarEtiquetaBienvenida();
        agregarBotones();

        getRootPane().setDefaultButton(botonLogin);
        setContentPane(panelPrincipal);
    }

    private void agregarImagenDecorativa() {
        ImageIcon iconoSuperiorIzquierdo = new ImageIcon("src/main/resources/recursosGraficos/titulos/inicio.jpg");
        Image imagenEscalada = iconoSuperiorIzquierdo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel labelImagenIzquierda = new JLabel(new ImageIcon(imagenEscalada));

        GridBagConstraints gbcImagen = new GridBagConstraints();
        gbcImagen.gridx = 0;
        gbcImagen.gridy = 0;
        gbcImagen.anchor = GridBagConstraints.NORTHWEST;
        gbcImagen.insets = new Insets(10, 10, 0, 0);
        panelPrincipal.add(labelImagenIzquierda, gbcImagen);
    }

    private void agregarEtiquetaBienvenida() {
        JLabel mensaje = crearEtiqueta("¡Bienvenido a Smart Learn!");
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mensaje.setForeground(new Color(185, 222, 245));
        panelPrincipal.add(mensaje, crearConstraintBotonAncho(0, 0, 3, 1, 100));
    }

    private void agregarBotones() {
        botonLogin = crearBotonEstilizado("Iniciar Sesión", null, null);
        botonLogin.setMnemonic(KeyEvent.VK_I);
        panelPrincipal.add(botonLogin, crearConstraintBotonAncho(1, 0, 3, 1, 100));

        botonRegistro = crearBotonEstilizado("Registrar Usuario", null, null);
        botonRegistro.setMnemonic(KeyEvent.VK_R);
        panelPrincipal.add(botonRegistro, crearConstraintBotonAncho(2, 0, 3, 1, 100));

        botonSalirApp = crearBotonEstilizado("Salir de la App", null, null);
        botonSalirApp.setMnemonic(KeyEvent.VK_S);
        panelPrincipal.add(botonSalirApp, crearConstraintBotonAncho(3, 0, 3, 1, 100));
    }

    private void agregarEventos() {
        botonLogin.addActionListener(e -> abrirVentanaInicioSesion());
        botonRegistro.addActionListener(e -> abrirVentanaRegistro());
        botonSalirApp.addActionListener(e -> salirAplicacion());
    }

    private void abrirVentanaInicioSesion() {
        try {
            new InicioSesionVentanta().setVisible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Entrando a Login");
        dispose();
    }

    private void abrirVentanaRegistro() {
        new RegistroVentana().setVisible(true);
        System.out.println("Entrando a Registro Usr");
        dispose();
    }

    private void salirAplicacion() {
        System.out.println("Saliendo de la aplicación");
        System.exit(0);
    }

    private void iniciarHiloAccesibilidad() {
        new Thread(() -> {
            try {
                InicioControlador.procesarAccesiblidad(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(33);
        VariablesGlobales.usuario = ejemplo;
        SwingUtilities.invokeLater(() -> {
            try {
                new InicioVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
}
