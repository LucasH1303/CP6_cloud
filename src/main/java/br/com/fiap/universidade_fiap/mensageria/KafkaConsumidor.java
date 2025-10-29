package br.com.fiap.universidade_fiap.mensageria;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumidor {
	
	@KafkaListener(topics = KafkaProdutor.TOPICO, groupId = "fiap-grupo")
	public void consumirMensagem(String mensagem) {
		System.out.println("Consumindo mensagem via Kafka: " + mensagem);
	}

}
