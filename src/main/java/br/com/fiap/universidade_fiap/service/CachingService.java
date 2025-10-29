package br.com.fiap.universidade_fiap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.fiap.universidade_fiap.model.Discente;
import br.com.fiap.universidade_fiap.model.Funcao;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.DiscenteRepository;
import br.com.fiap.universidade_fiap.repository.FuncaoRepository;
import br.com.fiap.universidade_fiap.repository.PessoaRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@Service
public class CachingService {

	@Autowired
	private DiscenteRepository repD;
	@Autowired
	private FuncaoRepository repF;
	@Autowired
	private PessoaRepository repP;
	@Autowired
	private UsuarioRepository repU;

	@Cacheable(value = "findAllDiscente")
	public List<Discente> findAllDiscente() {
		return repD.findAll();
	}

	@Cacheable(value = "findByUsername", key = "#username")
	public Optional<Usuario> findByUsername(String username) {
		return repU.findByUsername(username);
	}

	@Cacheable(value = "findByIdDiscente", key = "#id")
	public Optional<Discente> findByIdDiscente(Long id) {
		return repD.findById(id);
	}

	@Cacheable(value = "findAllFuncao")
	public List<Funcao> findAllFuncao() {
		return repF.findAll();
	}

	@Cacheable(value = "findByIdFuncao", key = "#id")
	public Optional<Funcao> findByIdFuncao(Long id) {
		return repF.findById(id);
	}

	@CacheEvict(value = { "findAllDiscente", "findByIdDiscente" }, allEntries = true)
	public void removerCacheDiscente() {
		System.out.println("Limpando cache dos discentes");
	}

	@CacheEvict(value = {"findAllFuncao","findByIdFuncao"}, allEntries = true)
	public void removerCacheFuncao() {
		System.out.println("Limpando cache das funções");
	}

	public void removerCachePessoa() {
		System.out.println("Limpando cache das pessoas");
	}

	@CacheEvict(value = { "findByUsername" }, allEntries = true)
	public void removerCacheUsuario() {
		System.out.println("Limpando cache dos usuários");
	}

}
