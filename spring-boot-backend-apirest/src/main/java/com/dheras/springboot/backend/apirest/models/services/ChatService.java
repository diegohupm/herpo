package com.dheras.springboot.backend.apirest.models.services;

import java.util.List;

import com.dheras.springboot.backend.apirest.models.entity.Mensaje;
import com.dheras.springboot.backend.apirest.models.entity.Mensaje2;

public interface ChatService {

	public List<Mensaje> obtenerUltimos10Mensajes1();
	public Mensaje guardar1(Mensaje mensaje);
	public List<Mensaje2> obtenerUltimos10Mensajes2();
	public Mensaje2 guardar2(Mensaje2 mensaje);
}
