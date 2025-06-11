// Define o pacote onde esta classe está localizada.
package com.bbb_votos.app.rabbitmq.conection;

import consts.RabbitmqConst;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

/**
 * Anotação @Component:
 * Marca esta classe como um "bean" do Spring. Isso significa que o Spring irá
 * criar e gerenciar uma instância (objeto) desta classe automaticamente,
 * permitindo que ela seja usada em outras partes da aplicação através de injeção de dependência.
 */
@Component
public class RabbitmqConection {

    /**
     * Define o nome da exchange que será utilizada.
     * "amq.direct" é uma exchange padrão e pré-declarada no RabbitMQ,
     * que roteia mensagens com base em uma correspondência exata da chave de roteamento (routing key).
     */
    private static final String EXCHANGE_NAME = "amq.direct";

    /**
     * Declara uma dependência da interface AmqpAdmin.
     * Esta interface fornece métodos para criar, modificar e deletar
     * exchanges, filas e bindings de forma programática.
     */
    private AmqpAdmin amqpAdmin;

    /**
     * Construtor da classe.
     * Este é o ponto de Injeção de Dependência. Quando o Spring cria a instância
     * de RabbitmqConection, ele automaticamente fornece (injeta) uma instância
     * de AmqpAdmin, que já está configurada e pronta para uso.
     *
     * @param amqpAdmin A instância de AmqpAdmin fornecida pelo Spring.
     */
    public RabbitmqConection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    /**
     * Método auxiliar para criar um objeto de Fila (Queue).
     * @param queueName O nome da fila a ser criada.
     * @return um objeto Queue configurado.
     * Parâmetros de new Queue(name, durable, exclusive, autoDelete):
     * - durable (true): A fila sobrevive a uma reinicialização do servidor RabbitMQ.
     * - exclusive (false): A fila pode ser acessada por múltiplas conexões.
     * - autoDelete (false): A fila não será deletada quando o último consumidor se desconectar.
     */
    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    /**
     * Método auxiliar para criar um objeto de Exchange Direta.
     * @return um objeto DirectExchange configurado com o nome padrão.
     */
    private DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * Método auxiliar para criar uma Ligação (Binding) entre uma fila e uma exchange.
     * A ligação usa o próprio nome da fila como chave de roteamento (routing key).
     * Isso significa que a exchange "amq.direct" enviará para esta fila todas as
     * mensagens que chegarem com a routing key igual ao nome da fila.
     *
     * @param queue A fila de destino.
     * @param directExchange A exchange de origem.
     * @return um objeto Binding que representa a conexão.
     */
    private Binding binding(Queue queue, DirectExchange directExchange){
        return new Binding(
                queue.getName(),                    // O nome da fila de destino.
                Binding.DestinationType.QUEUE,      // O tipo de destino é uma Fila.
                directExchange.getName(),           // O nome da exchange de origem.
                queue.getName(),                    // A chave de roteamento (routing key).
                null                                // Argumentos adicionais (não usados aqui).
        );
    }

    /**
     * Anotação @PostConstruct:
     * Garante que este método será executado automaticamente pelo Spring
     * uma única vez, logo após a criação da instância da classe e a
     * injeção de todas as dependências (como o amqpAdmin).
     *
     * É aqui que a infraestrutura no RabbitMQ (fila, exchange e a ligação entre eles)
     * é efetivamente criada.
     */
    @PostConstruct
    private void init(){
        // 1. Cria os objetos de configuração em memória usando os métodos auxiliares.
        Queue queueVotes = this.queue(RabbitmqConst.QUEUE_VOTES); // Cria a definição da fila de votos.
        DirectExchange directExchange = this.directExchange(); // Cria a definição da exchange.
        Binding binding = this.binding(queueVotes, directExchange); // Cria a definição da ligação.

        // 2. Usa o AmqpAdmin para criar as estruturas no servidor RabbitMQ.
        // Estes comandos são idempotentes: se a estrutura já existir com as mesmas propriedades, nada acontece.

        // Declara a fila no RabbitMQ.
        this.amqpAdmin.declareQueue(queueVotes);

        // Declara a exchange no RabbitMQ.
        this.amqpAdmin.declareExchange(directExchange);

        // Declara a ligação entre a fila e a exchange no RabbitMQ.
        this.amqpAdmin.declareBinding(binding);
    }
}