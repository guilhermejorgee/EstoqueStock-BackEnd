package br.org.guilherme.gerenciadordeestoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.guilherme.gerenciadordeestoque.model.Categoria;
import br.org.guilherme.gerenciadordeestoque.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> exibirTodasCategorias(){
		return ResponseEntity.ok(categoriaService.buscarTodosAsCategoria());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> CategoriasPorId(@PathVariable long id){
		return categoriaService.pesquisarPorId(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}	
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<Categoria> pesquisarDescricao(@PathVariable String descricao){
		return categoriaService.pesquisarPorDescricao(descricao).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Categoria> adicionarCategoria(@RequestBody Categoria categoria){
		if(categoriaService.verificacaoPorId(categoria.getId()))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.salvarNoBanco(categoria));
		
	}
	
	@PutMapping
	public ResponseEntity<Categoria> atualizarCategoria(@RequestBody Categoria categoria){
		if(categoriaService.verificacaoPorId(categoria.getId()))
			return ResponseEntity.status(HttpStatus.OK).body(categoriaService.salvarNoBanco(categoria));
		return ResponseEntity.badRequest().build();
		
	}
	
	@DeleteMapping("/{id}")
	public void deletarPorId(@PathVariable long id) {
		
			categoriaService.excluirNoBanco(id);

	}


}
