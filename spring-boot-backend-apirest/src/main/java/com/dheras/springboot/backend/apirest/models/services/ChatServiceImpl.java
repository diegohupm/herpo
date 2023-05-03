package com.dheras.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dheras.springboot.backend.apirest.models.dao.ChatRepository;
import com.dheras.springboot.backend.apirest.models.dao.ChatRepository2;
import com.dheras.springboot.backend.apirest.models.entity.Mensaje;
import com.dheras.springboot.backend.apirest.models.entity.Mensaje2;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatRepository chatDao1;
	
	@Autowired
	private ChatRepository2 chatDao2;

	@Override
	public List<Mensaje> obtenerUltimos10Mensajes1() {
		return chatDao1.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje guardar1(Mensaje mensaje) {
		return chatDao1.save(mensaje);
	}
	
	@Override
	public List<Mensaje2> obtenerUltimos10Mensajes2() {
		return chatDao2.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje2 guardar2(Mensaje2 mensaje) {
		return chatDao2.save(mensaje);
	}

}
