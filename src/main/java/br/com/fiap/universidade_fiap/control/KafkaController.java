package br.com.fiap.universidade_fiap.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.mensageria.KafkaProdutor;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class KafkaController {
	
	@Autowired
	private KafkaProdutor kafkaProdutor;
	
	@GetMapping("/kafka")
	public ModelAndView retornarPagKafka(HttpServletRequest req) {
		
		ModelAndView mv = new ModelAndView("/kafka/mensagem");
		mv.addObject("uri", req.getRequestURI());
		
		return mv;
		
	}
	
	@PostMapping("/enviar_mensagem_kafka")
	public ModelAndView enviarMensagemKafka(HttpServletRequest req,
			@RequestParam(name = "mensagem") String mensagem) {
		kafkaProdutor.enviarMensagem(mensagem);
		ModelAndView mv = new ModelAndView("/kafka/mensagem");
		mv.addObject("uri", req.getRequestURI());
		mv.addObject("sucesso", "Mensagem enviada com sucesso!");
		return mv;
	}
	
	
	

}
