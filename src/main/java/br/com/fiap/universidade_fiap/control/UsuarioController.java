package br.com.fiap.universidade_fiap.control;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.Funcao;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.FuncaoRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;
import br.com.fiap.universidade_fiap.service.CachingService;

@Controller
public class UsuarioController {
	
	@Autowired
	private FuncaoRepository repF;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private CachingService cache;
	
	@GetMapping("/usuario/novo")
	public ModelAndView retornarCadUsuario() {
		
		ModelAndView mv = new ModelAndView("/usuario/novo");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<Usuario> op = cache.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario_logado", op.get());
		}
		
		mv.addObject("usuario", new Usuario());
		mv.addObject("lista_funcoes", cache.findAllFuncao());
		
		return mv;
		
	}
	
	@PostMapping("/insere_usuario")
	public ModelAndView inserirUsuario(Usuario usuario, @RequestParam(name = "id_funcao") Long id_funcao) {

		usuario.setSenha(encoder.encode(usuario.getSenha()));

		Set<Funcao> funcoes = new HashSet<Funcao>();

		if (id_funcao != null) {

			funcoes.add(cache.findByIdFuncao(id_funcao).orElse(null));

		}

		usuario.setFuncoes(funcoes);

		repU.save(usuario);
		cache.removerCacheUsuario();

		return new ModelAndView("redirect:/index");

	}
	
	
	

}
