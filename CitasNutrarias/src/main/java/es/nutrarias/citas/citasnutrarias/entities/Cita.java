package es.nutrarias.citas.citasnutrarias.entities;


import java.time.LocalDateTime;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import javax.persistence.Table;



@Entity
@Table(name="citas")
public class Cita {
	
	@Id
	private String idcita;
	private LocalDateTime fullfecha;
	private String fecha;
	private String hora;
	@Enumerated(EnumType.STRING)
	private AreaCita area;
	@Embedded
	private Cliente cliente;
	private boolean disponible;
	
	public Cita() {}

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	

}
