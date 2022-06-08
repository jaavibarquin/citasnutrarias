package es.nutrarias.citas.citasnutrarias.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/nutrarias/citas")
public class CitasController {

	@Autowired
	private CitasService citasService;

	@GetMapping("/authenticate")
	public String getToken() {
		String token = "";
		return token;
	}

	@GetMapping()
	public ResponseEntity<List<CitaDTO>> getAllCitas(){
		List<Cita> allCitas = citasService.getAllCitas();
		List<CitaDTO> listaCitas = new ListaCitasDTO(allCitas).getListaCitas();
		if ( listaCitas != null) {
			return ResponseEntity.ok(listaCitas);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{area}")
	public ResponseEntity<List<CitaDTO>> getCitasArea(@PathVariable AreaCita area, @RequestParam(required = false) String dia) {
		if (!compruebaArea(area)) {
			return ResponseEntity.badRequest().build();
		}
		List<Cita> lista;
		if (dia == null) {
			lista = citasService.citasPorArea(area);
		} else {
			lista = citasService.citasPorAreaYFecha(area, dia);
		}
		if (lista != null) {
			List<CitaDTO> listaCitas = new ListaCitasDTO(lista).getListaCitas();
			if(listaCitas!=null) {
				return ResponseEntity.ok(listaCitas);
			}
		} 
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{area}/libres")
	public ResponseEntity<List<CitaLibreDTO>> getCitasAreaLibres(@PathVariable AreaCita area, @RequestParam(required = false) String dia) {
		if (!compruebaArea(area)) {
			return ResponseEntity.badRequest().build();
		}
		ListaCitasLibresDTO listaLibres;
		if (dia == null) {
			listaLibres = new ListaCitasLibresDTO(citasService.citasLibresPorArea(area));
		} else {
			listaLibres = new ListaCitasLibresDTO(citasService.citasLibresPorAreaYFecha(area, dia));
		}
		if (listaLibres.getListaCitas() != null) {
			return ResponseEntity.ok(listaLibres.getListaCitas());
		} 
		return ResponseEntity.notFound().build();

	}
	@GetMapping("/{area}/{dia}/{hora}")
	public ResponseEntity<CitaDTO> getCitaByFecha(@PathVariable AreaCita area, @PathVariable String dia, @PathVariable String hora){
		Cita cita = citasService.citaPorAreaDiaYHora(area, dia, hora);
		if (cita != null) {
			CitaDTO cDTO = new CitaDTO(cita);
			return ResponseEntity.ok(cDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/busca-cita/{idCita}")
	public ResponseEntity<CitaDTO> getCitaByID(@PathVariable String idCita){
		Cita cita = citasService.citaPorId(idCita);
		if (cita != null) {
			CitaDTO cDTO = new CitaDTO(cita);
			return ResponseEntity.ok(cDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/busca-cita/{idCita}")
	public ResponseEntity<CitaDTO> actualizaCitaByID(@RequestBody CitaDTO citaDTO, @PathVariable String idCita){
		Cita citaPorID = citasService.citaPorId(idCita);
		if (citaPorID == null) {
			return ResponseEntity.notFound().build();
		}
		Cita cita = citaDTO.transformToCita();
		Cita citaModif = citasService.modificaCita(cita);
		if (citaModif != null) {
			citaDTO = new CitaDTO(citaModif);
			return ResponseEntity.ok().body(citaDTO);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	


//	@GetMapping("/clientes/{idCliente}/citas")
//	public ResponseEntity<List<CitaDTO>> getCitasByIdCliente(@PathVariable String idCliente){
//		List<Cita> listaCitas = citasService.citaPorIdCliente(idCliente);
//		if (listaCitas != null) {
//			List<CitaDTO> listaDTO = new ListaCitasDTO(listaCitas).getListaCitas();
//			return ResponseEntity.ok(listaDTO);
//		}
//		return ResponseEntity.notFound().build();
//	}

	private boolean compruebaArea (AreaCita area) {
		return (area.toString().equals("ENTR") || area.toString().equals("NUTR") || area.toString().equals("PSIC")); 	
	}

}
