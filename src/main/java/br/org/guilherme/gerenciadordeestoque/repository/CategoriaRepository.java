package br.org.guilherme.gerenciadordeestoque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.guilherme.gerenciadordeestoque.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	@Query(value = "select * from tb_categoria where descricao like %:descricao%", nativeQuery = true)
	public Optional<Categoria> pesquisaDescricao(@Param("descricao") String nome);

}
