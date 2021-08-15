package br.org.guilherme.gerenciadordeestoque.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.guilherme.gerenciadordeestoque.model.EntradaSaida;
import br.org.guilherme.gerenciadordeestoque.model.Estoque;
import br.org.guilherme.gerenciadordeestoque.model.Usuario;
import br.org.guilherme.gerenciadordeestoque.repository.EntradaSaidaRepository;
import br.org.guilherme.gerenciadordeestoque.repository.EstoqueRepository;
import br.org.guilherme.gerenciadordeestoque.repository.UsuarioRepository;

@Service
public class EstoqueService {

	@Autowired
	private EstoqueRepository repository;

	@Autowired
	private EntradaSaidaRepository movRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public LocalDate convertToLocalDate(Date dateToConvert) {
		return LocalDate.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}

	public List<Estoque> exibirTodosMaterias() {

		return repository.findAll();
	}

	public Optional<Estoque> pesquisarMaterialPorNome(String nome) {

		return repository.pesquisaNome(nome);

	}

	public List<Estoque> pesquisarPorValorUnit(double valorUnit) {

		return repository.pesquisaValorUnit(valorUnit);

	}

	public Estoque adicionarMaterial(Estoque material, long idUsuario) {

		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

		material.setValorTotal(atualizarEstoqueTotal(material.getValorUnit(), material.getQuantidade()));

		Estoque materialSalvo = repository.save(material);

		EntradaSaida novaEntrada = new EntradaSaida();
		novaEntrada.setEntrada(novaEntrada.getEntrada() + materialSalvo.getQuantidade());
		novaEntrada.setEstoque(materialSalvo);
		novaEntrada = movRepository.save(novaEntrada);

		usuario.getMovimentacoes().add(novaEntrada);

		usuarioRepository.save(usuario);

		return materialSalvo;

	}

	public Estoque adicionarQuantidadeClicando(long id, long idUsuario) {

		Estoque material = pesquisarMaterial(id);

		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

		for (EntradaSaida movimentacao : material.getMovimentacao()) {

			Integer periodo = Period.between(convertToLocalDate(movimentacao.getData()), LocalDate.now()).getDays();

			if (periodo == 0) {

				movimentacao.setEntrada(movimentacao.getEntrada() + 1);
				movRepository.save(movimentacao);

				usuario.getMovimentacoes().add(movimentacao);

			}else {
				
				EntradaSaida novaEntrada = new EntradaSaida();
				novaEntrada.setEntrada(novaEntrada.getEntrada() + 1);
				novaEntrada.setEstoque(material);
				novaEntrada = movRepository.save(novaEntrada);

				usuario.getMovimentacoes().add(novaEntrada);

				usuarioRepository.save(usuario);
			}

		}

		material.setQuantidade(material.getQuantidade() + 1);

		material.setValorTotal(atualizarEstoqueTotal(material.getValorUnit(), material.getQuantidade()));

		return repository.save(material);

	}

	public Estoque retirarQuantidadeClicando(long id, long idUsuario) {

		Estoque material = pesquisarMaterial(id);

		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

		for (EntradaSaida movimentacao : material.getMovimentacao()) {

			Integer periodo = Period.between(convertToLocalDate(movimentacao.getData()), LocalDate.now()).getDays();

			if (periodo == 0) {

				movimentacao.setSaida(movimentacao.getSaida() + 1);
				movRepository.save(movimentacao);

				usuario.getMovimentacoes().add(movimentacao);

			}else {
				
				EntradaSaida novaEntrada = new EntradaSaida();
				novaEntrada.setSaida(novaEntrada.getSaida() + 1);
				novaEntrada.setEstoque(material);
				novaEntrada = movRepository.save(novaEntrada);

				usuario.getMovimentacoes().add(novaEntrada);

				usuarioRepository.save(usuario);
			}

		}

		if (material.getQuantidade() > 0) {
			material.setQuantidade(material.getQuantidade() - 1);

		} else {
			material.setQuantidade(0);
		}

		material.setValorTotal(atualizarEstoqueTotal(material.getValorUnit(), material.getQuantidade()));

		return repository.save(material);

	}

	public Estoque adicionarQuantidadeDigitando(long id, long idUsuario, int quantidade) {

		Estoque material = pesquisarMaterial(id);

		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

		for (EntradaSaida movimentacao : material.getMovimentacao()) {

			Integer periodo = Period.between(convertToLocalDate(movimentacao.getData()), LocalDate.now()).getDays();

			if (periodo == 0) {

				movimentacao.setEntrada(movimentacao.getEntrada() + quantidade);
				movRepository.save(movimentacao);

				usuario.getMovimentacoes().add(movimentacao);

			}else {
				
				EntradaSaida novaEntrada = new EntradaSaida();
				novaEntrada.setEntrada(novaEntrada.getEntrada() + quantidade);
				novaEntrada.setEstoque(material);
				novaEntrada = movRepository.save(novaEntrada);

				usuario.getMovimentacoes().add(novaEntrada);

				usuarioRepository.save(usuario);
			}

		}

		material.setQuantidade(material.getQuantidade() + quantidade);

		material.setValorTotal(atualizarEstoqueTotal(material.getValorUnit(), material.getQuantidade()));

		return repository.save(material);

	}

	public Estoque retirarQuantidadeDigitando(long id, long idUsuario, int quantidade) {

		Estoque material = pesquisarMaterial(id);

		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

		if (material.getQuantidade() - quantidade < 0) {
			return null;
		}

		for (EntradaSaida movimentacao : material.getMovimentacao()) {

			Integer periodo = Period.between(convertToLocalDate(movimentacao.getData()), LocalDate.now()).getDays();

			if (periodo == 0) {

				movimentacao.setSaida(movimentacao.getSaida() + quantidade);
				movRepository.save(movimentacao);

				usuario.getMovimentacoes().add(movimentacao);

			}else {
				
				EntradaSaida novaEntrada = new EntradaSaida();
				novaEntrada.setSaida(novaEntrada.getSaida() + quantidade);
				novaEntrada.setEstoque(material);
				novaEntrada = movRepository.save(novaEntrada);

				usuario.getMovimentacoes().add(novaEntrada);

				usuarioRepository.save(usuario);
			}

		}

		material.setQuantidade(material.getQuantidade() - quantidade);

		material.setValorTotal(atualizarEstoqueTotal(material.getValorUnit(), material.getQuantidade()));

		return repository.save(material);

	}

	public void excluirMaterialPorId(long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Material não encontrado", e);
		}

	}

	public Estoque atualizarCaracteristicas(Estoque material) {

		Estoque estoque = pesquisarMaterial(material);
		
		material.setMovimentacao(estoque.getMovimentacao());

		material.setCategoria(estoque.getCategoria());
		
		material.setQuantidade(estoque.getQuantidade());

		return repository.save(material);

	}

	public int calculoConsumoPadraoMaterial(long id) {

		Estoque estoque = pesquisarMaterial(id);

		Set<EntradaSaida> listaMesMovimentacoes = new HashSet<>();

		int somaSaidas = 0;

		int consulmo = 0;

		for (EntradaSaida movimento : estoque.getMovimentacao()) {

			Integer periodo = Period.between(convertToLocalDate(movimento.getData()), LocalDate.now()).getDays();

			if (periodo <= estoque.getTempoConsumoPadrao()) {

				listaMesMovimentacoes.add(movimento);

			}

		}

		for (EntradaSaida movimento : listaMesMovimentacoes) {

			somaSaidas += movimento.getSaida();

		}

		consulmo = somaSaidas / listaMesMovimentacoes.size();

		return consulmo;
	}
	
	public int estoqueSegurancaPadrao(long id) {
		
		Estoque estoque = pesquisarMaterial(id);
		
		return calculoConsumoPadraoMaterial(id) * estoque.getTempoEntrega();
		
	}
	
	public int PontodePedidoPadraoSemSeguranca(long id) {
		
		Estoque estoque = pesquisarMaterial(id);
		
		return (calculoConsumoPadraoMaterial(id) * estoque.getLeadtime());
		
	}
	
	public int PontodePedidoPadraoComSeguranca(long id) {
		
		Estoque estoque = pesquisarMaterial(id);
		
		return (calculoConsumoPadraoMaterial(id) * estoque.getLeadtime()) + estoqueSegurancaPadrao(id);
		
	}
	

	public boolean verificacaoMaterial(Estoque material) {

		return repository.findById(material.getId()).isEmpty();

	}

	public boolean verificacaoMaterial(long id) {

		return repository.findById(id).isEmpty();
	}

	public Estoque pesquisarMaterial(Estoque material) {

		return repository.findById(material.getId()).orElse(null);

	}

	public Estoque pesquisarMaterial(long id) {

		return repository.findById(id).orElse(null);
	}

	public BigDecimal atualizarEstoqueTotal(BigDecimal valorUnit, int valorQtd) {

		BigDecimal valorTotal = new BigDecimal(BigInteger.ZERO, 2);

		valorTotal = valorUnit.multiply(new BigDecimal(valorQtd));

		return valorTotal;
	}

}
