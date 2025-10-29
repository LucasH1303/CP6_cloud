package br.com.fiap.universidade_fiap.model;

public enum EnumNivel {

	TECNICO("Técnico"), 
	BACHARELADO("Bacharelado"), 
	TECNOLOGO("Tecnólogo"), 
	MBA("MBA"),
	MESTRADO("Mestrado"), 
	DOUTORADO("Doutorado"), 
	ESPECIALIZACAO("Especialização"), 
	A_DEFINIR("A definir");

	private final String descricao;

	EnumNivel(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
