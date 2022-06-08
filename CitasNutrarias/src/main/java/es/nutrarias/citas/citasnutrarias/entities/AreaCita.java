package es.nutrarias.citas.citasnutrarias.entities;

public enum AreaCita {

	NUTR, PSIC, ENTR;
	
	public static AreaCita getArea (String area) {
		if (area.equals("NUTR")) {
			return AreaCita.NUTR;
		}
		else if (area.equals("PSIC")) {
			return AreaCita.PSIC;
		}
		else if (area.equals("ENTR")) {
			return AreaCita.ENTR;
		}
		else {
			return null;
		}
	}

}

