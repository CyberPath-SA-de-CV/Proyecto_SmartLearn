package org.cyberpath.controlador.materias;

import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.modelo.entidades.divisionTematica.UsuarioMateria;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.util.VariablesGlobales;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;

public class InscripcionMateriasControlador {

    public void procesarInscripcion(Materia materia) {
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
            nuevaInscripcion.setProgreso(0); // o el valor inicial que uses

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
}
