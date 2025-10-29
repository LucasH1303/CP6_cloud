package br.com.fiap.universidade_fiap.mensageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String FILA = "universidade-fiap-fila";
	public static final String ROTEADOR = "universidade-fiap-roteador";
	public static final String CHAVE_ROTA = "universidade-fiap-chave";

	// Fila
	@Bean
	public Queue queue() {
		Queue fila = new Queue(FILA, true);
		return fila;
	}

	// Roteador
	@Bean
	public DirectExchange directExchange() {
		DirectExchange roteador = new DirectExchange(ROTEADOR);
		return roteador;
	}

	// Associar uma fila a um roteador via uma chave de rota
	@Bean
	public Binding binding(Queue queue, DirectExchange directExchange) {
		return BindingBuilder.bind(queue).to(directExchange).with(CHAVE_ROTA);
	}

}
