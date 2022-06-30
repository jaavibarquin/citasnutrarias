package es.nutrarias.citas.citasnutrarias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import es.nutrarias.citas.citasnutrarias.entities.AreaCita;
import es.nutrarias.citas.citasnutrarias.entities.Cita;

public interface CitasRepository extends JpaRepository<Cita, String> {
	
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

	
	// Libres
	// Metodo propio para buscar citas libres de un area un dia
	public List<Cita> findByAreaAndFechaAndDisponibleTrue(AreaCita area, String fecha);
	// Metodo propio para buscar libres por area
	public List<Cita> findByAreaAndDisponibleTrue(AreaCita area);
	
	// Globales
	// Metodo propio para buscar por fecha y hora y area
	public Cita findByAreaAndFechaAndHora(AreaCita area, String fecha, String hora);

	// Metodo propio para buscar por fecha y area
//	public List<Cita> findByAreaAndFecha(AreaCita area,String fecha);
	public List<Cita> findByAreaAndFechaAndClienteNotNull(AreaCita area, String fecha);
	// Metodo propio para buscar por area
//	public List<Cita> findByArea(AreaCita area);
	public List<Cita> findByAreaAndClienteNotNull(AreaCita area);	

	//	 Usuario
//	@Query(value = "SELECT * FROM CITAS WHERE telefono = ?1", nativeQuery = true)
//	public List<Cita> findByIdCliente(String idCliente);
	
	
	// Procedimientos
	@Procedure
	public void addCitasLibres(@Param("fecha") String fecha, @Param("narea") String narea);
	
	@Procedure
	public void desactivaCitasPrevias(@Param("fecha")String fecha);
	
	@Procedure
	public void desactivaCitasProximas(@Param("fecha")String string);


}
