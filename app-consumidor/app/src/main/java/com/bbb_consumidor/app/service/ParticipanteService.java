package com.bbb_consumidor.app.service;

import com.bbb_consumidor.app.entity.ParticipanteEntity;
import com.bbb_consumidor.app.repository.ParticipanteRepository;
import com.bbb_consumidor.app.util.HashUtil;
import dto.ParticipanteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    @Autowired
    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;

    }


    @Transactional
    public ParticipanteEntity processarVoto(ParticipanteDto participanteDto) {

        String cpfSha256 = HashUtil.generateSha256(participanteDto.getCpf());


        ParticipanteEntity participanteExistente = participanteRepository.findByCpf(cpfSha256).orElse(null);


        String nameSha256 = HashUtil.generateSha256(participanteDto.getNome());
        String emailSha256 = HashUtil.generateSha256(participanteDto.getEmail());

        ParticipanteEntity participanteParaSalvar;

        if (participanteExistente != null) {

            participanteParaSalvar = participanteExistente;
            System.out.println("CPF existente detectado. Sobrescrevendo voto para: " + participanteDto.getCpf());

            participanteParaSalvar.setName(nameSha256);
            participanteParaSalvar.setEmail(emailSha256);
            participanteParaSalvar.setVoto(participanteDto.getVoto());

        } else {

            participanteParaSalvar = new ParticipanteEntity();
            System.out.println("Novo CPF detectado. Salvando voto para: " + participanteDto.getCpf());
            participanteParaSalvar.setCpf(cpfSha256);
            participanteParaSalvar.setName(nameSha256);
            participanteParaSalvar.setEmail(emailSha256);
            participanteParaSalvar.setVoto(participanteDto.getVoto());
        }

        ParticipanteEntity usuarioSalvo = participanteRepository.save(participanteParaSalvar);
        return usuarioSalvo;
    }
}