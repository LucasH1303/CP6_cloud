package br.com.fiap.universidade_fiap.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.Discente;
import br.com.fiap.universidade_fiap.model.EnumNacionalidade;
import br.com.fiap.universidade_fiap.model.EnumNivel;
import br.com.fiap.universidade_fiap.model.EnumStatus;
import br.com.fiap.universidade_fiap.model.Pessoa;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.DiscenteRepository;
import br.com.fiap.universidade_fiap.repository.PessoaRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;
import br.com.fiap.universidade_fiap.service.CachingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class DiscenteController {

	@Autowired
	private DiscenteRepository repD;
	
	@Autowired
	private PessoaRepository repP;
	
	@Autowired
	private UsuarioRepository repU;
	
	@Autowired
	private CachingService cache;

	@GetMapping("/index")
	public ModelAndView popularIndex() {

		ModelAndView mv = new ModelAndView("/home/index");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<Usuario> op = cache.findByUsername(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("usuario", op.get());
		}
		
		mv.addObject("discentes", cache.findAllDiscente());

		return mv;
	}

	@GetMapping("/pessoa/nova")
	public ModelAndView retornaPagCadPessoa(HttpServletRequest req) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Usuario> op = cache.findByUsername(auth.getName());

		ModelAndView mv = new ModelAndView("/pessoa/nova");

		if(op.isPresent()) {
			mv.addObject("usuario",op.get());
		}
		
		mv.addObject("pessoa", new Pessoa());
		mv.addObject("nacionalidades", EnumNacionalidade.values());
		mv.addObject("uri", req.getRequestURI());

		return mv;
	}
	
	@PostMapping("insere_pessoa")
	public ModelAndView inserirPessoa(@Valid Pessoa pessoa, BindingResult bd) {
		
		if(bd.hasErrors()) {
			
			ModelAndView mv = new ModelAndView("/pessoa/nova");
			mv.addObject("pessoa", pessoa);
			mv.addObject("nacionalidades", EnumNacionalidade.values());
			return mv;
			
		} else {
		
			Pessoa pes_nova = new Pessoa();
			pes_nova.setCpf(pessoa.getCpf());
			pes_nova.setNacionalidade(pessoa.getNacionalidade());
			pes_nova.setNome(pessoa.getNome());
			pes_nova.setDataNascimento(pessoa.getDataNascimento());

			repP.save(pes_nova);
			cache.removerCachePessoa();

			Discente discente = new Discente();
			discente.setPessoa(pes_nova);
			discente.setNivel(EnumNivel.A_DEFINIR);
			discente.setStatus(EnumStatus.A_DEFINIR);
			discente.setRm("123456");

			repD.save(discente);
			cache.removerCacheDiscente();		
			

			return new ModelAndView("redirect:/index");
		}
		
	}
	
	@GetMapping("/discente/detalhes/{id}")
	public ModelAndView exibirDetalhes(HttpServletRequest req, @PathVariable Long id) {
		
		Optional<Discente> op = cache.findByIdDiscente(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/discente/detalhes");
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			Optional<Usuario> op1 = cache.findByUsername(auth.getName());
			
			if(op1.isPresent()) {
				mv.addObject("usuario", op1.get());
			}
			
			mv.addObject("discente", op.get());
			mv.addObject("uri", req.getRequestURI());
			
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
	
	@GetMapping("/discente/editar/{id}")
	public ModelAndView exibirPaginaEdicao(@PathVariable Long id) {
		
		Optional<Discente> op = cache.findByIdDiscente(id);
		
		if(op.isPresent()) {
			
			ModelAndView mv = new ModelAndView("/discente/edicao");
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			Optional<Usuario> op1 = cache.findByUsername(auth.getName());
			
			if(op1.isPresent()) {
				mv.addObject("usuario", op1.get());
			}
			
			mv.addObject("discente", op.get());
			mv.addObject("lista_nac", EnumNacionalidade.values());
			mv.addObject("lista_status", EnumStatus.values());
			mv.addObject("lista_niveis", EnumNivel.values());
			return mv;
			
		} else {
			return new ModelAndView("redirect:/index");
		}
		
	}
	
	@PostMapping("/editar/{id}")
	public ModelAndView editarDiscente(@PathVariable Long id, @Valid Discente discente, 
			BindingResult bd) {
		
		if(bd.hasErrors()) {
			
			ModelAndView mv = new ModelAndView("/discente/edicao");
			mv.addObject("discente", discente);
			mv.addObject("lista_nac", EnumNacionalidade.values());
			mv.addObject("lista_status", EnumStatus.values());
			mv.addObject("lista_niveis", EnumNivel.values());
			return mv;
			
			
		} else {
			Optional<Discente> op = cache.findByIdDiscente(id);
			
			if(op.isPresent()) {
				
				Discente discente_atualizado = op.get();
				discente_atualizado.transferirDiscente(discente);
				repD.save(discente_atualizado);
				cache.removerCacheDiscente();
				return new ModelAndView("redirect:/index");
				
				
			} else {
				return new ModelAndView("redirect:/index");
			}
		}
		
	}
	
	@GetMapping("/discente/remover/{id}")
	public ModelAndView removerDiscente(@PathVariable Long id) {
		Optional<Discente> op = cache.findByIdDiscente(id);
		
		if(op.isPresent()) {
			
			repD.deleteById(id);
			cache.removerCacheDiscente();
			
			return new ModelAndView("redirect:/index");
			
			
		} else {
			return new ModelAndView("redirect:/index");
		}
	}
	

}
