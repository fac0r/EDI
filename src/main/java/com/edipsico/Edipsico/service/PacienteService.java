package com.edipsico.Edipsico.service;

import com.edipsico.Edipsico.model.Paciente;
import com.edipsico.Edipsico.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> obtenerPorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    public Paciente guardar(Paciente paciente) {
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + paciente.getDni());
        }
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizar(Integer id, Paciente pacienteActualizado) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));

        paciente.setNombre(pacienteActualizado.getNombre());
        paciente.setApellido(pacienteActualizado.getApellido());
        paciente.setDni(pacienteActualizado.getDni());
        paciente.setBarrio(pacienteActualizado.getBarrio());
        paciente.setCalle(pacienteActualizado.getCalle());
        paciente.setNumeroDeCasa(pacienteActualizado.getNumeroDeCasa());
        paciente.setTelDeContacto(pacienteActualizado.getTelDeContacto());

        // Campos nuevos
        paciente.setNacionalidad(pacienteActualizado.getNacionalidad());
        paciente.setEdad(pacienteActualizado.getEdad());
        paciente.setLocalidad(pacienteActualizado.getLocalidad());
        paciente.setEstadoCivil(pacienteActualizado.getEstadoCivil());
        paciente.setNumeroDeHijos(pacienteActualizado.getNumeroDeHijos());
        paciente.setTrabaja(pacienteActualizado.getTrabaja());
        paciente.setOcupacion(pacienteActualizado.getOcupacion());

        return pacienteRepository.save(paciente);
    }

    public void eliminar(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> obtenerPorProfesionalId(Integer profesionalId) {
        return pacienteRepository.findByProfesionalId(profesionalId);
    }
}