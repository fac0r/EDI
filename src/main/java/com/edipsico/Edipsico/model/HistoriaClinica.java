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
@Table(name = "historia_clinica")
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

    // --- IDENTIFICACIÓN ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference("paciente-historia")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    // --- DATOS DE CONTACTO Y SOCIODEMOGRÁFICOS ---

    @Column(name = "localidad_barrio")
    private String localidadBarrio;

    @Column(name = "tel_de_contacto")
    private String telDeContacto;

    private String nacionalidad;

    @Column(name = "fecha_de_nacimiento")
    private LocalDate fechaDeNacimiento;

    private Integer edad;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "numero_de_hijos")
    private Integer numeroDeHijos;

    /** Valores esperados: "Si" / "No" */
    private String trabaja;

    private String ocupacion;

    // --- DATOS DE SALUD Y COBERTURA ---

    @Column(name = "obra_social")
    private String obraSocial;

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
    private String fechaUltimosChequeosMedicos;

    @Column(name = "toma_alguna_medicacion", columnDefinition = "TEXT")
    private String tomaAlgunaMedicacion;

    @Column(name = "consulto_con_psicologo_antes")
    private String consultoConPsicologoAntes;

    @Column(name = "motivo_consulta_anterior", columnDefinition = "TEXT")
    private String motivoConsultaAnterior;

    @Column(name = "se_atiende_con_otro_profesional")
    private String seAtiendeConOtroProfesionalDeLaSalud;

    // --- DATOS DE CONSULTA EN PSICOENACCIÓN ---

    @Column(name = "dispositivo_lugar")
    private String dispositivoLugar;

    @Column(name = "motivo_consulta_psicoenaccion")
    private String motivoConsultaPsicoenaccion;

    @Column(name = "breve_resena_del_caso", columnDefinition = "TEXT")
    private String breveResenaDelCaso;

}
