package com.edipsico.Edipsico.service;

import com.edipsico.Edipsico.model.Intervencion;
import com.edipsico.Edipsico.model.Paciente;
import com.edipsico.Edipsico.model.Profesional;
import com.edipsico.Edipsico.repository.IntervencionRepository;
import com.edipsico.Edipsico.repository.PacienteRepository;
import com.edipsico.Edipsico.repository.ProfesionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntervencionService {

    private final IntervencionRepository intervencionRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfesionalRepository profesionalRepository;

    public IntervencionService(IntervencionRepository intervencionRepository,
                               PacienteRepository pacienteRepository,
                               ProfesionalRepository profesionalRepository) {
        this.intervencionRepository = intervencionRepository;
        this.pacienteRepository = pacienteRepository;
        this.profesionalRepository = profesionalRepository;
    }

    public List<Intervencion> obtenerTodas() {
        return intervencionRepository.findAll();
    }

    public List<Intervencion> obtenerPorPacienteId(Integer pacienteId) {
        return intervencionRepository.findByPacienteId(pacienteId);
    }

    public List<Intervencion> obtenerPorProfesionalId(Integer profesionalId) {
        return intervencionRepository.findByProfesionalId(profesionalId);
    }

    public Optional<Intervencion> obtenerPorId(Integer id) {
        return intervencionRepository.findById(id);
    }

    public Intervencion guardar(Integer pacienteId, Integer profesionalId, Intervencion intervencion) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + pacienteId));
        Profesional profesional = profesionalRepository.findById(profesionalId)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado con id: " + profesionalId));

        intervencion.setPaciente(paciente);
        intervencion.setProfesional(profesional);

        return intervencionRepository.save(intervencion);
    }

    public Intervencion actualizar(Integer id, Intervencion intervencionActualizada) {
        Intervencion intervencion = intervencionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervención no encontrada con id: " + id));

        intervencion.setFechaDeIntervencion(intervencionActualizada.getFechaDeIntervencion());
        intervencion.setIntervencion(intervencionActualizada.getIntervencion());

        return intervencionRepository.save(intervencion);
    }

    public void eliminar(Integer id) {
        if (!intervencionRepository.existsById(id)) {
            throw new RuntimeException("Intervención no encontrada con id: " + id);
        }
        intervencionRepository.deleteById(id);
    }


}