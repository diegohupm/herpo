package com.dheras.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dheras.springboot.backend.apirest.models.dao.IMiembroDao;
import com.dheras.springboot.backend.apirest.models.dao.ISubtareaDao;
import com.dheras.springboot.backend.apirest.models.dao.ITareaDao;
import com.dheras.springboot.backend.apirest.models.entity.Miembro;
import com.dheras.springboot.backend.apirest.models.entity.Producto;
import com.dheras.springboot.backend.apirest.models.entity.Subtarea;
import com.dheras.springboot.backend.apirest.models.entity.Tarea;

@Service
public class MiembroServiceImpl implements IMiembroService{

	@Autowired
	private IMiembroDao miembroDao;
	
	@Autowired
	private ITareaDao tareaDao;
	
	@Autowired
	private ISubtareaDao subtareaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Miembro> findAll() {
		return (List<Miembro>) miembroDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Miembro> findAll(Pageable pageable) {
		return miembroDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Miembro findById(Long id) {
		return miembroDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Miembro save(Miembro miembro) {
		return miembroDao.save(miembro);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		miembroDao.deleteById(id);	
	}
	
	@Override
	public Tarea findTareaById(Long id) {
		return tareaDao.findById(id).orElse(null);
	}

	@Override
	public Tarea saveTarea(Tarea tarea) {
		return tareaDao.save(tarea);
	}

	@Override
	public void deleteTareaById(Long id) {
		tareaDao.deleteById(id);
	}

	@Override
	public Subtarea findSubtareaById(Long id) {
		return subtareaDao.findById(id).orElse(null);
	}
	
	@Override
	public Subtarea saveSubtarea(Subtarea subtarea) {
		return subtareaDao.save(subtarea);
	}

	@Override
	public void deleteSubtareaById(Long id) {
		subtareaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tarea> findAllTareas() {
		return miembroDao.findAllTareas();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Subtarea> findAllSubtareas() {
		return miembroDao.findAllSubtareas();
	}

	@Override
	public List<Subtarea> findSubtareaByDesc(String term) {
		return subtareaDao.findByDesc(term);
	}

}
