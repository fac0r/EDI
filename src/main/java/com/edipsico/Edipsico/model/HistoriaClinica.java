package com.edipsico.Edipsico.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HistoriaClinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference("paciente-historia")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "informacion_de_acceso", columnDefinition = "TEXT")
    private String informacionDeAcceso;

    @Column(name = "datos_sociodemograficos", columnDefinition = "TEXT")
    private String datosSociodemograficos;

    @Column(name = "historia_clinica", columnDefinition = "TEXT")
    private String historiaClinica;
}