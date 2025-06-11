package com.bbb_consumidor.app.consumer;


import com.bbb_consumidor.app.service.ParticipanteService;
import com.bbb_consumidor.app.Exception.CpfJaExistenteException;

import consts.RabbitmqConst;
import dto.ParticipanteDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotesConsumer {

    // Injeta o ParticipanteService para processar o voto
    @Autowired
    private ParticipanteService participanteService;


    @RabbitListener(queues = RabbitmqConst.QUEUE_VOTES)
    public void consumer(ParticipanteDto participanteDto){
        try {
            System.out.println("Mensagem de voto recebida para CPF: " + participanteDto.getCpf());
            System.out.println("Voto: " + participanteDto.getVoto());


            participanteService.processarVoto(participanteDto);

            System.out.println("Voto processado e salvo/atualizado com sucesso para CPF: " + participanteDto.getCpf());

        } catch (CpfJaExistenteException e) {

            System.err.println("ERRO: Voto duplicado para CPF: " + participanteDto.getCpf() + ". Mensagem: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("ERRO INESPERADO ao processar voto para CPF: " + participanteDto.getCpf() + ". Causa: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha no processamento do voto: " + e.getMessage(), e);
        }
    }
}