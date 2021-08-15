package br.org.guilherme.gerenciadordeestoque.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import br.org.guilherme.gerenciadordeestoque.model.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
	
	
	@Query(value = "select * from tb_estoque where nome like %:nome%", nativeQuery = true)
	public Optional<Estoque> pesquisaNome(@Param("nome") String nome);
	
	@Query(value = "select * from tb_estoque where valor_unit <= :valorUnit", nativeQuery = true)
	public List<Estoque> pesquisaValorUnit(@Param("valorUnit") double valorUnit);
	

}
