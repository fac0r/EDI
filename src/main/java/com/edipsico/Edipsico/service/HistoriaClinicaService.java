package com.edipsico.Edipsico.service;

import com.edipsico.Edipsico.model.HistoriaClinica;
import com.edipsico.Edipsico.model.Paciente;
import com.edipsico.Edipsico.repository.HistoriaClinicaRepository;
import com.edipsico.Edipsico.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final PacienteRepository pacienteRepository;

    public HistoriaClinicaService(HistoriaClinicaRepository historiaClinicaRepository,
                                  PacienteRepository pacienteRepository) {
        this.historiaClinicaRepository = historiaClinicaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Optional<HistoriaClinica> obtenerPorPacienteId(Integer pacienteId) {
        return historiaClinicaRepository.findByPacienteId(pacienteId);
    }

    public HistoriaClinica guardar(Integer pacienteId, HistoriaClinica historiaClinica) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + pacienteId));
        historiaClinica.setPaciente(paciente);
        return historiaClinicaRepository.save(historiaClinica);
    }

    public HistoriaClinica actualizar(Integer pacienteId, HistoriaClinica historiaActualizada) {
        HistoriaClinica historia = historiaClinicaRepository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada para paciente id: " + pacienteId));

        historia.setInformacionDeAcceso(historiaActualizada.getInformacionDeAcceso());
        historia.setDatosSociodemograficos(historiaActualizada.getDatosSociodemograficos());
        historia.setHistoriaClinica(historiaActualizada.getHistoriaClinica());

        return historiaClinicaRepository.save(historia);
    }
}