package es.nutrarias.citas.entities;


import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import org.springframework.lang.NonNull;



@Entity
@Table(name="citas")
public class Cita {
	
	@Id
	private String idcita;
	@NonNull
	private LocalDateTime fullfecha;
	@NonNull
	private String fecha;
	@NonNull
	private String hora;
	@Enumerated(EnumType.STRING)
	@NonNull
	private AreaCita area;

	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "nombre", column = @Column(name = "nombre")),
		  @AttributeOverride( name = "apellidos", column = @Column(name = "apellidos")),
		  @AttributeOverride( name = "telefono", column = @Column(name = "telefono")),
		  @AttributeOverride( name = "email", column = @Column(name = "email"))
		})
	private Cliente cliente;
	@NonNull
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
