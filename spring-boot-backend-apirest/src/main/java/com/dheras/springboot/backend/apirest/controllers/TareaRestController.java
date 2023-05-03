package com.dheras.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dheras.springboot.backend.apirest.models.entity.Producto;
import com.dheras.springboot.backend.apirest.models.entity.Subtarea;
import com.dheras.springboot.backend.apirest.models.entity.Tarea;
import com.dheras.springboot.backend.apirest.models.services.IMiembroService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TareaRestController {
	
	@Autowired
	private IMiembroService miembroService;
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_USER"})
	@GetMapping("/tareas/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Tarea show(@PathVariable Long id) {
		return miembroService.findTareaById(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS"})
	@DeleteMapping("/tareas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		miembroService.deleteTareaById(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_USER"})
	@PostMapping("/tareas")
	@ResponseStatus(HttpStatus.CREATED)
	public Tarea crear(@RequestBody Tarea tarea) {
//		if(tarea.getMiembro().getTareas()!=null) {
//			miembroService.deleteTareaById(tarea.getMiembro().getTareas().getId());
//		}
		if(tarea.getEstado()==null) {
			tarea.setEstado("To do");
		}
		return miembroService.saveTarea(tarea);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS"})
	@GetMapping("/tareas/subtareas/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Subtarea showSubtareas(@PathVariable Long id){
		return miembroService.findSubtareaById(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS"})
	@DeleteMapping("/tareas/subtareas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSubtareas(@PathVariable Long id) {
		miembroService.deleteSubtareaById(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_USER"})
	@PostMapping("/tareas/subtareas")
	@ResponseStatus(HttpStatus.CREATED)
	public Subtarea crearSubtareas(@RequestBody Subtarea subtarea) {
		if(subtarea.getEstado()==null) {
			subtarea.setEstado("To do");
		}
		return miembroService.saveSubtarea(subtarea);
	}
	
	@GetMapping("/tareas/subtareas")
	public List<Subtarea> listarSubtareas(){
		return miembroService.findAllSubtareas();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS"})
	@GetMapping("/tareas/filtrar-subtareas/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<Subtarea> filtrarProductos(@PathVariable String term){
		return miembroService.findSubtareaByDesc(term);
	}
}
