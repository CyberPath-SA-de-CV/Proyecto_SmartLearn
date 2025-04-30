package org.cyberpath.modelo.entidades.divisionTematica;

import jakarta.persistence.*;
import lombok.*;
import org.cyberpath.modelo.entidades.base.Entidad;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor  // Para JPA
@AllArgsConstructor // Para crear fácilmente objetos completos
@Entity
@Table(name = "TBL_TEMA")
public class Tema extends Entidad {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    @ToString.Exclude  // Evita recursión infinita en logs
    private Materia materia;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Evita recursión infinita en logs
    private List<Subtema> subtemas = new ArrayList<>();

    public Tema(String nombre, Materia materia) {
        this.nombre = nombre;
        this.materia = materia;
    }

    // Métodos de ayuda para mantener consistencia de la relación
    public void agregarSubtema(Subtema subtema) {
        subtemas.add(subtema);
        subtema.setTema(this);
    }

    public void eliminarSubtema(Subtema subtema) {
        subtemas.remove(subtema);
        subtema.setTema(null);
    }
}