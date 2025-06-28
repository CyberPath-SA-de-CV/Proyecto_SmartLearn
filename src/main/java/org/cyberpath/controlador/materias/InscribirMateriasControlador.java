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
import java.util.stream.Collectors;

public class InscribirMateriasControlador {

    private static final SalidaAudioControlador ttsControlador = SalidaAudioControlador.getInstance();
    private static final EntradaAudioControlador sttControlador;

    static {
        try {
            sttControlador = EntradaAudioControlador.getInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar controladores de audio", e);
        }
    }

    public static boolean procesarInscripcion(Materia materia) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            for(UsuarioMateria materiaAux : VariablesGlobales.usuario.getMateriasInscritas()){
                if ( Objects.equals(materiaAux.getMateria().getId(), materia.getId()) ){
                    return false;
                }
            }
            Transaction tx = session.beginTransaction();

            Usuario usuarioActual = session.get(Usuario.class, VariablesGlobales.usuario.getId());

            UsuarioMateria nuevaInscripcion = new UsuarioMateria();
            nuevaInscripcion.setUsuario(usuarioActual);
            nuevaInscripcion.setMateria(materia);
            nuevaInscripcion.setFechaInscripcion(LocalDate.now());

            usuarioActual.agregarInscripcion(nuevaInscripcion);
            materia.agregarInscripcion(nuevaInscripcion);

            session.persist(nuevaInscripcion);
            session.merge(usuarioActual);

            tx.commit();

            VariablesGlobales.usuario = usuarioActual;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void procesarAccesibilidad(Window window) throws Exception {
        if (!VariablesGlobales.auxModoAudio) return;

        List<Integer> idsMateriasInscritas = VariablesGlobales.usuario.getMateriasInscritas().stream()
                .map(inscripcion -> inscripcion.getMateria().getId())
                .toList();

        List<Materia> materiasFiltradas = Materia.materiaDao.findAll().stream()
                .filter(m -> !idsMateriasInscritas.contains(m.getId()))
                .toList();

        List<String> opciones = new ArrayList<>();
        opciones.add("ninguna");

        if (materiasFiltradas.isEmpty()) {
            ttsControlador.hablar("No hay materias disponibles para inscribirse. Regresando al menú principal.", 6);
            cerrarYVolverAMenu(window);
            return;
        }

        ttsControlador.hablar("A continuación se enlistan las materias disponibles para inscribirse.", 5);
        for (Materia materia : materiasFiltradas) {
            ttsControlador.hablar(materia.getNombre(), 3);
            opciones.add(materia.getNombre().toLowerCase());
        }

        ttsControlador.hablar("Si desea inscribir una materia, diga su nombre. De lo contrario, diga 'ninguna' para volver al menú principal.", 6);
        String seleccion = sttControlador.esperarPorPalabrasClave(opciones.toArray(new String[0])).toLowerCase();

        if (seleccion.equals("ninguna")) {
            cerrarYVolverAMenu(window);
            return;
        }

        for (Materia materia : materiasFiltradas) {
            if (materia.getNombre().equalsIgnoreCase(seleccion)) {
                procesarInscripcion(materia);
                ttsControlador.hablar("Materia" + materia.getNombre() + "inscrita correctamente. ¿Desea inscribir otra? Responda sí o no.");

                if (sttControlador.entradaAfirmacionNegacion()) {
                    procesarAccesibilidad(window);
                } else {
                    cerrarYVolverAMenu(window);
                }
                return;
            }
        }

        ttsControlador.hablar("No se reconoció la materia mencionada. Volviendo al menú principal.");
        cerrarYVolverAMenu(window);
    }

    private static void cerrarYVolverAMenu(Window window) throws Exception {
        window.dispose();
        PantallasControlador.mostrarPantalla(PantallasEnum.MENU_PRINCIPAL);
    }
}
