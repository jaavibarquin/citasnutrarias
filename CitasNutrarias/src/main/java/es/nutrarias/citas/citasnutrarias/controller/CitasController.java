package es.nutrarias.citas.citasnutrarias.controller;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;
import es.nutrarias.citas.citasnutrarias.service.CitasService;

@RestController
@RequestMapping("/citas")
public class CitasController {
	
	@Autowired
	private CitasService citasService;

	@GetMapping("/all")
	public ResponseEntity<List<Cita>> getAllCitas(){
		List<Cita> allCitas = citasService.getAllCitas();
		if (allCitas != null) {
			return ResponseEntity.ok(allCitas);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{area}/libres/{dia}")
	public List<Cita> getCitasLibres(@PathVariable AreaCita area, @PathVariable String dia){
		List<Cita> listaCitas = new LinkedList<>();
		List<Cita> lista = citasService.citasLibresPorAreaYDia(area, dia);
		if (lista != null) {
			listaCitas = lista;
		}
		return listaCitas;
	}
	
	@GetMapping("/{area}/libres")
	public ResponseEntity<List<Cita>> getCitasLibres(@PathVariable AreaCita area){
		List<Cita> listaCitas = new LinkedList<>();
		List<Cita> lista = citasService.citasLibresPorArea(area);
		if (lista != null) {
			listaCitas = lista;
		}
		return ResponseEntity.ok(listaCitas);
	}
	
	@GetMapping("/cita/{idcita}")
	public ResponseEntity<Cita> getCita(@PathVariable String idcita){
		System.out.println(idcita);
		Cita cita = citasService.citaPorId(idcita);
		if (cita != null) {
			return ResponseEntity.ok(cita);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping
	public ResponseEntity<Cita> actualizaCita(@RequestBody Cita cita, @PathVariable String idcita){
		Cita c = citasService.modificaCita(cita);
		if (c != null) {
			return ResponseEntity.ok().body(c);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/usuario/telefono/{telefono}")
	public ResponseEntity<Cita> getCitaByTelefono(@PathVariable String telefono){
		System.out.println(telefono);
		Cita cita = citasService.citaPorTelefono(telefono);
		if (cita != null) {
			return ResponseEntity.ok(cita);
		}
		return ResponseEntity.notFound().build();
	}

	
}
