package com.edipsico.Edipsico.service;

import com.edipsico.Edipsico.model.Profesional;
import com.edipsico.Edipsico.repository.ProfesionalRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfesionalService(ProfesionalRepository profesionalRepository,
                              PasswordEncoder passwordEncoder) {
        this.profesionalRepository = profesionalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Profesional> obtenerTodos() {
        return profesionalRepository.findAll();
    }

    public Optional<Profesional> obtenerPorId(Integer id) {
        return profesionalRepository.findById(id);
    }

    public Profesional guardar(Profesional profesional) {
        System.out.println("Intentando guardar: " + profesional.getEmail() + " / " + profesional.getMatricula());

        if (profesionalRepository.existsByMatricula(profesional.getMatricula())) {
            System.out.println("CONFLICTO: matrícula ya existe");
            throw new RuntimeException("Ya existe un profesional con la matrícula: " + profesional.getMatricula());
        }
        if (profesionalRepository.existsByEmail(profesional.getEmail())) {
            System.out.println("CONFLICTO: email ya existe");
            throw new RuntimeException("Ya existe un profesional con el email: " + profesional.getEmail());
        }

        profesional.setPassword(passwordEncoder.encode(profesional.getPassword()));

        Profesional guardado = profesionalRepository.save(profesional);

        return guardado;
    }

    public Profesional actualizar(Integer id, Profesional profesionalActualizado) {
        Profesional profesional = profesionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado con id: " + id));

        profesional.setNombre(profesionalActualizado.getNombre());
        profesional.setApellido(profesionalActualizado.getApellido());
        profesional.setMatricula(profesionalActualizado.getMatricula());
        profesional.setEspecialidad(profesionalActualizado.getEspecialidad());
        profesional.setTelDeContacto(profesionalActualizado.getTelDeContacto());
        profesional.setEmail(profesionalActualizado.getEmail());
        profesional.setRol(profesionalActualizado.getRol());

        // Solo re-encriptamos si mandan un password nuevo
        if (profesionalActualizado.getPassword() != null &&
                !profesionalActualizado.getPassword().isEmpty()) {
            profesional.setPassword(passwordEncoder.encode(profesionalActualizado.getPassword()));
        }

        return profesionalRepository.save(profesional);
    }

    public void eliminar(Integer id) {
        if (!profesionalRepository.existsById(id)) {
            throw new RuntimeException("Profesional no encontrado con id: " + id);
        }
        profesionalRepository.deleteById(id);
    }
}