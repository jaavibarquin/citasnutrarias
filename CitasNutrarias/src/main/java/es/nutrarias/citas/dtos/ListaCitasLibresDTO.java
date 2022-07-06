package es.nutrarias.citas.dtos;

import java.util.LinkedList;
import java.util.List;

import es.nutrarias.citas.entities.Cita;

public class ListaCitasLibresDTO {

	private List<CitaLibreDTO> listaCitas;

	public ListaCitasLibresDTO() {
	}

	public ListaCitasLibresDTO(List<Cita> lista) {
		listaCitas = new LinkedList<>();
		for (Cita c : lista) {
			CitaLibreDTO cdto = new CitaLibreDTO(c);
			listaCitas.add(cdto);
		}
	}

	public List<CitaLibreDTO> getListaCitas() {
		return listaCitas;
	}

	public void setListaCitas(List<CitaLibreDTO> listaCitas) {
		this.listaCitas = listaCitas;
	}

}
