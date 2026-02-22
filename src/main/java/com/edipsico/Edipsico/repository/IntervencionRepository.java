package com.edipsico.Edipsico.repository;

import com.edipsico.Edipsico.model.Intervencion;
import com.edipsico.Edipsico.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IntervencionRepository extends JpaRepository<Intervencion, Integer> {
    List<Intervencion> findByPacienteId(Integer pacienteId);
    List<Intervencion> findByProfesionalId(Integer profesionalId);
    @Query("SELECT i FROM Intervencion i JOIN FETCH i.profesional WHERE i.paciente.id = :pacienteId")
    List<Intervencion> findByPacienteIdWithProfesional(@Param("pacienteId") Integer pacienteId);
    @Query("SELECT DISTINCT i.profesional FROM Intervencion i WHERE i.paciente.id = :pacienteId")
    List<Profesional> findProfesionalesByPacienteId(@Param("pacienteId") Integer pacienteId);
}