package es.nutrarias.citas.citasnutrarias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;
import es.nutrarias.citas.citasnutrarias.entities.Cliente;
import es.nutrarias.citas.citasnutrarias.repository.CitasRepository;
import es.nutrarias.citas.citasnutrarias.repository.ClientesRepository;

@Service
public class CitasService {

	@Autowired
	private CitasRepository citasRepo;

	@Autowired
	private ClientesRepository clientesRepo;

	// Libres
	public List<Cita> citasLibresPorAreaYFecha(AreaCita area, String fecha) {
		return citasRepo.findByAreaAndFechaAndDisponibleTrue(area, fecha);
	}

	public List<Cita> citasLibresPorArea(AreaCita area) {
		return citasRepo.findByAreaAndDisponibleTrue(area);
	}

	// Globales
	public Cita citaPorAreaDiaYHora(AreaCita area, String fecha, String hora) {
		return citasRepo.findByAreaAndFechaAndHora(area, fecha, hora);
	}

	public List<Cita> citasPorAreaYFecha(AreaCita area, String fecha){
		return citasRepo.findByAreaAndFecha(area, fecha);
	}

	public List<Cita> citasPorArea(AreaCita area) {
		return citasRepo.findByArea(area);
	}

	public Cita citaPorId(String id) {
		return citasRepo.findById(id).orElse(null);
	}


	public List<Cita> getAllCitas() {
		return citasRepo.findAll();
	}


	public List<Cita> citasPorCliente(String idCliente) {
		return citasRepo.findByIdCliente(idCliente);
	}


	public Cita modificaCita(Cita cita) {
		if (cita != null) {
			Cliente cli = cita.getCliente();
			if (cli != null) {
				if(!clientesRepo.existsById(cli.getTelefono())) {
					clientesRepo.save(cli);
				}
				cita.setDisponible(false);
			}
			return citasRepo.save(cita);
		} else return null;
	}

	// Cliente
	public Cliente buscaCliente(String idCliente) {
		if (idCliente == null) {
			return null;
		}
		return clientesRepo.findById(idCliente).orElse(null);
	}

	public Cliente modificaCliente(Cliente cliente) {
		if (cliente != null) {
			return clientesRepo.save(cliente);
		} else return null;
	}

	public Cliente anhadeCliente(Cliente cliente) {
		if (cliente != null) {
			return clientesRepo.save(cliente);
		} else return null;
	}




}
