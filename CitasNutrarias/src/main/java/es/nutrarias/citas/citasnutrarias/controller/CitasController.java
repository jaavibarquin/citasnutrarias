package es.nutrarias.citas.citasnutrarias.controller;


import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.nutrarias.citas.citasnutrarias.dtos.CitaDTO;
import es.nutrarias.citas.citasnutrarias.dtos.CitaLibreDTO;
import es.nutrarias.citas.citasnutrarias.dtos.ListaCitasDTO;
import es.nutrarias.citas.citasnutrarias.dtos.ListaCitasLibresDTO;
import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;
import es.nutrarias.citas.citasnutrarias.entities.Cliente;
import es.nutrarias.citas.citasnutrarias.service.CitasService;

@RestController
@RequestMapping("/nutrarias")
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

	@GetMapping("/citas/{area}")
	public ResponseEntity<List<CitaDTO>> getCitasArea(@PathVariable AreaCita area, @RequestParam(required = false) String fecha) {
		if (!compruebaArea(area)) {
			return ResponseEntity.badRequest().build();
		}
		List<Cita> lista;
		if (fecha == null) {
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
	public ResponseEntity<CitaDTO> getCitaByFecha(@PathVariable AreaCita area, @PathVariable String fecha, @PathVariable String hora){
		Cita cita = citasService.citaPorAreaDiaYHora(area, fecha, hora);
		if (cita != null) {
			CitaDTO cDTO = new CitaDTO(cita);
			return ResponseEntity.ok(cDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/citas/{area}/{fecha}/{hora}")
	public ResponseEntity<CitaDTO> modificaCitaByFecha(@PathVariable AreaCita area, @PathVariable String fecha, @PathVariable String hora, @RequestBody CitaDTO citaDTO){
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

	@GetMapping("/citas/busca-cita/{idCita}")
	public ResponseEntity<CitaDTO> getCitaByID(@PathVariable String idCita){
		Cita cita = citasService.citaPorId(idCita);
		if (cita != null) {
			CitaDTO cDTO = new CitaDTO(cita);
			return ResponseEntity.ok(cDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/citas/busca-cita/{idCita}")
	public ResponseEntity<CitaDTO> actualizaCitaByID(@RequestBody CitaDTO citaDTO, @PathVariable String idCita){
		Cita citaPorID = citasService.citaPorId(idCita);
		if (citaPorID == null) {
			return ResponseEntity.notFound().build();
		}
		Cita cita = citaDTO.transformToCita();
		Cita citaModif = citasService.modificaCita(cita);
		if (citaModif != null) {
			citaDTO = new CitaDTO(citaModif);
			return ResponseEntity.ok(citaDTO);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}


	@GetMapping("/clientes/{idCliente}/citas")
	public ResponseEntity<List<CitaDTO>> getCitasByIdCliente(@PathVariable String idCliente){
		Cliente cli = citasService.buscaCliente(idCliente);
		if (cli != null) {
			List<Cita> listaCitas = citasService.citasPorCliente(cli.getTelefono());
			if (listaCitas != null) {
				List<CitaDTO> listaDTO = new ListaCitasDTO(listaCitas).getListaCitas();
				return ResponseEntity.ok(listaDTO);
			}
		}
		return ResponseEntity.notFound().build();
	}

	private boolean compruebaArea (AreaCita area) {
		return (area.toString().equals("ENTR") || area.toString().equals("NUTR") || area.toString().equals("PSIC")); 	
	}

	@GetMapping("/clientes/{idCliente}")
	public ResponseEntity<Cliente> getCliente(@PathVariable String idCliente) 
	{
		Cliente cli = citasService.buscaCliente(idCliente);
		if (cli != null) {
			return ResponseEntity.ok(cli);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/clientes/{idCliente}")
	public ResponseEntity<Cliente> guardaCliente(@PathVariable String idCliente, @RequestBody Cliente cliente) 
	{
		if (idCliente.isEmpty() || idCliente.equals(null) || cliente == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}
		Cliente cli = citasService.buscaCliente(idCliente);
		if (cli == null) {
			Cliente cliCreado = citasService.anhadeCliente(cliente);
			if (cliCreado != null) {
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
				return ResponseEntity.created(location).body(cliCreado);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
			}
		} else {
			Cliente cliModif = citasService.modificaCliente(cliente);
			if (cliModif != null) {
				return ResponseEntity.ok(cliModif);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
			}

		}
	}

}
