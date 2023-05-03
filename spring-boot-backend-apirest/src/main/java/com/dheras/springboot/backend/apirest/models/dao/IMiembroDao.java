package com.dheras.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dheras.springboot.backend.apirest.models.entity.Miembro;
import com.dheras.springboot.backend.apirest.models.entity.Subtarea;
import com.dheras.springboot.backend.apirest.models.entity.Tarea;

public interface IMiembroDao extends JpaRepository<Miembro, Long>{

	@Query("from Tarea")
	public List<Tarea> findAllTareas();
	
	@Query("from Subtarea")
	public List<Subtarea> findAllSubtareas();
}
