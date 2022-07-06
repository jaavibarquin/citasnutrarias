package es.nutrarias.citas.security.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.nutrarias.citas.entities.Usuario;

public class UsuarioSecurity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private String email;

	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioSecurity() {
	}

	public UsuarioSecurity(String uid, String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.uid = uid;
		this.email = email;
		this.authorities = authorities;

	}

	public static UsuarioSecurity build(Usuario user) {
		List<GrantedAuthority> authList = user.getRoles().stream().map(
				rol -> new SimpleGrantedAuthority(rol.toString())).collect(Collectors.toList());

		return new UsuarioSecurity(user.getUid(), user.getEmail(), authList);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return uid;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
