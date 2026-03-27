package com.edipsico.Edipsico.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HistoriaClinica")
public class HistoriaClinica {

    // --- ENUMs internos ---

    public enum SituacionLaboral {
        EN_RELACION_DE_DEPENDENCIA,
        MONOTRIBUTISTA,
        TRABAJO_INFORMAL
    }

    public enum NivelDeEducacion {
        PRIMARIO_INCOMPLETO,
        PRIMARIO_COMPLETO,
        SECUNDARIO_INCOMPLETO,
        SECUNDARIO_COMPLETO,
        TERCIARIO_UNIVERSITARIO_INCOMPLETO,
        TERCIARIO_UNIVERSITARIO_COMPLETO
    }

    // --- CAMPOS EXISTENTES ---

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

    // --- CAMPOS NUEVOS ---

    @Column(name = "obra_social")
    private String obraSocial;

    /** Valores esperados: "Si" / "No" */
    @Column(name = "percibe_plan_asistencia_social")
    private String percibeplanAsistenciaSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacion_laboral")
    private SituacionLaboral situacionLaboral;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_de_educacion")
    private NivelDeEducacion nivelDeEducacion;

    @Column(name = "titulo_obtenido")
    private String tituloObtenido;

    @Column(name = "fecha_ultimos_chequeos_medicos")
    private LocalDate fechaUltimosChequeosMedicos;

    @Column(name = "toma_alguna_medicacion", columnDefinition = "TEXT")
    private String tomaAlgunaMedicacion;

    @Column(name = "consulto_con_psicologo_antes")
    private String consultoConPsicologoAntes;

    @Column(name = "motivo_consulta_anterior", columnDefinition = "TEXT")
    private String motivoConsultaAnterior;

    @Column(name = "se_atiende_con_otro_profesional")
    private String seAtiendeConOtroProfesionalDeLaSalud;

    // --- FIN CAMPOS NUEVOS ---
}