package org.cyberpath.modelo.baseDatos.dao.implementacion;

import lombok.NoArgsConstructor;
import org.cyberpath.modelo.baseDatos.dao.DaoInterface;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DaoImpl<T extends Entidad> implements DaoInterface<T> {

    private Class<T> clase;

    public DaoImpl(Class<T> clase) {
        this.clase = clase;
    }

    @Override
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from " + clase.getSimpleName(), clase).getResultList();
        }
    }

    @Override
    public List<T> findAllWithFetch(String fetchQuery) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(fetchQuery, clase).getResultList();
        }
    }

    @Override
    public boolean guardar(T entidad) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(entidad);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean actualizar(T entidad) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.merge(entidad);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean eliminar(T entidad) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.remove(entidad);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public T findById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(clase, id);
        }
    }



    public List<Materia> obtenerMateriasInscritasPorUsuario(int idUsuario) {
        try (Session session = HibernateUtil.getSession()) {
            Usuario usuario = session.get(Usuario.class, idUsuario);
            Hibernate.initialize(usuario.getMateriasInscritas());

            return usuario.getMateriasInscritas().stream()
                    .map(inscripcion -> {
                        Materia materia = inscripcion.getMateria();
                        Hibernate.initialize(materia);
                        return materia;
                    })
                    .collect(Collectors.toList());
        }
    }
}
