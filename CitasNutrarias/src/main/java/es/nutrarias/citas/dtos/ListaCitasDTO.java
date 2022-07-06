package es.nutrarias.citas.dtos;

import java.util.LinkedList;
import java.util.List;

import es.nutrarias.citas.entities.Cita;

public class ListaCitasDTO {

	private List<CitaDTO> listaCitas;

	public ListaCitasDTO() {
	}

	public ListaCitasDTO(List<Cita> lista) {
		listaCitas = new LinkedList<>();
		for (Cita c : lista) {
			CitaDTO cdto = new CitaDTO(c);
			listaCitas.add(cdto);
		}
	}

	public List<CitaDTO> getListaCitas() {
		return listaCitas;
	}

	public void setListaCitas(List<CitaDTO> listaCitas) {
		this.listaCitas = listaCitas;
	}
}
