package es.nutrarias.citas.citasnutrarias.security.jwt;

import java.util.Calendar;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import es.nutrarias.citas.citasnutrarias.security.entities.UsuarioSecurity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JWTProvider {
	private static final Logger log = LoggerFactory.getLogger(JWTProvider.class);

	private static final String SECRET_KEY = "pwjavi062022";


	private static final int EXPIRATION_TIME = 10 * 60 * 1000;

	public String generaToken(Authentication auth) {
		UsuarioSecurity user = (UsuarioSecurity) auth.getPrincipal();
		Date creation = new Date(Calendar.getInstance().getTimeInMillis());
		Date expiration = new Date(Calendar.getInstance().getTimeInMillis() + EXPIRATION_TIME);
		return Jwts.builder().setSubject(user.getUsername())
				.setIssuedAt(creation)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}

	public String getEmailFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validaToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		}catch (UnsupportedJwtException e) {
			// El token no incluye ese claim
			log.error("TOKEN NO SOPORTADO: " + token);
			return false;
		}
		catch (MissingClaimException e) {
			// El token no incluye ese claim
			log.error("FALTA EL CLAIM: " + token);
			return false;
		}
		catch (ExpiredJwtException e) { 
			// El token ha expirado
			log.error("EL TOKEN HA EXPIRADO: " + token);
			return false;
		} catch (MalformedJwtException e) {
			// El token incluye el claim pero el valor no coincide
			log.error("TOKEN INCORRECTO: " + token);
			return false;
		} catch (SignatureException e) {
			log.error("TOKEN NO VALIDO: " + token);
			return false;
		}
	}

}
