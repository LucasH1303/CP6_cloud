package br.com.fiap.universidade_fiap.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "discente")
public class Discente  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	@Valid
	private Pessoa pessoa;
	@Size(min = 6, max = 6, message = "{validacao.discente.rm}")
	private String rm;
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	@Enumerated(EnumType.STRING)
	private EnumNivel nivel;

	public Discente() {

	}

	public Discente(Long id, Pessoa pessoa, String rm, EnumStatus status, EnumNivel nivel) {
		super();
		this.id = id;
		this.pessoa = pessoa;
		this.rm = rm;
		this.status = status;
		this.nivel = nivel;
	}
	
	public void transferirDiscente(Discente discente) {
		setRm(discente.getRm());
		setStatus(discente.getStatus());
		setNivel(discente.getNivel());
		getPessoa().transferirPessoa(discente.getPessoa());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public EnumNivel getNivel() {
		return nivel;
	}

	public void setNivel(EnumNivel nivel) {
		this.nivel = nivel;
	}

}
