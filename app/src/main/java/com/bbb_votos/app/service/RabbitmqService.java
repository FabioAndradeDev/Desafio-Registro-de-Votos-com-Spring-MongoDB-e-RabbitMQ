package com.bbb_votos.app.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {
    //reteia a coonexão com o rabbitMQ que configurei
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(String queueName, Object mensagem ){
        //  queueName = RoutingKey && mensagem é mensagem msm, mas ali tô usando a chave de rotiamento como o nome da fila msm
        this.rabbitTemplate.convertAndSend(queueName, mensagem);
    }
}
