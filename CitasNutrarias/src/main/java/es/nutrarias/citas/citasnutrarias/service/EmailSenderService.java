package es.nutrarias.citas.citasnutrarias.service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void enviaEmail(String destinatario, Cita cita) {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setFrom("nutrarias@gmail.com");
		mensaje.setTo(destinatario);
		mensaje.setSubject("Nueva cita en nutrarias.es");
		mensaje.setText(convierteCitaAMail(cita));
		
		mailSender.send(mensaje);
		
		System.out.println("Correo enviado correctamente a: " + destinatario);
	}
	private String convierteCitaAMail(Cita cita) {
		String citaString = "Se ha reservado correctamente su cita para nutrarias.es con los siguientes datos: \n";
		if (cita!= null) {
			citaString +="ÁREA: \t" + convierteArea(cita.getArea()) + "\n";
			citaString +="FECHA Y HORA: \t" + cita.getFullfecha().format(
					DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT)
					.withLocale(new Locale("es", "ES")))+ "\n";
			citaString +="NOMBRE Y APELLIDOS: " + cita.getCliente().getNombre() + " " + cita.getCliente().getApellidos() + "\n";
			citaString +="TELÉFONO: \t" + cita.getCliente().getTelefono() + "\n";
			citaString +="EMAIL: \t" + cita.getCliente().getEmail() + "\n";
			return citaString;
		}
		return citaString;
	}
	 private String convierteArea(AreaCita area) {
		 String areaString ="";
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
