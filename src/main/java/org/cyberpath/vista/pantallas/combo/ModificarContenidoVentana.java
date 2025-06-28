package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.modelo.entidades.contenido.ContenidoTeorico;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.ejercicios.Ejercicio;
import org.cyberpath.modelo.entidades.ejercicios.Opcion;
import org.cyberpath.modelo.entidades.ejercicios.Pregunta;
import org.cyberpath.modelo.entidades.ejercicios.TipoEjercicio;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class ModificarContenidoVentana extends PlantillaBaseVentana {

    private JComboBox<Materia> comboMaterias;
    private JButton botonAgregarMateria;
    private JButton botonEditarMateria;
    private JButton botonEliminarMateria;

    private JButton botonAgregarTema;
    private JButton botonEditarTema;
    private JButton botonEliminarTema;

    private JButton botonAgregarSubtema;
    private JButton botonEditarSubtema;
    private JButton botonEliminarSubtema;

    private JButton botonAgregarEjercicio;
    private JButton botonEditarEjercicio;
    private JButton botonEliminarEjercicio;

    private JPanel panelContenido;

    public ModificarContenidoVentana() throws Exception {
        super("Gestión de Contenido", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    protected void inicializarComponentes() {
        panelContenido = crearPanelDegradadoDecorativo("Gestión de Contenido", "src/main/resources/recursosGraficos/titulos/modificacion.jpg");
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));

        agregarComboMaterias();
        agregarSeccionMaterias();
        agregarSeccionTemas();
        agregarSeccionSubtemas();
        agregarSeccionEjercicios();
        agregarBotonSalir();

        getPanelCentral().add(panelContenido);
    }

    private void agregarComboMaterias(){
        panelContenido.add(Box.createVerticalStrut(20));
        comboMaterias = new JComboBox<>(Materia.materiaDao.findAll().toArray(new Materia[0]));
        comboMaterias.setPreferredSize(new Dimension(600, 45));
        estilizarComboBox(comboMaterias);

        JPanel contenedorCombo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        contenedorCombo.setOpaque(false);
        contenedorCombo.add(comboMaterias);

        panelContenido.add(contenedorCombo);
        panelContenido.add(Box.createVerticalStrut(20));
    }

    private void agregarSeccionMaterias() {
        panelContenido.add(crearTituloCentrado("Gestión de Materias"));
        panelContenido.add(Box.createVerticalStrut(20));

        botonAgregarMateria = crearBotonEstilizado("Agregar Materia", null, null);
        botonEditarMateria = crearBotonEstilizado("Editar Materia", null, null);
        botonEliminarMateria = crearBotonEstilizado("Eliminar Materia", null, null);

        agregarBotonesMaterias();
    }


    private void agregarBotonesMaterias() {
        panelContenido.add(botonAgregarMateria, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEditarMateria, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEliminarMateria, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(40));
    }

    private void agregarSeccionTemas() {
        panelContenido.add(crearTituloCentrado("Gestión de Temas"));
        panelContenido.add(Box.createVerticalStrut(20));

        botonAgregarTema = crearBotonEstilizado("Agregar Tema", null, null);
        botonEditarTema = crearBotonEstilizado("Editar Tema", null, null);
        botonEliminarTema = crearBotonEstilizado("Eliminar Tema", null, null);

        agregarBotonesTemas();
    }

    private void agregarBotonesTemas() {
        panelContenido.add(botonAgregarTema, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEditarTema, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEliminarTema, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(40));
    }

    private void agregarSeccionSubtemas() {
        panelContenido.add(crearTituloCentrado("Gestión de Subtemas"));
        panelContenido.add(Box.createVerticalStrut(20));

        botonAgregarSubtema = crearBotonEstilizado("Agregar Subtema", null, null);
        botonEditarSubtema = crearBotonEstilizado("Editar Subtema", null, null);
        botonEliminarSubtema = crearBotonEstilizado("Eliminar Subtema", null, null);

        agregarBotonesSubtemas();
    }

    private void agregarBotonesSubtemas() {
        panelContenido.add(botonAgregarSubtema, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEditarSubtema, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEliminarSubtema, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(40));
    }

    private void agregarSeccionEjercicios() {
        panelContenido.add(crearTituloCentrado("Gestión de Ejercicios"));
        panelContenido.add(Box.createVerticalStrut(20));

        botonAgregarEjercicio = crearBotonEstilizado("Agregar Ejercicio", null, null);
        botonEditarEjercicio = crearBotonEstilizado("Editar Ejercicio", null, null);
        botonEliminarEjercicio = crearBotonEstilizado("Eliminar Ejercicio", null, null);

        agregarBotonesEjercicios();
    }

    private void agregarBotonesEjercicios() {
        panelContenido.add(botonAgregarEjercicio, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEditarEjercicio, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(6));
        panelContenido.add(botonEliminarEjercicio, crearConstraintBotonAncho(3, 0, 3, 1, 200));
        panelContenido.add(Box.createVerticalStrut(40));
    }

    private void agregarBotonSalir() {
        panelContenido.add(Box.createVerticalStrut(40));
        panelContenido.add(crearBotonSalirAPantallaPrincipal());
        panelContenido.add(Box.createVerticalGlue());
    }

    @Override
    protected void agregarEventos() {
        agregarEventosMaterias();
        agregarEventosTemas();
        agregarEventosSubtemas();
        agregarEventosEjercicios();
    }

    private void agregarEventosMaterias() {
        botonAgregarMateria.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre de la nueva materia:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                if (Materia.agregar(nombre.trim())) {
                    JOptionPane.showMessageDialog(this, "Materia agregada exitosamente.");
                    comboMaterias.addItem(Materia.findByNombre(nombre.trim())); // Actualizar el ComboBox
                }
            }
        });

        botonEditarMateria.addActionListener(e -> {
            JTextField campoTexto = new JTextField();
            List<Materia> opcionesMaterias = Materia.materiaDao.findAll();
            JComboBox<Materia> comboBox = new JComboBox<>(opcionesMaterias.toArray(new Materia[0]));

            Object[] mensaje = {
                    "Selecciona una materia:", comboBox,
                    "Nuevo Nombre", campoTexto
            };

            int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Actualizar materia", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                String nuevoNombre = campoTexto.getText();
                Materia materiaSeleccionada = (Materia) comboBox.getSelectedItem();

                if (materiaSeleccionada != null && nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    Integer id = materiaSeleccionada.getId();
                    Materia.actualizar(id, nuevoNombre);
                    JOptionPane.showMessageDialog(null, "Materia actualizada correctamente.");
                    comboMaterias.removeItem(materiaSeleccionada);
                    comboMaterias.addItem(Materia.materiaDao.findById(id)); // Actualizar el ComboBox
                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos para actualizar.");
                }
            }
        });

        botonEliminarMateria.addActionListener(e -> {
            List<Materia> opcionesMaterias = Materia.materiaDao.findAll();
            JComboBox<Materia> comboBox = new JComboBox<>(opcionesMaterias.toArray(new Materia[0]));

            Object[] mensaje = {
                    "Selecciona la materia a eliminar:", comboBox
            };

            int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Eliminar materia", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Materia materiaSeleccionada = (Materia) comboBox.getSelectedItem();
                if (materiaSeleccionada != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro de que deseas eliminar la materia \"" + materiaSeleccionada.getNombre() + "\"?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        Integer id = materiaSeleccionada.getId();
                        Materia.eliminar(id);
                        JOptionPane.showMessageDialog(null, "Materia eliminada exitosamente.");
                        comboMaterias.removeItem(materiaSeleccionada); // Actualizar el ComboBox
                    }
                }
            }
        });
    }

    private void agregarEventosTemas() {
        botonAgregarTema.addActionListener(e -> {
            JTextField campoNombre = new JTextField();
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();

            if (materiaSeleccionada != null) {
                Object[] mensaje = {
                        "Nombre del nuevo tema:", campoNombre
                };

                int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Crear nuevo tema", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.OK_OPTION) {
                    String nombre = campoNombre.getText();
                    if (nombre != null && !nombre.trim().isEmpty()) {
                        Tema.agregar(nombre, materiaSeleccionada);
                        JOptionPane.showMessageDialog(null, "Tema creado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Faltan datos.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
            }
        });

        botonEditarTema.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }

            List<Tema> temas = Tema.findByMateria(materiaSeleccionada);
            JComboBox<Tema> comboTemas = new JComboBox<>(temas.toArray(new Tema[0]));

            JTextField campoNombre = new JTextField();
            Object[] mensaje = {
                    "Selecciona el tema a actualizar:", comboTemas,
                    "Nuevo nombre:", campoNombre
            };

            int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Actualizar tema", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Tema tema = (Tema) comboTemas.getSelectedItem();
                String nuevoNombre = campoNombre.getText();

                if (tema != null) {
                    if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                        Tema.actualizar(tema.getId(), nuevoNombre);
                        JOptionPane.showMessageDialog(null, "Tema actualizado exitosamente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se seleccionó un tema.");
                }
            }
        });

        botonEliminarTema.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }

            List<Tema> temas = Tema.findByMateria(materiaSeleccionada);
            JComboBox<Tema> comboTemas = new JComboBox<>(temas.toArray(new Tema[0]));

            Object[] mensaje = {
                    "Selecciona el tema a eliminar:", comboTemas
            };

            int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Eliminar tema", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Tema tema = (Tema) comboTemas.getSelectedItem();
                if (tema != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro de que deseas eliminar el tema \"" + tema.getNombre() + "\"?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        Tema.eliminar(tema.getId());
                        JOptionPane.showMessageDialog(null, "Tema eliminado exitosamente.");
                    }
                }
            }
        });
    }

    private void agregarEventosSubtemas() {
        botonAgregarSubtema.addActionListener(e -> {
            String fechaSql = LocalDate.now().toString();
            JTextField campoNombre = new JTextField();
            JTextArea campoContenido = new JTextArea(5, 30);
            JScrollPane scroll = new JScrollPane(campoContenido);
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();

            if (materiaSeleccionada != null) {
                List<Tema> temas = Tema.findByMateria(materiaSeleccionada);
                JComboBox<Tema> comboBox = new JComboBox<>(temas.toArray(new Tema[0]));

                Object[] mensaje = {
                        "Nombre del subtema:", campoNombre,
                        "Contenido teórico:", scroll,
                        "Tema asociado:", comboBox
                };

                int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Crear Subtema", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.OK_OPTION) {
                    String nombre = campoNombre.getText();
                    String texto = campoContenido.getText();
                    Tema temaSeleccionado = (Tema) comboBox.getSelectedItem();

                    if (!texto.isEmpty() && !nombre.isEmpty() && temaSeleccionado != null) {
                        ContenidoTeorico contenido = new ContenidoTeorico();
                        contenido.setTexto(texto);
                        contenido.setFecha_creacion(fechaSql);
                        ContenidoTeorico.contenidoTeoricoDao.guardar(contenido);
                        Subtema.agregar(nombre, temaSeleccionado, contenido);
                        JOptionPane.showMessageDialog(null, "Subtema creado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
            }
        });

        botonEditarSubtema.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }

            // Obtener solo subtemas relacionados con la materia seleccionada
            List<Tema> temasMateria = Tema.findByMateria(materiaSeleccionada);
            List<Subtema> subtemasFiltrados = new ArrayList<>();
            for (Tema tema : temasMateria) {
                subtemasFiltrados.addAll(Subtema.findByTema(tema));
            }
            JComboBox<Subtema> comboBox = new JComboBox<>(subtemasFiltrados.toArray(new Subtema[0]));

            int seleccion = JOptionPane.showConfirmDialog(null, comboBox, "Selecciona el Subtema a Editar", JOptionPane.OK_CANCEL_OPTION);
            if (seleccion == JOptionPane.OK_OPTION) {
                Subtema subtemaSeleccionado = (Subtema) comboBox.getSelectedItem();
                if (subtemaSeleccionado == null) return;

                JTextField campoNombre = new JTextField(subtemaSeleccionado.getNombre());
                JTextArea campoContenido = new JTextArea(subtemaSeleccionado.getContenidoTeorico().getTexto(), 5, 30);
                JScrollPane scroll = new JScrollPane(campoContenido);
                List<Tema> temas = Tema.findByMateria(materiaSeleccionada);
                JComboBox<Tema> comboTemas = new JComboBox<>(temas.toArray(new Tema[0]));
                comboTemas.setSelectedItem(subtemaSeleccionado.getTema());

                Object[] mensajeEditar = {
                        "Nuevo nombre:", campoNombre,
                        "Contenido Teórico:", scroll,
                        "Nuevo tema asociado:", comboTemas
                };

                int editar = JOptionPane.showConfirmDialog(null, mensajeEditar, "Editar Subtema", JOptionPane.OK_CANCEL_OPTION);
                if (editar == JOptionPane.OK_OPTION) {
                    String nuevoNombre = campoNombre.getText();
                    String nuevoTexto = campoContenido.getText();
                    Tema nuevoTema = (Tema) comboTemas.getSelectedItem();

                    if (!nuevoNombre.isEmpty() && !nuevoTexto.isEmpty() && nuevoTema != null) {
                        Subtema.actualizar(subtemaSeleccionado.getId(), nuevoNombre);
                        Subtema.actualizar(subtemaSeleccionado.getId(), nuevoTema);
                        ContenidoTeorico.actualizar(subtemaSeleccionado.getContenidoTeorico().getId(), nuevoTexto);
                        JOptionPane.showMessageDialog(null, "Subtema actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No puede haber campos vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        botonEliminarSubtema.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }

            List<Subtema> subtemas = Subtema.subtemaDao.findAll();
            List<Subtema> subtemasFiltrados = new ArrayList<>();
            for (Subtema subtema : subtemas) {
                if (subtema.getTema().getMateria().getId().equals(materiaSeleccionada.getId())) {
                    subtemasFiltrados.add(subtema);
                }
            }

            JComboBox<Subtema> comboBox = new JComboBox<>(subtemasFiltrados.toArray(new Subtema[0]));

            int seleccion = JOptionPane.showConfirmDialog(null, comboBox, "Selecciona el Subtema a eliminar", JOptionPane.OK_CANCEL_OPTION);
            if (seleccion == JOptionPane.OK_OPTION) {
                Subtema subtema = (Subtema) comboBox.getSelectedItem();
                if (subtema == null) return;

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quieres eliminar el subtema \"" + subtema.getNombre() + "\"?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    Subtema.eliminar(subtema);
                    ContenidoTeorico.eliminar(subtema.getContenidoTeorico().getId());
                    JOptionPane.showMessageDialog(null, "Subtema eliminado correctamente.");
                }
            }
        });
    }

    private void agregarEventosEjercicios() {
        botonAgregarEjercicio.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }
            // Obtener solo subtemas relacionados con la materia seleccionada
            List<Tema> temasMateria = Tema.findByMateria(materiaSeleccionada);
            List<Subtema> subtemasFiltrados = new ArrayList<>();
            for (Tema tema : temasMateria) {
                subtemasFiltrados.addAll(Subtema.findByTema(tema));
            }

            JComboBox<TipoEjercicio> comboTipo = new JComboBox<>(TipoEjercicio.tipoEjercicioDao.findAll().toArray(new TipoEjercicio[0]));
            JComboBox<Subtema> comboSubtema = new JComboBox<>(subtemasFiltrados.toArray(new Subtema[0]));
            JTextField campoInstrucciones = new JTextField();

            Object[] mensaje = {
                    "Selecciona el tipo de ejercicio:", comboTipo,
                    "Selecciona el subtema:", comboSubtema,
                    "Instrucciones:", campoInstrucciones
            };

            int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Agregar Ejercicio", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                TipoEjercicio tipoSeleccionado = (TipoEjercicio) comboTipo.getSelectedItem();
                Subtema subtemaSeleccionado = (Subtema) comboSubtema.getSelectedItem();
                String instrucciones = campoInstrucciones.getText();

                if (tipoSeleccionado != null && subtemaSeleccionado != null && !instrucciones.trim().isEmpty()) {
                    Ejercicio ejercicio = new Ejercicio();
                    ejercicio.setTipo(tipoSeleccionado);
                    ejercicio.setSubtema(subtemaSeleccionado);
                    ejercicio.setInstrucciones(instrucciones);
                    ejercicio.setCreador(VariablesGlobales.usuario);

                    Ejercicio.ejercicioDao.guardar(ejercicio);

                    if (tipoSeleccionado.getId() == 2) {
                        int cantidadPreguntas = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas preguntas tiene el cuestionario?"));
                        for (int i = 0; i < cantidadPreguntas; i++) {
                            agregarPregunta(ejercicio);
                        }
                    } else {
                        String enunciado = JOptionPane.showInputDialog("Ingrese el enunciado del ejercicio:");
                        Pregunta pregunta = new Pregunta();
                        pregunta.setEjercicio(ejercicio);
                        pregunta.setEnunciado(enunciado);
                        Pregunta.preguntaDao.guardar(pregunta);

                        String respuestaCorrecta = JOptionPane.showInputDialog("Ingrese la respuesta correcta:");
                        Opcion.agregar(pregunta, respuestaCorrecta, true);
                    }
                    JOptionPane.showMessageDialog(null, "Ejercicio agregado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos para agregar el ejercicio.");
                }
            }
        });

        botonEditarEjercicio.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }

            List<Tema> temasMateria = Tema.findByMateria(materiaSeleccionada);
            List<Subtema> subtemaMateria = new ArrayList<>();
            List<Ejercicio> ejerciciosFiltrados = new ArrayList<>();
            for (Tema tema : temasMateria) {
                for(Subtema subtema : Subtema.subtemaDao.findAll()){
                    if(Objects.equals(subtema.getTema().getId(), tema.getId())){
                        subtemaMateria.add(subtema);
                    }
                }
            }
            for (Subtema subtema : subtemaMateria){
                for (Ejercicio ejercicio : Ejercicio.ejercicioDao.findAll()){
                    if (Objects.equals(ejercicio.getSubtema().getId(), subtema.getId())){
                        ejerciciosFiltrados.add(ejercicio);
                    }
                }
            }

            JComboBox<Ejercicio> comboEjercicios = new JComboBox<>(ejerciciosFiltrados.toArray(new Ejercicio[0]));

            int seleccion = JOptionPane.showConfirmDialog(null, comboEjercicios, "Selecciona el Ejercicio a Editar", JOptionPane.OK_CANCEL_OPTION);
            if (seleccion == JOptionPane.OK_OPTION) {
                Ejercicio ejercicioSeleccionado = (Ejercicio) comboEjercicios.getSelectedItem();
                if (ejercicioSeleccionado == null) return;

                JTextField campoInstrucciones = new JTextField(ejercicioSeleccionado.getInstrucciones());
                JComboBox<TipoEjercicio> comboTipo = new JComboBox<>(TipoEjercicio.tipoEjercicioDao.findAll().toArray(new TipoEjercicio[0]));
                comboTipo.setSelectedItem(ejercicioSeleccionado.getTipo());

                /*
                // Filtrar subtemas segun materia seleccionada
                List<Tema> temasMateria = Tema.findByMateria(materiaSeleccionada);

                 */
                List<Subtema> subtemasFiltrados = new ArrayList<>();
                for (Tema tema : temasMateria) {
                    subtemasFiltrados.addAll(Subtema.findByTema(tema));
                }
                JComboBox<Subtema> comboSubtema = new JComboBox<>(subtemasFiltrados.toArray(new Subtema[0]));
                comboSubtema.setSelectedItem(ejercicioSeleccionado.getSubtema());

                Object[] mensajeEditar = {
                        "Tipo de ejercicio:", comboTipo,
                        "Subtema:", comboSubtema,
                        "Instrucciones:", campoInstrucciones
                };

                int editar = JOptionPane.showConfirmDialog(null, mensajeEditar, "Editar Ejercicio", JOptionPane.OK_CANCEL_OPTION);
                if (editar == JOptionPane.OK_OPTION) {
                    TipoEjercicio tipoSeleccionado = (TipoEjercicio) comboTipo.getSelectedItem();
                    Subtema subtemaSeleccionado = (Subtema) comboSubtema.getSelectedItem();
                    String instrucciones = campoInstrucciones.getText();

                    if (tipoSeleccionado != null && subtemaSeleccionado != null && !instrucciones.trim().isEmpty()) {
                        ejercicioSeleccionado.setTipo(tipoSeleccionado);
                        ejercicioSeleccionado.setSubtema(subtemaSeleccionado);
                        ejercicioSeleccionado.setInstrucciones(instrucciones);
                        Ejercicio.ejercicioDao.actualizar(ejercicioSeleccionado);

                        for (Pregunta pregunta : ejercicioSeleccionado.getPreguntas()) {
                            actualizarPregunta(pregunta);
                        }

                        JOptionPane.showMessageDialog(null, "Ejercicio y preguntas actualizadas exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Faltan datos para actualizar el ejercicio.");
                    }
                }
            }
        });

        botonEliminarEjercicio.addActionListener(e -> {
            Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
            if (materiaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Selecciona una materia primero.");
                return;
            }

            List<Ejercicio> ejercicios = Ejercicio.ejercicioDao.findAll();
            List<Ejercicio> ejerciciosFiltrados = new ArrayList<>();
            for (Ejercicio ejercicio : ejercicios) {
                if (ejercicio.getSubtema().getTema().getMateria().getId().equals(materiaSeleccionada.getId())) {
                    ejerciciosFiltrados.add(ejercicio);
                }
            }

            JComboBox<Ejercicio> comboEjercicios = new JComboBox<>(ejerciciosFiltrados.toArray(new Ejercicio[0]));

            int seleccion = JOptionPane.showConfirmDialog(null, comboEjercicios, "Selecciona el Ejercicio a Eliminar", JOptionPane.OK_CANCEL_OPTION);
            if (seleccion == JOptionPane.OK_OPTION) {
                Ejercicio ejercicioSeleccionado = (Ejercicio) comboEjercicios.getSelectedItem();
                if (ejercicioSeleccionado == null) return;

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quieres eliminar el ejercicio \"" + ejercicioSeleccionado.getInstrucciones() + "\"?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    Ejercicio.eliminar(ejercicioSeleccionado.getId());
                    JOptionPane.showMessageDialog(null, "Ejercicio eliminado exitosamente.");
                }
            }
        });
    }


    private void agregarPregunta(Ejercicio ejercicio) {
        JTextField campoEnunciado = new JTextField();
        JTextField campoOpcionA = new JTextField();
        JTextField campoOpcionB = new JTextField();
        JTextField campoOpcionC = new JTextField();
        JTextField campoOpcionD = new JTextField();
        JComboBox<String> comboRespuestaCorrecta = new JComboBox<>(new String[]{"A", "B", "C", "D"});

        Object[] mensajePregunta = {
                "Enunciado de la pregunta:", campoEnunciado,
                "Opción A:", campoOpcionA,
                "Opción B:", campoOpcionB,
                "Opción C:", campoOpcionC,
                "Opción D:", campoOpcionD,
                "Respuesta correcta:", comboRespuestaCorrecta
        };

        int opcionPregunta = JOptionPane.showConfirmDialog(null, mensajePregunta, "Agregar Pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (opcionPregunta == JOptionPane.OK_OPTION) {
            String enunciado = campoEnunciado.getText();
            Pregunta pregunta = new Pregunta();
            pregunta.setEjercicio(ejercicio);
            pregunta.setEnunciado(enunciado);

            Pregunta.preguntaDao.guardar(pregunta);

            Opcion.agregar(pregunta, campoOpcionA.getText(), comboRespuestaCorrecta.getSelectedItem().equals("A"));
            Opcion.agregar(pregunta, campoOpcionB.getText(), comboRespuestaCorrecta.getSelectedItem().equals("B"));
            Opcion.agregar(pregunta, campoOpcionC.getText(), comboRespuestaCorrecta.getSelectedItem().equals("C"));
            Opcion.agregar(pregunta, campoOpcionD.getText(), comboRespuestaCorrecta.getSelectedItem().equals("D"));
        }
    }

    private void actualizarPregunta(Pregunta pregunta) {
        JTextField campoEnunciado = new JTextField(pregunta.getEnunciado());
        List<Opcion> opciones = pregunta.getOpciones();
        JTextField campoOpcionA = new JTextField(opciones.size() > 0 ? opciones.get(0).getTexto() : "");
        JTextField campoOpcionB = new JTextField(opciones.size() > 1 ? opciones.get(1).getTexto() : "");
        JTextField campoOpcionC = new JTextField(opciones.size() > 2 ? opciones.get(2).getTexto() : "");
        JTextField campoOpcionD = new JTextField(opciones.size() > 3 ? opciones.get(3).getTexto() : "");
        JComboBox<String> comboRespuestaCorrecta = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        comboRespuestaCorrecta.setSelectedItem(opciones.stream().filter(Opcion::getEs_correcta).findFirst().map(opcion -> {
            if (opcion.getTexto().equals(campoOpcionA.getText())) return "A";
            if (opcion.getTexto().equals(campoOpcionB.getText())) return "B";
            if (opcion.getTexto().equals(campoOpcionC.getText())) return "C";
            if (opcion.getTexto().equals(campoOpcionD.getText())) return "D";
            return null;
        }).orElse(null));

        Object[] mensajePregunta = {
                "Enunciado de la pregunta:", campoEnunciado,
                "Opción A:", campoOpcionA,
                "Opción B:", campoOpcionB,
                "Opción C:", campoOpcionC,
                "Opción D:", campoOpcionD,
                "Respuesta correcta:", comboRespuestaCorrecta
        };

        int opcionPregunta = JOptionPane.showConfirmDialog(null, mensajePregunta, "Actualizar Pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (opcionPregunta == JOptionPane.OK_OPTION) {
            String enunciado = campoEnunciado.getText();
            String opcionA = campoOpcionA.getText();
            String opcionB = campoOpcionB.getText();
            String opcionC = campoOpcionC.getText();
            String opcionD = campoOpcionD.getText();
            String respuestaCorrecta = (String) comboRespuestaCorrecta.getSelectedItem();

            if (!enunciado.trim().isEmpty() && !opcionA.trim().isEmpty() && !opcionB.trim().isEmpty() && !opcionC.trim().isEmpty() && !opcionD.trim().isEmpty() && respuestaCorrecta != null) {
                pregunta.setEnunciado(enunciado);
                List<Opcion> nuevasOpciones = new ArrayList<>();
                nuevasOpciones.add(new Opcion(pregunta, opcionA, respuestaCorrecta.equals("A")));
                nuevasOpciones.add(new Opcion(pregunta, opcionB, respuestaCorrecta.equals("B")));
                nuevasOpciones.add(new Opcion(pregunta, opcionC, respuestaCorrecta.equals("C")));
                nuevasOpciones.add(new Opcion(pregunta, opcionD, respuestaCorrecta.equals("D")));
                pregunta.setOpciones(nuevasOpciones);
                Pregunta.preguntaDao.actualizar(pregunta);
            } else {
                JOptionPane.showMessageDialog(null, "Faltan datos para actualizar la pregunta.");
            }
        }
    }

    private void estilizarComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setForeground(new Color(33, 37, 41));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(108, 117, 125), 1, true), // Borde gris suave, redondeado
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        comboBox.setMaximumRowCount(8);
        comboBox.setFocusable(false);

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setBorder(new EmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
    }


    @Override
    public JPanel getContenido() {
        return this.panelContenido;
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(33);
        VariablesGlobales.usuario = ejemplo;
        SwingUtilities.invokeLater(() -> {
            try {
                new ModificarContenidoVentana().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
