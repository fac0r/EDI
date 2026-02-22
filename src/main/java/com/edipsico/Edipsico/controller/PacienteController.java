package com.edipsico.Edipsico.controller;

import com.edipsico.Edipsico.model.Intervencion;
import com.edipsico.Edipsico.model.Paciente;
import com.edipsico.Edipsico.model.Profesional;
import com.edipsico.Edipsico.repository.IntervencionRepository;
import com.edipsico.Edipsico.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    private final IntervencionRepository intervencionRepository;

    public PacienteController(PacienteService pacienteService,
                              IntervencionRepository intervencionRepository) {
        this.pacienteService = pacienteService;
        this.intervencionRepository = intervencionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerTodos() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Integer id) {
        return pacienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Paciente> obtenerPorDni(@PathVariable String dni) {
        return pacienteService.obtenerPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        try {
            Paciente nuevo = pacienteService.guardar(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Integer id,
                                               @RequestBody Paciente paciente) {
        try {
            return ResponseEntity.ok(pacienteService.actualizar(id, paciente));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            pacienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mis-pacientes")
    public ResponseEntity<List<Paciente>> obtenerMisPacientes(
            @RequestParam Integer profesionalId) {
        return ResponseEntity.ok(pacienteService.obtenerPorProfesionalId(profesionalId));
    }

    @GetMapping("/{id}/profesionales")

    public ResponseEntity<List<Map<String, Object>>> obtenerProfesionalesDelPaciente(@PathVariable Integer id) {
        List<Profesional> profesionales = intervencionRepository.findProfesionalesByPacienteId(id);

        List<Map<String, Object>> resultado = profesionales.stream()
                .map(p -> Map.<String, Object>of(
                        "id", p.getId(),
                        "nombre", p.getNombre(),
                        "apellido", p.getApellido(),
                        "matricula", p.getMatricula() != null ? p.getMatricula() : "—",
                        "especialidad", p.getEspecialidad() != null ? p.getEspecialidad() : "—"
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }
}