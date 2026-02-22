package com.edipsico.Edipsico.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String dni;

    private String barrio;
    private String calle;

    @Column(name = "numero_de_casa")
    private String numeroDeCasa;

    @Column(name = "tel_de_contacto")
    private String telDeContacto;

    @JsonManagedReference("paciente-historia")
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HistoriaClinica historiaClinica;

    @JsonManagedReference("paciente-intervenciones")
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Intervencion> intervenciones;


}