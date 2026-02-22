package com.edipsico.Edipsico.controller;

import com.edipsico.Edipsico.model.HistoriaClinica;
import com.edipsico.Edipsico.service.HistoriaClinicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historias-clinicas")
public class HistoriaClinicaController {

    private final HistoriaClinicaService historiaClinicaService;

    public HistoriaClinicaController(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> obtenerPorPacienteId(@PathVariable Integer pacienteId) {
        return historiaClinicaService.obtenerPorPacienteId(pacienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> guardar(@PathVariable Integer pacienteId,
                                                   @RequestBody HistoriaClinica historiaClinica) {
        try {
            HistoriaClinica nueva = historiaClinicaService.guardar(pacienteId, historiaClinica);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> actualizar(@PathVariable Integer pacienteId,
                                                      @RequestBody HistoriaClinica historiaClinica) {
        try {
            return ResponseEntity.ok(historiaClinicaService.actualizar(pacienteId, historiaClinica));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}