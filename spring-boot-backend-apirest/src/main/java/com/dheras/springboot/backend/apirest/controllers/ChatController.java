package com.dheras.springboot.backend.apirest.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.dheras.springboot.backend.apirest.models.entity.Mensaje;
import com.dheras.springboot.backend.apirest.models.entity.Mensaje2;
import com.dheras.springboot.backend.apirest.models.services.ChatService;

@Controller
public class ChatController {

	private String[] colores = {"red", "green", "blue", "magenta", "purple", "orange"};
	
	@Autowired
	private ChatService chatService1, chatService2;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@MessageMapping("/1/mensaje")
	@SendTo("/chat/1/mensaje")
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("nuevo usuario");
		} else {
			chatService1.guardar1(mensaje);
		}
		
		return mensaje;
	}

	@MessageMapping("/1/escribiendo")
	@SendTo("/chat/1/escribiendo")
	public String estaEscribiendo(@PathVariable String proyecto, String username) {
		return username.concat(" está escribiendo ...");
	}
	
	@MessageMapping("/1/historial")
	public void historial(@PathVariable String proyecto, String userId){

		webSocket.convertAndSend("/chat/1/historial/" + userId, chatService1.obtenerUltimos10Mensajes1());
	}
	
	@MessageMapping("/2/mensaje")
	@SendTo("/chat/2/mensaje")
	public Mensaje2 recibeMensaje2(Mensaje2 mensaje) {
		mensaje.setFecha(new Date().getTime());
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("nuevo usuario");
		} else {
			chatService2.guardar2(mensaje);
		}
		
		return mensaje;
	}

	@MessageMapping("/2/escribiendo")
	@SendTo("/chat/2/escribiendo")
	public String estaEscribiendo2(@PathVariable String proyecto, String username) {
		return username.concat(" está escribiendo ...");
	}
	
	@MessageMapping("/2/historial")
	public void historial2(@PathVariable String proyecto, String userId){
		webSocket.convertAndSend("/chat/2/historial/" + userId, chatService2.obtenerUltimos10Mensajes2());
	}

}
