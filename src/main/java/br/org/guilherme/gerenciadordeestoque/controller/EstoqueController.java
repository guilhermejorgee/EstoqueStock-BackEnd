package br.org.guilherme.gerenciadordeestoque.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.guilherme.gerenciadordeestoque.model.Estoque;
import br.org.guilherme.gerenciadordeestoque.service.EstoqueService;


@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;
	
	@GetMapping
	public ResponseEntity<List<Estoque>> TodosMateriais(){
		return ResponseEntity.ok(estoqueService.exibirTodosMaterias());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estoque> exibirMaterialPorId(@PathVariable long id){	
		if(estoqueService.verificacaoMaterial(id))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(estoqueService.pesquisarMaterial(id)); 
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Estoque> exibirPorNome(@PathVariable String nome){
		return estoqueService.pesquisarMaterialPorNome(nome).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/valorunit/{valorUnit}")
	public ResponseEntity<List<Estoque>> exibirValorUnit(@PathVariable double valorUnit){
		return ResponseEntity.ok(estoqueService.pesquisarPorValorUnit(valorUnit));
	}
	
	@PostMapping("/consumopadrao/{id}")
	public ResponseEntity<Integer> consumoPadrao(@PathVariable long id){
		
		return ResponseEntity.ok(estoqueService.calculoConsumoPadraoMaterial(id));
	}
	
	@PostMapping("/estsegpadrao/{id}")
	public ResponseEntity<Integer> estSegPadrao(@PathVariable long id){
		
		return ResponseEntity.ok(estoqueService.estoqueSegurancaPadrao(id));
	}
	
	@PostMapping("/tempopedidocomseg/{id}")
	public ResponseEntity<Integer> tempPedComSeg(@PathVariable long id){
		
		return ResponseEntity.ok(estoqueService.PontodePedidoPadraoComSeguranca(id));
	}
	
	@PostMapping("/tempopedidosemseg/{id}")
	public ResponseEntity<Integer> tempPedSemSeg(@PathVariable long id){
		
		return ResponseEntity.ok(estoqueService.PontodePedidoPadraoSemSeguranca(id));
	}
		
	
	@PostMapping("/{idUsuario}")
	public ResponseEntity<Estoque> addMaterial(@PathVariable long idUsuario, @RequestBody Estoque material){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.adicionarMaterial(material, idUsuario));
	}
	
	
	@PutMapping("/addquantidadebotao/{id}/usuario/{idUsuario}")
	public ResponseEntity<Estoque> addComAcao(@PathVariable long id, @PathVariable long idUsuario){
		if(estoqueService.verificacaoMaterial(id))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.status(HttpStatus.OK).body(estoqueService.adicionarQuantidadeClicando(id, idUsuario));
	}
	
	@PutMapping("/rttquantidadebotao/{id}/usuario/{idUsuario}")
	public ResponseEntity<Estoque> rttComAcao(@PathVariable long id, @PathVariable long idUsuario){
		if(estoqueService.verificacaoMaterial(id))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.status(HttpStatus.OK).body(estoqueService.retirarQuantidadeClicando(id, idUsuario));
	}
	
	@PutMapping("/addquantidadedig/{id}/usuario/{idUsuario}/qtd/{qtd}")
	public ResponseEntity<Estoque> addDigitando(@PathVariable long id, @PathVariable long idUsuario, @PathVariable int qtd){
		if(estoqueService.verificacaoMaterial(id))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.status(HttpStatus.OK).body(estoqueService.adicionarQuantidadeDigitando(id, idUsuario, qtd));
	}
	
	@PutMapping("/rttquantidadedig/{id}/usuario/{idUsuario}/qtd/{qtd}")
	public ResponseEntity<Estoque> rttDigitando(@PathVariable long id, @PathVariable long idUsuario, @PathVariable int qtd){
		if(estoqueService.verificacaoMaterial(id))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.status(HttpStatus.OK).body(estoqueService.retirarQuantidadeDigitando(id, idUsuario, qtd));
	}
	

	@PutMapping
	public ResponseEntity<Estoque> editarCaracteristicas(@RequestBody Estoque material){
		if(estoqueService.verificacaoMaterial(material))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.status(HttpStatus.OK).body(estoqueService.atualizarCaracteristicas(material));
	}
	

	
	
	
	@DeleteMapping("/{id}")
	public void deletarPorId(@PathVariable long id) {
		estoqueService.excluirMaterialPorId(id);
	}

	
}