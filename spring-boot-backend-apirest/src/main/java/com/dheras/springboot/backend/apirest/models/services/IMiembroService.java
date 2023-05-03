package com.dheras.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dheras.springboot.backend.apirest.models.entity.Miembro;
import com.dheras.springboot.backend.apirest.models.entity.Subtarea;
import com.dheras.springboot.backend.apirest.models.entity.Tarea;

public interface IMiembroService {
	
	public List<Miembro> findAll();
	
	public Page<Miembro> findAll(Pageable pageable);
	
	public Miembro findById(Long id);
	
	public Miembro save(Miembro miembro);
	
	public void delete(Long id);

	public Tarea findTareaById(Long id);
	
	public List<Tarea> findAllTareas();
	
	public Tarea saveTarea(Tarea tarea);
	
	public void deleteTareaById(Long id);
	
	public Subtarea findSubtareaById(Long id);
	
	public List<Subtarea> findAllSubtareas();

	public Subtarea saveSubtarea(Subtarea subtarea);

	public void deleteSubtareaById(Long id);

	public List<Subtarea> findSubtareaByDesc(String term);
}