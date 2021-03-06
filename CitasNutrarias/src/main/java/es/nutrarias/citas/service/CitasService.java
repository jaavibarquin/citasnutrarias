package es.nutrarias.citas.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.entities.AreaCita;
import es.nutrarias.citas.entities.Cita;
import es.nutrarias.citas.entities.Cliente;
import es.nutrarias.citas.repository.CitasRepository;

@Service
public class CitasService {

	@Autowired
	private CitasRepository citasRepo;

	@Autowired
	private EmailSenderService emailService;

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

	public List<Cita> citasPorAreaYFecha(AreaCita area, String fecha) {
		return citasRepo.findByAreaAndFechaAndClienteNotNull(area, fecha);
	}

	public List<Cita> citasPorArea(AreaCita area) {
		return citasRepo.findByAreaAndClienteNotNull(area);
	}

	// public List<Cita> citasPorCliente(String idCliente) {
	// return citasRepo.findByIdCliente(idCliente);
	// }

	public Cita modificaCita(Cita cita) {
		if (cita != null) {
			Cliente cli = cita.getCliente();
			if (cli != null) {
				cita.setDisponible(false);
				try {
					emailService.enviaEmail(cita.getCliente().getEmail(), cita);
				} catch (UnsupportedEncodingException | MessagingException e) {
					System.out.println("Error al enviar el correo");
					e.printStackTrace();
					return null;
				}
			}
			return citasRepo.save(cita);
		} else
			return null;
	}

	public void addCitasLibres(LocalDate fecha) {
		for (AreaCita area : AreaCita.values()) {
			this.citasRepo.addCitasLibres(fecha.toString(), area.toString());
		}

	}

	public void desactivaCitasPrevias(LocalDateTime fecha) {
		System.out.println("Desactivar citas previas a fecha: " + fecha.toString());
		this.citasRepo.desactivaCitasPrevias(fecha.toString());

	}

	public void desactivaCitasProximas(LocalDateTime fecha) {
		System.out.println("Desactivar citas proximas a fecha: " + fecha.toString());
		this.citasRepo.desactivaCitasProximas(fecha.toString());

	}

	// public boolean existeCliente(String idCliente) {
	// return (!this.citasRepo.findByIdCliente(idCliente).isEmpty());
	// }

}
