package es.nutrarias.citas.dtos;

import java.time.LocalDateTime;

import es.nutrarias.citas.entities.AreaCita;
import es.nutrarias.citas.entities.Cita;
import es.nutrarias.citas.entities.Cliente;

public class CitaDTO {

	private String idcita;
	private LocalDateTime fullfecha;
	private String fecha;
	private String hora;
	private AreaCita area;
	private Cliente cliente;
	private boolean disponible;

	public CitaDTO() {
	}

	public CitaDTO(Cita c) {
		this.idcita = c.getIdcita();
		this.fullfecha = c.getFullfecha();
		this.fecha = c.getFecha();
		this.hora = c.getHora().substring(0, 5);
		this.area = c.getArea();
		this.cliente = c.getCliente();
		this.disponible = c.isDisponible();
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

	public Cliente getCliente() {
		return cliente;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public AreaCita getArea() {
		return area;
	}

	public void setArea(AreaCita area) {
		this.area = area;
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

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Cita transformToCita() {
		Cita cita = new Cita();
		cita.setIdcita(this.idcita);
		cita.setFullfecha(this.fullfecha);
		cita.setFecha(this.fecha);
		cita.setHora(this.hora);
		cita.setArea(this.area);
		cita.setCliente(this.cliente);
		return cita;
	}
	//
	// private AreaCita getArea (String area) {
	// if (area.equals("NUTR")) {
	// return AreaCita.NUTR;
	// }
	// else if (area.equals("PSIC")) {
	// return AreaCita.PSIC;
	// }
	// else if (area.equals("ENTR")) {
	// return AreaCita.ENTR;
	// }
	// else {
	// return null;
	// }
	// }

}
