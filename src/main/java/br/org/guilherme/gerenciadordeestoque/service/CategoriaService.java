package br.org.guilherme.gerenciadordeestoque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.guilherme.gerenciadordeestoque.model.Categoria;
import br.org.guilherme.gerenciadordeestoque.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repository;
	
	
	public List<Categoria> buscarTodosAsCategoria() {
		
		if(repository.findAll().isEmpty()) {
			return null;
		}
		else {
			return repository.findAll();
		}
		
	}
	
	public boolean verificacaoPorId(long id){
		
		
		return repository.findById(id).isPresent();
	}
	
		
	public Optional<Categoria> pesquisarPorId(long id){
		
	
		return repository.findById(id);
		
					
	}
	
	public Optional<Categoria> pesquisarPorDescricao(String descricao){
		
		return repository.pesquisaDescricao(descricao);
		
		
	}
	
	public Categoria salvarNoBanco(Categoria categoria) {
		
		
		return repository.save(categoria);
		
	}
	
	
	public void excluirNoBanco(long id) {
		
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResponseStatusException(
					 HttpStatus.BAD_REQUEST, "ID de categoria incorreto", e);
		}

		
	}
	

}
