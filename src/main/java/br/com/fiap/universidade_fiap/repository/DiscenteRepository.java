package br.com.fiap.universidade_fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.universidade_fiap.model.Discente;

@Repository
public interface DiscenteRepository extends JpaRepository<Discente, Long>{

}
