package es.nutrarias.citas.citasnutrarias.dtos;

import java.time.LocalDateTime;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;
import es.nutrarias.citas.citasnutrarias.entities.Cliente;

public class CitaDTO {
	
	private String idcita;
	private LocalDateTime fullfecha;
	private String fecha;
	private String hora;
	private String area;
	private String email;
	private String nombre;
	private String apellidos;
	private String telefono;
	private boolean disponible;
	
	public CitaDTO() {}
	
	public CitaDTO(Cita c) {
		this.idcita = c.getIdcita();
		this.fullfecha = c.getFullfecha();
		this.fecha = c.getFecha();
		this.hora = c.getHora();
		this.area = c.getArea().toString();
		this.email = c.getCliente().getEmail();
		this.nombre = c.getCliente().getNombre(); 
		this.apellidos = c.getCliente().getApellidos();
		this.telefono = c.getCliente().getTelefono();
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
	public String getArea() {
		return area;
	}
	public String getEmail() {
		return email;
	}
	public String getNombre() {
		return nombre;
	}
	public String getApellidos() {
		return apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public boolean isDisponible() {
		return disponible;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
		cita.setArea(AreaCita.getArea(this.area));
		Cliente cli = new Cliente(this.email, this.nombre, this.apellidos, this.telefono);
		cita.setCliente(cli);
		return cita;
	}

}
