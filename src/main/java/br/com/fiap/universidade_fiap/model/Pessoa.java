package br.com.fiap.universidade_fiap.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "{validacao.pessoa.nome.vazio}")
	@Size(min = 4, max = 80, message = "{validacao.pessoa.nome.tamanho}")
	private String nome;
	@CPF(message = "{validacao.pessoa.cpf}")
	private String cpf;
	@DateTimeFormat(iso = ISO.DATE)
	@Past(message = "{validacao.pessoa.data_nascimento}")
	private LocalDate dataNascimento;
	@Enumerated(EnumType.STRING)
	private EnumNacionalidade nacionalidade;

	public Pessoa() {

	}

	public Pessoa(Long id, String nome, String cpf, LocalDate dataNascimento, EnumNacionalidade nacionalidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
	}
	
	public void transferirPessoa(Pessoa pessoa) {
		setNome(pessoa.getNome());
		setCpf(pessoa.getCpf());
		setDataNascimento(pessoa.getDataNascimento());
		setNacionalidade(pessoa.getNacionalidade());
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EnumNacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(EnumNacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

}
