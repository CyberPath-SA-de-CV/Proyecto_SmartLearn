package org.cyberpath.modelo.entidades.ejercicios;

import java.util.Objects;

public enum TipoEjercicioEnum {
    EJERCICIO(TipoEjercicio.tipoEjercicioDao.findById(1), "ejercicio"),
    CUESTIONARIO(TipoEjercicio.tipoEjercicioDao.findById(2), "cuestionario");
    private TipoEjercicio tipoEjericio;
    private String tipoString;

    /*

    mysql> use smartlearn
Database changed
mysql> TRUNCATE TABLE TBL_ROL;
ERROR 1701 (42000): Cannot truncate a table referenced in a foreign key constraint (`smartlearn`.`tbl_usuario`, CONSTRAINT `usuario_ibfk_1`)
mysql> ALTER TABLE TBL_ROL AUTO_INCREMENT = 1;
Query OK, 0 rows affected (0.16 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> ALTER TABLE TBL_TIPO_EJERCICIO AUTO_INCREMENT = 1;
Query OK, 0 rows affected (0.11 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> INSERT INTO TBL_ROL (NOMBRE) VALUES ("ADMIN"), ("ESTUDIANTE");
Query OK, 2 rows affected (0.08 sec)
Records: 2  Duplicates: 0  Warnings: 0

mysql> INTO TBL_TIPO_EJERCICIO (NOMBRE) VALUES ("ejercicio"), ("cuestionario");
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'INTO TBL_TIPO_EJERCICIO (NOMBRE) VALUES ("ejercicio"), ("cuestionario")' at line 1
mysql> INSERT INTO TBL_TIPO_EJERCICIO (NOMBRE) VALUES ("ejercicio"), ("cuestionario");
Query OK, 2 rows affected (0.10 sec)
Records: 2  Duplicates: 0  Warnings: 0

     */

    TipoEjercicioEnum(TipoEjercicio tipoEjericio, String tipoString) {
        this.tipoEjericio = tipoEjericio;
        this.tipoString = tipoString;
    }

    public static TipoEjercicio getTipoEjercicio(String tipo){
        if(Objects.equals(EJERCICIO.tipoString, tipo)){
            return EJERCICIO.tipoEjericio;
        } else if (Objects.equals(CUESTIONARIO.tipoString, tipo)){
            return CUESTIONARIO.tipoEjericio;
        } else {
            return null;
        }
    }
}
