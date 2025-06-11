package com.bbb_votos.app.controller;
import com.bbb_votos.app.service.RabbitmqService;

import consts.RabbitmqConst;
import dto.ParticipanteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// VotosController.java (na Aplicação 1 - API/Envio)
@RestController
@RequestMapping("votos")
public class VotosController {
    @Autowired
    private RabbitmqService rabbitmqService; // Apenas este aqui para enviar

    @PostMapping
    public ResponseEntity votar(@RequestBody ParticipanteDto participanteDto){
        try {
            this.rabbitmqService.enviarMensagem(RabbitmqConst.QUEUE_VOTES, participanteDto);
            return new ResponseEntity("Voto aceito para processamento assíncrono.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            // Lida com erros ao enviar para a fila
            return new ResponseEntity("Erro ao enfileirar o voto.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
