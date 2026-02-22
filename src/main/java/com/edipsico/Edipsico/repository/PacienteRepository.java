package com.edipsico.Edipsico.repository;

import com.edipsico.Edipsico.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByDni(String dni);
    boolean existsByDni(String dni);

    @Query("SELECT DISTINCT p FROM Paciente p JOIN p.intervenciones i WHERE i.profesional.id = :profesionalId")
    List<Paciente> findByProfesionalId(@Param("profesionalId") Integer profesionalId);
}