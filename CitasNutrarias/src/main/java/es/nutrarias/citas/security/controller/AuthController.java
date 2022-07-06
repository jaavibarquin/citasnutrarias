package es.nutrarias.citas.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.nutrarias.citas.security.dtos.JwtDTO;
import es.nutrarias.citas.security.dtos.UsuarioDTO;
import es.nutrarias.citas.security.jwt.JWTProvider;
import es.nutrarias.citas.service.UsuarioService;

@RestController
@RequestMapping("/nutrarias/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	UsuarioService userSvc;

	@Autowired
	JWTProvider jwtProvider;

	@PostMapping("/token")
	public ResponseEntity<JwtDTO> getToken(@RequestBody UsuarioDTO userDTO) {
		if (userDTO == null) {
			return ResponseEntity.badRequest().build();
		} else {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
					userDTO.getEmail(), userDTO.getUid()));
			SecurityContextHolder.getContext().setAuthentication(auth);
			String token = jwtProvider.generaToken(auth);
			UserDetails usrDetails = (UserDetails) auth.getPrincipal();
			JwtDTO jwtDTO = new JwtDTO(token, usrDetails.getUsername(), usrDetails.getAuthorities());
			return ResponseEntity.ok(jwtDTO);
		}
	}
}
