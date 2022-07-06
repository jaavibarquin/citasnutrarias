package es.nutrarias.citas.service;

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.entities.AreaCita;
import es.nutrarias.citas.entities.Cita;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	public void enviaEmail(String destinatario, Cita cita) throws MessagingException, UnsupportedEncodingException {
		MimeMessage mensaje = mailSender.createMimeMessage();
		MimeMessageHelper mensajeHelper = new MimeMessageHelper(mensaje, true);
		mensajeHelper.setFrom("javibarquines10@gmail.com", "nutrarias.es");
		mensajeHelper.setTo(destinatario);
		mensajeHelper.setSubject("Nueva cita en nutrarias.es");
		String foto = "<img src='cid:logoImg' />";
		mensajeHelper.setText(foto + convierteCitaAMail(cita), true);

		ClassPathResource resource = new ClassPathResource("/img/fondonutrarias.jpg");
		mensajeHelper.addInline("logoImg", resource);
		mailSender.send(mensaje);

		System.out.println("Correo enviado correctamente a: " + destinatario);
	}

	private String convierteCitaAMail(Cita cita) {
		String citaString = "<hr><p>Se ha reservado correctamente su cita para nutrarias.es con los siguientes datos: <p>\n";
		if (cita != null) {
			citaString += "<p>ÁREA: \t <b>" + convierteArea(cita.getArea()) + "</b></p>";
			citaString += "<p>FECHA Y HORA: \t<b>" + cita.getFullfecha().format(
					DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
							.withLocale(new Locale("es", "ES")))
					+ "</b></p>";
			citaString += "<p>NOMBRE Y APELLIDOS: <b>" + cita.getCliente().getNombre() + " "
					+ cita.getCliente().getApellidos() + "</b><p>";
			citaString += "<p>TELÉFONO: \t<b>" + cita.getCliente().getTelefono() + "</b></p>";
			citaString += "<p>EMAIL: \t<b>" + cita.getCliente().getEmail() + "</b></p>";

			return citaString;
		}
		return citaString;
	}

	private String convierteArea(AreaCita area) {
		String areaString = "";
		if (area.toString().equals("ENTR")) {
			areaString = "ENTRENAMIENTO";
		} else if (area.toString().equals("PSIC")) {
			areaString = "PSICOLOGÍA";
		} else if (area.toString().equals("NUTR")) {
			areaString = "NUTRICIÓN";
		}
		return areaString;
	}

}
