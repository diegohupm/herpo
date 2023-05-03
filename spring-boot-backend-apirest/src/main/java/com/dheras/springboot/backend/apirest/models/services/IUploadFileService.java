package com.dheras.springboot.backend.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	public Resource cargar(String nombreFoto) throws MalformedURLException;
	public String copiar(MultipartFile archivo) throws IOException;
	public boolean eliminar(String nombreFoto);
	public Path getPath(String nombreFoto);
//	public Resource cargarm(String nombreFoto) throws MalformedURLException;
//	public String copiarm(MultipartFile archivo) throws IOException;
//	public boolean eliminarm(String nombreFoto);
//	public Path getPathm(String nombreFoto);
}
