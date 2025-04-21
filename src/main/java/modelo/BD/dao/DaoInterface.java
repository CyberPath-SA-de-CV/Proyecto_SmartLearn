package modelo.BD.dao;

import java.util.List;

public interface DaoInterface<T>{
    List<T> findAll( );
    boolean guardar( T t );
    boolean actualizar( T t );
    boolean eliminar( T t );
    T findById( int id );
}
