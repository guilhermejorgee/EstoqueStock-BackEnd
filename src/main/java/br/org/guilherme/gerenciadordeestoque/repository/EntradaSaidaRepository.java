package br.org.guilherme.gerenciadordeestoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.guilherme.gerenciadordeestoque.model.EntradaSaida;

@Repository
public interface EntradaSaidaRepository extends JpaRepository<EntradaSaida, Long> {

}
