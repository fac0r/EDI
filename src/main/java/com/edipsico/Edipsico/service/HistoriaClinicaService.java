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

        // Datos de contacto y sociodemográficos
        historia.setLocalidadBarrio(historiaActualizada.getLocalidadBarrio());
        historia.setTelDeContacto(historiaActualizada.getTelDeContacto());
        historia.setNacionalidad(historiaActualizada.getNacionalidad());
        historia.setFechaDeNacimiento(historiaActualizada.getFechaDeNacimiento());
        historia.setEdad(historiaActualizada.getEdad());
        historia.setEstadoCivil(historiaActualizada.getEstadoCivil());
        historia.setNumeroDeHijos(historiaActualizada.getNumeroDeHijos());
        historia.setTrabaja(historiaActualizada.getTrabaja());
        historia.setOcupacion(historiaActualizada.getOcupacion());

        // Datos de salud y cobertura
        historia.setObraSocial(historiaActualizada.getObraSocial());
        historia.setPercibeplanAsistenciaSocial(historiaActualizada.getPercibeplanAsistenciaSocial());
        historia.setSituacionLaboral(historiaActualizada.getSituacionLaboral());
        historia.setNivelDeEducacion(historiaActualizada.getNivelDeEducacion());
        historia.setTituloObtenido(historiaActualizada.getTituloObtenido());
        historia.setFechaUltimosChequeosMedicos(historiaActualizada.getFechaUltimosChequeosMedicos());
        historia.setTomaAlgunaMedicacion(historiaActualizada.getTomaAlgunaMedicacion());
        historia.setConsultoConPsicologoAntes(historiaActualizada.getConsultoConPsicologoAntes());
        historia.setMotivoConsultaAnterior(historiaActualizada.getMotivoConsultaAnterior());
        historia.setSeAtiendeConOtroProfesionalDeLaSalud(historiaActualizada.getSeAtiendeConOtroProfesionalDeLaSalud());

        // Datos de consulta en PsicoEnAcción
        historia.setDispositivoLugar(historiaActualizada.getDispositivoLugar());
        historia.setMotivoConsultaPsicoenaccion(historiaActualizada.getMotivoConsultaPsicoenaccion());
        historia.setBreveResenaDelCaso(historiaActualizada.getBreveResenaDelCaso());

        return historiaClinicaRepository.save(historia);
    }
}
