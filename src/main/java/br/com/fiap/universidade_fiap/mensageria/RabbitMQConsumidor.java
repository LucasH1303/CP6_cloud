package br.com.fiap.universidade_fiap.mensageria;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumidor {

	@RabbitListener(queues = RabbitMQConfig.FILA)
	public void lerMensagem(String mensagem) {
		System.out.println("Consumindo mensagem via RabbitMQ: " + mensagem);
	}
	
}
