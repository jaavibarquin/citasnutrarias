package es.nutrarias.citas.citasnutrarias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.nutrarias.citas.citasnutrarias.entities.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, String>{

}
