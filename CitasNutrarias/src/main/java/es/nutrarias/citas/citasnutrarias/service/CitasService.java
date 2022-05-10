package es.nutrarias.citas.citasnutrarias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;
import es.nutrarias.citas.citasnutrarias.repository.CitasRepository;

@Service
public class CitasService {

	@Autowired
	private CitasRepository repository;


	public List<Cita> citasLibresPorAreaYDia(AreaCita area, String dia) {
		return repository.findByAreaAndFechaAndEmailIsNull(area, dia);
	}


	public List<Cita> citasLibresPorArea(AreaCita area) {
		return repository.findByArea(area);
	}


	public Cita citaPorId(String id) {
		return repository.findById(id).get();
	}


	public List<Cita> getAllCitas() {
		return repository.findAll();
	}


	public Cita citaPorTelefono(String telefono) {
		return repository.findByTelefono(telefono);
	}


	public Cita modificaCita(Cita cita) {
		// TODO Auto-generated method stub
		return null;
	}

}
