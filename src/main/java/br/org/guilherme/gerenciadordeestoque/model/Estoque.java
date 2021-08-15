package br.org.guilherme.gerenciadordeestoque.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_estoque")
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String sku;
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	@NotNull
	private Integer quantidade;
	
	
	@NotNull
	@Column(columnDefinition = "decimal(5,2)")
	private BigDecimal valorUnit;
	

	@Column(columnDefinition = "decimal(6,2)")
	private BigDecimal valorTotal;
	
	private Integer leadtime;
	
	private Integer tempoEntrega;
	
	private Integer estoqueMinimo;
	
	private Integer tempoConsumoPadrao;

	
	@Size(max = 100)
	private String observacao;
	
	@OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("estoque")
	private List<EntradaSaida> movimentacao;
	
	
	@NotNull
	@ManyToOne
	@JsonIgnoreProperties("estoque")
	private Categoria categoria;
	
	
	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnit() {
		return valorUnit;
	}

	public void setValorUnit(BigDecimal valorUnit) {
		this.valorUnit = valorUnit;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;

	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}	

	public Integer getLeadtime() {
		return leadtime;
	}

	public void setLeadtime(Integer leadtime) {
		this.leadtime = leadtime;
	}

	public List<EntradaSaida> getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(List<EntradaSaida> movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getTempoConsumoPadrao() {
		return tempoConsumoPadrao;
	}

	public void setTempoConsumoPadrao(Integer tempoConsumoPadrao) {
		this.tempoConsumoPadrao = tempoConsumoPadrao;
	}

	public Integer getTempoEntrega() {
		return tempoEntrega;
	}

	public void setTempoEntrega(Integer tempoEntrega) {
		this.tempoEntrega = tempoEntrega;
	}
	
	
	
	
}
