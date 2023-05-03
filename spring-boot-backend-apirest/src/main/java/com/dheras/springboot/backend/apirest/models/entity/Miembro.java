package com.dheras.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="miembros")
public class Miembro implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="no puede estar vacío")
	@Size(min=4, max=20, message="el tamaño tiene que estar entre 4 y 20 caracteres")
	@Column(nullable=false)
	private String nombre;
	
	@NotEmpty(message="no puede estar vacío")
	@Size(min=2, max=50, message="el tamaño tiene que estar entre 2 y 50 caracteres")
	private String apellido;
	
	@NotEmpty(message="no puede estar vacío")
	@Email(message="no es una dirección de correo con formato válido")
	@Column(nullable=false, unique=true)
	private String email;
	
	@NotEmpty(message="no puede estar vacío")
	@Size(min=4, max=20, message="el tamaño tiene que estar entre 4 y 20 caracteres")
	@Column(nullable=false)
	private String cargo;

	@NotEmpty(message="no puede estar vacío")
	@Size(min=2, max=50, message="el tamaño tiene que estar entre 2 y 50 caracteres")
	private String proyecto;
	
	@NotNull(message="no puede estar vacío")
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	private String foto;
	
	@JsonIgnoreProperties(value={"miembro", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "miembro", cascade = CascadeType.ALL)
	private List<Tarea> tareas;
	
	@JsonIgnoreProperties(value={"miembro", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "miembro", cascade = CascadeType.ALL)
	private List<Subtarea> subtareas;

	public Miembro() {
		this.tareas = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	public List<Subtarea> getSubtareas() {
		return subtareas;
	}

	public void setSubtareas(List<Subtarea> subtareas) {
		this.subtareas = subtareas;
	}

}
