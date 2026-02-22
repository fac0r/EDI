package com.edipsico.Edipsico.controller;

import com.edipsico.Edipsico.model.Intervencion;
import com.edipsico.Edipsico.service.IntervencionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intervenciones")
public class IntervencionController {

    private final IntervencionService intervencionService;


    public IntervencionController(IntervencionService intervencionService) {
        this.intervencionService = intervencionService;
    }

    @GetMapping
    public ResponseEntity<List<Intervencion>> obtenerTodas() {
        return ResponseEntity.ok(intervencionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intervencion> obtenerPorId(@PathVariable Integer id) {
        return intervencionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Intervencion>> obtenerPorPacienteId(@PathVariable Integer pacienteId) {
        return ResponseEntity.ok(intervencionService.obtenerPorPacienteId(pacienteId));
    }

    @GetMapping("/profesional/{profesionalId}")
    public ResponseEntity<List<Intervencion>> obtenerPorProfesionalId(@PathVariable Integer profesionalId) {
        return ResponseEntity.ok(intervencionService.obtenerPorProfesionalId(profesionalId));
    }

    @PostMapping("/paciente/{pacienteId}/profesional/{profesionalId}")
    public ResponseEntity<Intervencion> guardar(@PathVariable Integer pacienteId,
                                                @PathVariable Integer profesionalId,
                                                @RequestBody Intervencion intervencion) {
        try {
            Intervencion nueva = intervencionService.guardar(pacienteId, profesionalId, intervencion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intervencion> actualizar(@PathVariable Integer id,
                                                   @RequestBody Intervencion intervencion) {
        try {
            return ResponseEntity.ok(intervencionService.actualizar(id, intervencion));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            intervencionService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}