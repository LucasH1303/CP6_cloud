package br.com.fiap.universidade_fiap.mensageria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProdutor {
	
	public static final String TOPICO = "Fiap-Topico";
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	public void enviarMensagem(String mensagem) {
		kafkaTemplate.send(TOPICO,mensagem);
		System.out.println("Enviando mensagem via Kafka: " + mensagem);
		
	}

}
