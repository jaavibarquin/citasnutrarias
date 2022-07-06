package es.nutrarias.citas.citasnutrarias.security.dtos;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtDTO {
	
	private String token;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtDTO(String token, String email, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.email = email;
		this.authorities = authorities;
	}

	public String getToken() {
		return token;
	}

	public String getEmail() {
		return email;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	
	

}
