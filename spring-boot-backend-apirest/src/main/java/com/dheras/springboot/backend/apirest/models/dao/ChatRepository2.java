package com.dheras.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dheras.springboot.backend.apirest.models.entity.Mensaje2;

public interface ChatRepository2 extends MongoRepository<Mensaje2, String>{
	
    public List<Mensaje2> findFirst10ByOrderByFechaDesc();
}
