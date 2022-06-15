package es.nutrarias.citas.citasnutrarias.entities;


import javax.persistence.Embeddable;


@Embeddable
public class Cliente {

	private String telefono;
	private String email;
	private String nombre;
	private String apellidos;

	public Cliente() {}
	
	public Cliente(String email, String nombre, String apellidos, String telefono) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
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

	public void setEmail(String email) {
		this.email = email;
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
	

}
