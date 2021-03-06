package es.nutrarias.citas.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.entities.Usuario;
import es.nutrarias.citas.security.entities.UsuarioSecurity;
import es.nutrarias.citas.service.UsuarioService;

@Service
public class UsuarioSecurityService implements UserDetailsService {

	@Autowired
	UsuarioService userSvc;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userSvc.getUsuario(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario con email " + username + " no encontrado.");
		}
		return UsuarioSecurity.build(user);
	}

}
