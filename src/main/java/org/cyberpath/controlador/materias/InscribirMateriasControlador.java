package org.cyberpath.controlador.materias;

import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.controlador.pantallas.PantallasEnum;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.util.audio.EntradaAudioControlador;
import org.cyberpath.util.audio.SalidaAudioControlador;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InscribirMateriasControlador {

    public static void procesarInscripcion(Materia materia) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Usuario usuarioActual = session.get(Usuario.class, VariablesGlobales.usuario.getId());

            // Crear la relación UsuarioMateria
            UsuarioMateria nuevaInscripcion = new UsuarioMateria();
            nuevaInscripcion.setUsuario(usuarioActual);
            nuevaInscripcion.setMateria(materia);
            nuevaInscripcion.setFechaInscripcion(LocalDate.now());
            usuarioActual.agregarInscripcion(nuevaInscripcion);
            materia.agregarInscripcion(nuevaInscripcion);

            // Asociar ambas partes
            usuarioActual.getMateriasInscritas().add(nuevaInscripcion);
            session.persist(nuevaInscripcion);

            session.merge(usuarioActual);
            tx.commit();

            // Reflejar cambios en VariablesGlobales si es necesario
            VariablesGlobales.usuario = usuarioActual;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void procesarAccesibilidad(Window window) throws Exception {
        if (VariablesGlobales.auxModoAudio) {
            EntradaAudioControlador sttControlador = EntradaAudioControlador.getInstance();
            SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();

            List<Materia> materias = Materia.materiaDao.findAll();
            List<String> materiasNombres = new ArrayList<>();
            materiasNombres.add("ninguna");

            if (materias.isEmpty()) {
                ttsControlador.hablar("no hay materias disponibles para inscribir, regresando al menu principal");
                window.dispose();
                PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
            } else {
                ttsControlador.hablar("Acontinuación se hace una lista de todas las materias que puede inscribir", 5);
                for (Materia materia : materias) {
                    ttsControlador.hablar(materia.getNombre(), 3);
                    materiasNombres.add(materia.getNombre().toLowerCase());
                }
                ttsControlador.hablar("Si desea incribir una materia diga el nombre de ésta, si nó diga, ninguna y volverá al menú principal", 6);
                String materiaInscribir = sttControlador.esperarPorPalabrasClave(materiasNombres.toArray(new String[0])).toLowerCase();
                if (!Objects.equals(materiaInscribir, "ninguna")) {
                    for (Materia materia : materias) {
                        if (Objects.equals(materiaInscribir, materia.getNombre().toLowerCase())) {
                            procesarInscripcion(materia);
                            ttsControlador.hablar("Materia inscrita correctamente, ¿desea incribir otra?, responda si o no en voz alta");
                            if (sttControlador.entradaAfirmacionNegacion()) {
                                procesarAccesibilidad(window);
                            } else {
                                window.dispose();
                                PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
                            }
                        }
                    }
                } else {
                    window.dispose();
                    PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
                }
            }


        }
    }
}
