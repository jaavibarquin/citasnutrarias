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

	// Libres
	public List<Cita> citasLibresPorAreaYFecha(AreaCita area, String fecha) {
		return repository.findByAreaAndFechaAndDisponibleTrue(area, fecha);
	}

	public List<Cita> citasLibresPorArea(AreaCita area) {
		return repository.findByAreaAndDisponibleTrue(area);
	}
	
	// Globales
	public Cita citaPorAreaDiaYHora(AreaCita area, String fecha, String hora) {
		return repository.findByAreaAndFechaAndHora(area, fecha, hora);
	}
	
	public List<Cita> citasPorAreaYFecha(AreaCita area, String fecha){
		return repository.findByAreaAndFecha(area, fecha);
	}
	
	public List<Cita> citasPorArea(AreaCita area) {
		return repository.findByArea(area);
	}

	public Cita citaPorId(String id) {
		return repository.findById(id).orElse(null);
	}


	public List<Cita> getAllCitas() {
		return repository.findAll();
	}


//	public List<Cita> citaPorIdCliente(String telefono) {
//		return repository.findByTelefono(telefono);
//	}


	public Cita modificaCita(Cita cita) {
		return repository.save(cita);
	}
	

	

}
