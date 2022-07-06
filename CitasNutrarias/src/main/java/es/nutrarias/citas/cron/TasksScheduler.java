package es.nutrarias.citas.cron;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.nutrarias.citas.service.CitasService;

@Component
public class TasksScheduler {
	
	@Autowired
	private CitasService citasSvc;

	@PostConstruct
	public void desactivaCitasPasadas() {
		this.desactivaCitasPrevias();
	}
	
	@Scheduled(cron = "@daily")
	public void creaCitas() {
		LocalDate fecha = LocalDate.now();
		LocalDate fecha3meses = fecha.plusMonths(3).plusDays(1);
		this.citasSvc.addCitasLibres(fecha3meses);
	}
	
	@Scheduled(cron="@daily")
	public void desactivaCitasPrevias() {
		LocalDateTime fecha = LocalDateTime.now();
		this.citasSvc.desactivaCitasPrevias(fecha);
	}
	
	@Scheduled(cron = "0 1,31 * ? * *")
	public void desactivaCitasProximas() {
		LocalDateTime fecha = LocalDateTime.now();
		this.citasSvc.desactivaCitasProximas(fecha);
	}
	
}
