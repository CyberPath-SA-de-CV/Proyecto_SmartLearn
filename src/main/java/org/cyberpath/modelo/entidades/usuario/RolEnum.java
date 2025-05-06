package org.cyberpath.modelo.entidades.usuario;

public enum RolEnum {
    ADMINISTRADOR(1),
    ALUMNO(2);

    private Integer id_rol;

    RolEnum(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public static RolEnum getId(Integer id_rol){
        return switch (id_rol) {
            case 1 -> ADMINISTRADOR;
            //  case 3 -> ADMINISTRADOR;
            case 2 -> ALUMNO;
            //  case 4 -> ALUMNO;
            default -> null;
        };
    }
}
