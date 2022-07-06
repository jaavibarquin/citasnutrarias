package es.nutrarias.citas.citasnutrarias.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.nutrarias.citas.citasnutrarias.dtos.CitaDTO;
import es.nutrarias.citas.citasnutrarias.dtos.CitaLibreDTO;
import es.nutrarias.citas.citasnutrarias.dtos.ListaCitasDTO;
import es.nutrarias.citas.citasnutrarias.dtos.ListaCitasLibresDTO;
import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;
import es.nutrarias.citas.citasnutrarias.service.CitasService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/nutrarias")
public class CitasController {

	@Autowired
	private CitasService citasService;


	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/citas/{area}")
	public ResponseEntity<List<CitaDTO>> getCitasArea(@PathVariable AreaCita area, @RequestParam(required = false) String fecha) {
		if (!compruebaArea(area)) {
			return ResponseEntity.badRequest().build();
		}
		List<Cita> lista;
		if (fecha == null || fecha.isEmpty()) {
			lista = citasService.citasPorArea(area);
		} else {
			lista = citasService.citasPorAreaYFecha(area, fecha);
		}
		if (lista != null) {
			List<CitaDTO> listaCitas = new ListaCitasDTO(lista).getListaCitas();
			if(listaCitas!=null) {
				return ResponseEntity.ok(listaCitas);
			}
		} 
		return ResponseEntity.notFound().build();
	}


	@GetMapping("/citas/{area}/libres")
	public ResponseEntity<List<CitaLibreDTO>> getCitasAreaLibres(@PathVariable AreaCita area, @RequestParam(required = false) String fecha) {
		if (!compruebaArea(area)) {
			return ResponseEntity.badRequest().build();
		}
		ListaCitasLibresDTO listaLibres;
		if (fecha == null) {
			listaLibres = new ListaCitasLibresDTO(citasService.citasLibresPorArea(area));
		} else {
			listaLibres = new ListaCitasLibresDTO(citasService.citasLibresPorAreaYFecha(area, fecha));
		}
		if (listaLibres.getListaCitas() != null) {
			return ResponseEntity.ok(listaLibres.getListaCitas());
		} 
		return ResponseEntity.notFound().build();

	}
	
	@GetMapping("/citas/{area}/{fecha}/{hora}")
	public ResponseEntity<CitaDTO> getCitaByFecha(@PathVariable AreaCita area, 
			@PathVariable String fecha, @PathVariable String hora){
		Cita cita = citasService.citaPorAreaDiaYHora(area, fecha, hora);
		if (cita != null) {
			CitaDTO cDTO = new CitaDTO(cita);
			return ResponseEntity.ok(cDTO);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/citas/{area}/{fecha}/{hora}")
	public ResponseEntity<CitaDTO> modificaCitaByFecha(@PathVariable AreaCita area,
			@PathVariable String fecha, @PathVariable String hora, @RequestBody CitaDTO citaDTO){
		if (citaDTO == null) {
			return ResponseEntity.badRequest().body(citaDTO);
		}
		Cita cita = citasService.citaPorAreaDiaYHora(area, fecha, hora);
		if (cita != null) {
			Cita citaModif = citasService.modificaCita(citaDTO.transformToCita());
			CitaDTO dtoModif = new CitaDTO(citaModif);
			return ResponseEntity.ok(dtoModif);
		}
		return ResponseEntity.notFound().build();
	}

//	@PreAuthorize("hasRole('ADMIN')")
//	@GetMapping("/clientes/{idCliente}/citas")
//	public ResponseEntity<List<CitaDTO>> getCitasByIdCliente(@PathVariable String idCliente){
//		
//		if (citasService.existeCliente(idCliente)) {
//			List<Cita> listaCitas = citasService.citasPorCliente(idCliente);
//			if (listaCitas != null) {
//				List<CitaDTO> listaDTO = new ListaCitasDTO(listaCitas).getListaCitas();
//				return ResponseEntity.ok(listaDTO);
//			}
//		}
//		return ResponseEntity.notFound().build();
//	}

	private boolean compruebaArea (AreaCita area) {
		return (area.toString().equals("ENTR") || area.toString().equals("NUTR") || area.toString().equals("PSIC")); 	
	}


}
