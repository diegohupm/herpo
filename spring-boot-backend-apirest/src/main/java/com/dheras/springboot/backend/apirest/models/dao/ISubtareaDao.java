package com.dheras.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dheras.springboot.backend.apirest.models.entity.Subtarea;

public interface ISubtareaDao extends CrudRepository<Subtarea, Long>{
	//Subtareas
	
	@Query("select s from Subtarea s where s.descripcion like %?1%")
	public List<Subtarea> findByDesc(String term);
}
