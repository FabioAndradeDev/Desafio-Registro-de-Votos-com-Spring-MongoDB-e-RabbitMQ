package com.bbb_consumidor.app.repository;

import com.bbb_consumidor.app.entity.ParticipanteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ParticipanteRepository extends MongoRepository<ParticipanteEntity,Long> {

    boolean existsByCpf(String cpf);
    Optional<ParticipanteEntity> findByCpf(String cpf);

}
