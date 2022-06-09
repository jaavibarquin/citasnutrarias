package es.nutrarias.citas.citasnutrarias.dtos;

import java.time.LocalDateTime;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;

public class CitaLibreDTO {

	private String idcita;
	private LocalDateTime fullfecha;
	private String fecha;
	private String hora;
	private AreaCita area;
	
	public CitaLibreDTO() {}
	
	public CitaLibreDTO(Cita c) {
		this.idcita = c.getIdcita();
		this.fullfecha = c.getFullfecha();
		this.fecha = c.getFecha();
		this.hora = c.getHora().substring(0, 5);
		this.area = c.getArea();
	}

	public String getIdcita() {
		return idcita;
	}

	public LocalDateTime getFullfecha() {
		return fullfecha;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public AreaCita getArea() {
		return area;
	}

	public void setIdcita(String idcita) {
		this.idcita = idcita;
	}

	public void setFullfecha(LocalDateTime fullfecha) {
		this.fullfecha = fullfecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setArea(AreaCita area) {
		this.area = area;
	}

}
