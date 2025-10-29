package br.com.fiap.universidade_fiap.model;

public enum EnumNacionalidade {

	JAPONESA("Japonesa"), BRASILEIRA("Brasileira"), 
	NORTE_AMERICANA("Norte-Americana"), ARGENTINA("Argentina"), CHILENA("Chilena"), 
	ITALIANA("Italiana");

	private final String descricao;

	EnumNacionalidade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
