package com.edipsico.Edipsico.repository;

import com.edipsico.Edipsico.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Integer> {
    Optional<Profesional> findByMatricula(String matricula);
    Optional<Profesional> findByEmail(String email);
    boolean existsByMatricula(String matricula);
    boolean existsByEmail(String email);
}