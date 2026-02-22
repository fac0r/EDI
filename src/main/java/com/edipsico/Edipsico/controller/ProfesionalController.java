package com.edipsico.Edipsico.controller;

import com.edipsico.Edipsico.model.Profesional;
import com.edipsico.Edipsico.service.ProfesionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {

    private final ProfesionalService profesionalService;

    public ProfesionalController(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }

    @GetMapping
    public ResponseEntity<List<Profesional>> obtenerTodos() {
        return ResponseEntity.ok(profesionalService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesional> obtenerPorId(@PathVariable Integer id) {
        return profesionalService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profesional> guardar(@RequestBody Profesional profesional) {
        try {
            Profesional nuevo = profesionalService.guardar(profesional);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesional> actualizar(@PathVariable Integer id,
                                                  @RequestBody Profesional profesional) {
        try {
            return ResponseEntity.ok(profesionalService.actualizar(id, profesional));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            profesionalService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}