package br.com.fiap.universidade_fiap.mensageria;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProdutor {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void enviarMensagem(String mensagem) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.ROTEADOR, RabbitMQConfig.CHAVE_ROTA,mensagem);
		System.out.println("Enviando mensagem via RabbitMQ: " + mensagem);
	}
	
}
