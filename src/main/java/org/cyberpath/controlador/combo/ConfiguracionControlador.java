package org.cyberpath.controlador.combo;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.usuario.InicioSesionControlador;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.cyberpath.vista.pantallas.inicio.InicioVentana;
import org.cyberpath.vista.util.componentes.PanelConRayasVerticales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ConfiguracionControlador {

    private static EntradaAudioControlador sttControlador;
    private static SalidaAudioControlador ttsControlador;

    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
            ttsControlador = SalidaAudioControlador.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void procesarAccesibilidad(JFrame ventana) throws Exception {
        if (VariablesGlobales.auxModoAudio) {
            ttsControlador.hablar("Bienvenido a la configuración de usuario. ¿Qué dato desea cambiar? Diga 'nombre', 'contraseña' o 'correo'. O repetir el menu principal", 5);
            String[] opciones = {"nombre", "contraseña", "correo", "repetir el menu principal", "repetir menu", "repetir"};
            String seleccion = sttControlador.esperarPorPalabrasClave(opciones);
            if(Objects.equals(seleccion, "repetir el menu principal") || Objects.equals(seleccion, "repetir menu") || Objects.equals(seleccion, "repetir")){
                if(PantallasControlador.menuAccesibilidad("Configuración de Cuenta", ventana)){
                    switch (seleccion) {
                        case "nombre":
                            cambiarNombre(ventana);
                            break;
                        case "contraseña":
                            cambiarContrasena(ventana);
                            break;
                        case "correo":
                            cambiarCorreo(ventana);
                            break;
                    }
                    procesarAccesibilidad(ventana);
                }
            } else {
                switch (seleccion) {
                    case "nombre":
                        cambiarNombre(ventana);
                        break;
                    case "contraseña":
                        cambiarContrasena(ventana);
                        break;
                    case "correo":
                        cambiarCorreo(ventana);
                        break;
                }
                procesarAccesibilidad(ventana);
            }
        }
    }

    private static void cambiarNombre(JFrame ventana) {
        JDialog dialog = new JDialog(ventana, "Cambio de Nombre", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(ventana);
        dialog.setLayout(new BorderLayout());

        JTextField campoNombre = crearCampoTxt(15);

        ttsControlador.hablar("Ventana cambio de nombre abierta, escriba el nuevo nombre con el teclado, presione la combinación Alt más N para confirmar, se hará saber cuando pueda empezar a escribir");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        campoNombre.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                ttsControlador.hablar("Campo nombre activo");
            }
        });

        JPanel panel = new PanelConRayasVerticales();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(5, 100, 110));

        JLabel nombreEtiqueta = crearEtiqueta("Nombre:");
        nombreEtiqueta.setFont(nombreEtiqueta.getFont().deriveFont(20f));
        nombreEtiqueta.setForeground(Color.WHITE);

        //
        JButton botonAceptar = crearBotonEstilizado("Aceptar", null, e -> {
            String nombreNuevo = campoNombre.getText();
            new Thread(() -> {
                try {
                    ttsControlador.hablar("Usted ingresó el nombre: " + nombreNuevo + ". ¿Desea cambiar a ese nombre? Diga sí o no.");
                    boolean confirmacion = sttControlador.entradaAfirmacionNegacion();

                    if (confirmacion) {
                        ttsControlador.hablar("Cambiando el nombre.", 1);
                        VariablesGlobales.usuario.setNombre(nombreNuevo);
                        Usuario.actualizar(VariablesGlobales.usuario);
                        dialog.dispose();
                    } else {
                        ttsControlador.hablar("Por favor, corrija los datos e intente de nuevo o presione Alt + S para salir");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ttsControlador.hablar("Ocurrió un error al reconocer su respuesta.");
                }
            }).start();
        });
        botonAceptar.setFocusable(false);
        botonAceptar.setMnemonic(KeyEvent.VK_N); // Alt + N

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK), "focoUsuario");
        actionMap.put("focoUsuario", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoNombre.requestFocusInWindow();
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "salir");
        actionMap.put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ttsControlador.hablar("Cerrando la ventana");
                dialog.dispose();  // Cierra solo esta ventana
            }
        });

        panel.add(nombreEtiqueta, crearConstraint(0, 0, 1, 1, 10));
        panel.add(campoNombre, crearConstraint(0, 1, 2, 1, 60));
        panel.add(botonAceptar, crearConstraintBotonAncho(2, 0, 3, 1, 200));

        JButton botonVolver = crearBotonEstilizado("Volver a Inicio", null, e -> {
            try {
                procesarAccesibilidad(ventana);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dialog.dispose();
        });
        botonVolver.setFocusable(false);
        panel.add(botonVolver, crearConstraintBotonAncho(3, 0, 3, 1, 200));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }

    private static void cambiarCorreo(JFrame ventana) {
        JDialog dialog = new JDialog(ventana, "Cambio de Correo", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(ventana);
        dialog.setLayout(new BorderLayout());

        JTextField campoNombre = crearCampoTxt(15);

        ttsControlador.hablar("Ventana cambio de correo abierta, escriba el nuevo correo con el teclado, presione la combinación Alt más N para confirmar, se hará saber cuando pueda empezar a escribir, el correo debe tener el formato. Nombre De usuario. Arroba. dominio");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        campoNombre.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                ttsControlador.hablar("Campo correo activo");
            }
        });

        JPanel panel = new PanelConRayasVerticales();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(5, 100, 110));

        JLabel nombreEtiqueta = crearEtiqueta("Correo:");
        nombreEtiqueta.setFont(nombreEtiqueta.getFont().deriveFont(20f));
        nombreEtiqueta.setForeground(Color.WHITE);

        //
        JButton botonAceptar = crearBotonEstilizado("Aceptar", null, e -> {
            String correoNuevo = campoNombre.getText();
            new Thread(() -> {
                try {
                    ttsControlador.hablar("Usted ingresó el correo: " + correoNuevo + ". ¿Desea cambiar a ese nombre? Diga sí o no.");
                    boolean confirmacion = sttControlador.entradaAfirmacionNegacion();
                    if(!correoNuevo.isEmpty() || correoNuevo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        if (confirmacion) {
                            ttsControlador.hablar("Cambiando el correo.", 1);
                            VariablesGlobales.usuario.setCorreo(correoNuevo);
                            Usuario.actualizar(VariablesGlobales.usuario);
                            dialog.dispose();
                        } else {
                            ttsControlador.hablar("Por favor, corrija los datos e intente de nuevo o presione Alt + S para salir");
                        }
                    } else {
                        ttsControlador.hablar("El siguiente correo que ingresó es inválido" + correoNuevo + " Por favor, corrija los datos e intente de nuevo o presione Alt + S para salir");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ttsControlador.hablar("Ocurrió un error al reconocer su respuesta.");
                }
            }).start();
        });
        botonAceptar.setFocusable(false);
        botonAceptar.setMnemonic(KeyEvent.VK_N); // Alt + N

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK), "focoCorreo");
        actionMap.put("focoCorreo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoNombre.requestFocusInWindow();
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "salir");
        actionMap.put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ttsControlador.hablar("Cerrando la ventana");
                dialog.dispose();  // Cierra solo esta ventana
            }
        });

        panel.add(nombreEtiqueta, crearConstraint(0, 0, 1, 1, 10));
        panel.add(campoNombre, crearConstraint(0, 1, 2, 1, 60));
        panel.add(botonAceptar, crearConstraintBotonAncho(2, 0, 3, 1, 200));

        JButton botonVolver = crearBotonEstilizado("Volver a Inicio", null, e -> {
            try {
                procesarAccesibilidad(ventana);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dialog.dispose();
        });
        botonVolver.setFocusable(false);
        panel.add(botonVolver, crearConstraintBotonAncho(3, 0, 3, 1, 200));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }

    private static void cambiarContrasena(JFrame ventana) {
        JDialog dialog = new JDialog(ventana, "Cambio de Contraseña", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(ventana);
        dialog.setLayout(new BorderLayout());

        JTextField campoContrasena1 = crearCampoTxt(15);
        JTextField campoContrasena2 = crearCampoTxt(15);

        ttsControlador.hablar("Ventana cambio de contraseña abierta, escriba la nueva contraseña dos veces, cuando termine la primera pulse la tecla tabulador y escribala de nuevo, la nuevo contraseña con el teclado, presione la combinación Alt más N para confirmar, se hará saber cuando pueda empezar a escribir");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        campoContrasena1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                ttsControlador.hablar("Campo contraseña activo");
            }
        });
        campoContrasena2.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                ttsControlador.hablar("Campo confirmar contraseña activo");
            }
        });

        JPanel panel = new PanelConRayasVerticales();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(5, 100, 110));

        JLabel cont1Etiqueta = crearEtiqueta("Contraseña:");
        cont1Etiqueta.setFont(cont1Etiqueta.getFont().deriveFont(20f));
        cont1Etiqueta.setForeground(Color.WHITE);
        JLabel cont2Etiqueta = crearEtiqueta("Confirmar contraseña:");
        cont2Etiqueta.setFont(cont2Etiqueta.getFont().deriveFont(20f));
        cont2Etiqueta.setForeground(Color.WHITE);

        //
        JButton botonAceptar = crearBotonEstilizado("Aceptar", null, e -> {
            String contrasena1 = campoContrasena1.getText();
            String contrasena2 = campoContrasena2.getText();
            new Thread(() -> {
                try {
                    if(Objects.equals(contrasena1, contrasena2)) {
                        ttsControlador.hablar("Cambiando la contraseña", 1);
                        VariablesGlobales.usuario.setContrasena(contrasena1);
                        Usuario.actualizar(VariablesGlobales.usuario);
                        dialog.dispose();
                    } else {
                        ttsControlador.hablar("Las contraseñas no coinciden, Por favor, corrija los datos e intente de nuevo o presione Alt + S para salir");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ttsControlador.hablar("Ocurrió un error al reconocer su respuesta.");
                }

            }).start();
        });
        botonAceptar.setFocusable(false);
        botonAceptar.setMnemonic(KeyEvent.VK_N); // Alt + N

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK), "focoCont1");
        actionMap.put("focoCont1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoContrasena1.requestFocusInWindow();
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK), "focoCont2");
        actionMap.put("focoCont2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoContrasena2.requestFocusInWindow();
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "salir");
        actionMap.put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ttsControlador.hablar("Cerrando la ventana");
                dialog.dispose();  // Cierra solo esta ventana
            }
        });

        panel.add(cont1Etiqueta, crearConstraint(0, 0, 1, 1, 10));
        panel.add(campoContrasena1, crearConstraint(0, 1, 2, 1, 60));
        panel.add(cont2Etiqueta, crearConstraint(1, 0, 1, 1, 10));
        panel.add(campoContrasena2, crearConstraint(1, 1, 2, 1, 60));
        panel.add(botonAceptar, crearConstraintBotonAncho(2, 0, 3, 1, 200));

        JButton botonVolver = crearBotonEstilizado("Volver a Inicio", null, e -> {
            try {
                procesarAccesibilidad(ventana);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dialog.dispose();
        });
        botonVolver.setFocusable(false);
        panel.add(botonVolver, crearConstraintBotonAncho(3, 0, 3, 1, 200));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }

}
