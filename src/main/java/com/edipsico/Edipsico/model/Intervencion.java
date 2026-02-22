package com.edipsico.Edipsico.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Intervencion")
public class Intervencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_de_intervencion")
    private Integer numeroDeIntervencion;

    @JsonBackReference("paciente-intervenciones")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @JsonBackReference("profesional-intervenciones")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id", nullable = false)
    private Profesional profesional;

    @Column(name = "fecha_de_intervencion", nullable = false)
    private LocalDate fechaDeIntervencion;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String intervencion;


}