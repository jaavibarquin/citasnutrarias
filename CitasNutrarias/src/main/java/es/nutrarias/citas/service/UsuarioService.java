package es.nutrarias.citas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.entities.RolUsuario;
import es.nutrarias.citas.entities.Usuario;
import es.nutrarias.citas.repository.UsuariosRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuariosRepository usuariosRepo;

	public List<Usuario> getUsuarios() {
		return usuariosRepo.findAll();
	}

	public Usuario getUsuario(String emailUsuario) {
		return usuariosRepo.findByEmail(emailUsuario);
	}

	public Usuario tieneEmailRolAdmin(String emailUsuario) {
		Usuario user = usuariosRepo.findByEmail(emailUsuario);
		if (compruebaAdmin(user)) {
			return user;
		}
		return null;
	}

	public Usuario tieneUidRolAdmin(String uid) {
		Usuario user = usuariosRepo.findByUid(uid);
		if (compruebaAdmin(user)) {
			return user;
		}
		return null;
	}

	private boolean compruebaAdmin(Usuario user) {
		if (user != null) {
			return user.getRoles().contains(RolUsuario.ROLE_ADMIN);
		}
		return false;
	}

}
