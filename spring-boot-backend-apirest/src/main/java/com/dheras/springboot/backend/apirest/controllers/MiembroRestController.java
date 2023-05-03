package com.dheras.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dheras.springboot.backend.apirest.models.entity.Miembro;
import com.dheras.springboot.backend.apirest.models.entity.Subtarea;
import com.dheras.springboot.backend.apirest.models.entity.Tarea;
import com.dheras.springboot.backend.apirest.models.services.IMiembroService;
import com.dheras.springboot.backend.apirest.models.services.IUploadFileService;

@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class MiembroRestController {

	@Autowired
	private IMiembroService miembroService;
	
	@Autowired
	private IUploadFileService uploadService;
	
	@GetMapping("/miembros")
	public List<Miembro> index(){
		return miembroService.findAll();
	}
	
	@GetMapping("/miembros/page/{page}")
	public Page<Miembro> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return miembroService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_USER"})
	@GetMapping("/miembros/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Miembro miembro = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			miembro = miembroService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(miembro==null) {
			response.put("mensaje", "El miembro ID:".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Miembro>(miembro, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/miembros")
	public ResponseEntity<?> create(@Valid @RequestBody Miembro  miembro, BindingResult result) {
		Miembro miembroNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try{
			miembroNew = miembroService.save(miembro);
		}
		catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El miembro ha sido creado con éxito");
		response.put("miembro", miembroNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/miembros/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Miembro miembro, BindingResult result, @PathVariable Long id) {
		Miembro miembroUpdated = null;
		Miembro miembroActual = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			miembroActual = miembroService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(miembroActual==null) {
			response.put("mensaje", "Error: no se pudo editar, el miembro ID:".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			miembroActual.setNombre(miembro.getNombre());
			miembroActual.setApellido(miembro.getApellido());
			miembroActual.setEmail(miembro.getEmail());
			miembroActual.setCargo(miembro.getCargo());
			miembroActual.setProyecto(miembro.getProyecto());
			miembroActual.setCreateAt(miembro.getCreateAt());
			miembroUpdated = miembroService.save(miembroActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El miembro ha sido actualizado con éxito");
		response.put("miembro", miembroUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/miembros/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Miembro miembro = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			miembro = miembroService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try{
			String nombreFotoAnterior = miembro.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			miembroService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el miembro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El miembro ha sido eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_BOSS", "ROLE_USER"})
	@PostMapping("/miembros/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {

		Miembro miembro = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			miembro = miembroService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!archivo.isEmpty()) {
			
			String nombreArchivo = null;
			
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del miembro");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = miembro.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			miembro.setFoto(nombreArchivo);
			miembroService.save(miembro);
			response.put("miembro", miembro);
			response.put("mensaje", "Ha subido correctamente la imagen: "+nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("miembros/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		Resource recurso = null;
		
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/miembros/tareas")
	public List<Tarea> listarTareas(){
		return miembroService.findAllTareas();
	}
	
}
