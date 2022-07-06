package es.nutrarias.citas.entities;

import java.util.HashSet;

import java.util.Set;

public class Usuario {
	
	private String uid;
	
	private String email;
	
	private Set<RolUsuario> roles;
	
	public Usuario() {}

	public Usuario(String uid, String email, RolUsuario role) {
		this.uid = uid;
		this.email = email;
		this.roles = new HashSet<>();
		this.roles.add(role);
	}

	public String getUid() {
		return uid;
	}

	public String getEmail() {
		return email;
	}

	public Set<RolUsuario> getRoles() {
		return roles;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(RolUsuario role) {
		this.roles.clear();
		this.roles.add(role);
	}
	

}
