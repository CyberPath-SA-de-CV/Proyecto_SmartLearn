package org.cyberpath.modelo.baseDatos.dao.implementacion;

import lombok.NoArgsConstructor;
import org.cyberpath.modelo.baseDatos.dao.DaoInterface;
import org.cyberpath.modelo.baseDatos.hibernate.HibernateUtil;
import org.cyberpath.modelo.entidades.base.Entidad;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.UsuarioMateria;
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
        Session session = HibernateUtil.getSession();
        List<T> lista = session.createQuery("from " + clase.getSimpleName(), clase).getResultList();
        session.close();
        return lista;
    }

    @Override
    public List<T> findAllWithFetch(String fetchQuery) {
        Session session = HibernateUtil.getSession();
        List<T> lista = session.createQuery(fetchQuery, clase).getResultList();
        session.close();
        return lista;
    }

    @Override
    public boolean guardar(T e) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(e);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean actualizar(T e) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(e);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean eliminar(T e) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(e);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public T findById(int id) {
        Session session = HibernateUtil.getSession();
        T obj = session.get(clase, id);
        session.close();
        return obj;
    }

    public List<Materia> obtenerMateriasInscritasPorUsuario(int idUsuario) {
        try (Session session = HibernateUtil.getSession()) {
            Usuario usuario = session.get(Usuario.class, idUsuario);
            Hibernate.initialize(usuario.getMateriasInscritas());

            return usuario.getMateriasInscritas().stream()
                    .map(um -> {
                        Materia materia = um.getMateria();
                        Hibernate.initialize(materia); // <- esto es clave
                        return materia;
                    })
                    .collect(Collectors.toList());
        }
    }

    public Usuario findUsuarioConMaterias(int idUsuario) {
        Session session = HibernateUtil.getSession();
        Usuario usuario = session.createQuery(
                "SELECT u FROM Usuario u LEFT JOIN FETCH u.materiasInscritas mi LEFT JOIN FETCH mi.materia WHERE u.id = :id",
                Usuario.class
        ).setParameter("id", idUsuario).uniqueResult();
        session.close();
        return usuario;
    }
}
