package es.nutrarias.citas.citasnutrarias.cron;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import es.nutrarias.citas.citasnutrarias.service.CitasService;
@Component
public class TasksScheduler {
	
	@Autowired
	private CitasService citasSvc;

//	@Scheduled(cron = "*/10 * * * * *")
//	public void desactivaCitas() {
//		long now = System.currentTimeMillis()/1000;
//		LocalDateTime ahora = LocalDateTime.now();
//		System.out.println("Desactiva tareas cuya fecha sea mas tarde de: " + ahora.format(DateTimeFormatter.ISO_DATE_TIME));
//		System.out.println(": " + now);
//	}
	
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
