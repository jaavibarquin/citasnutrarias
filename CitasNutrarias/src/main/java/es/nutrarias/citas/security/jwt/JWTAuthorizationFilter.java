package es.nutrarias.citas.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import es.nutrarias.citas.security.service.UsuarioSecurityService;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private static final String CABECERA = "Authorization";
	private static final String BEARER = "Bearer ";

	@Autowired
	JWTProvider jwtProvider;

	@Autowired
	UsuarioSecurityService userSvc;

	private static final Logger loger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = getJWTToken(request);
			if (token != null && jwtProvider.validaToken(token)) {
				String email = jwtProvider.getEmailFromToken(token);
				UserDetails userDet = userSvc.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDet, null,
						userDet.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			loger.error("error en el doFilter: " + e.getMessage());
			e.printStackTrace();
		}
		filterChain.doFilter(request, response);

	}

	private String getJWTToken(HttpServletRequest request) {
		String cabeceraAuth = request.getHeader(CABECERA);
		if (cabeceraAuth != null && cabeceraAuth.startsWith(BEARER)) {
			return cabeceraAuth.replace(BEARER, "");
		}
		return null;
	}

}
