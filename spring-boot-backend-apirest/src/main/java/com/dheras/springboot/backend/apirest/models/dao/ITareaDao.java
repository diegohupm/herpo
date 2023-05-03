package com.dheras.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dheras.springboot.backend.apirest.models.entity.Tarea;

public interface ITareaDao extends CrudRepository<Tarea, Long>{
	//Tareas
}
