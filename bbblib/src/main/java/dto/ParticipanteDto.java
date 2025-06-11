package dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor; // Mantenha esta anotação

import java.io.Serializable;

// Serializable = envia a info em sequencia de bytes para a fila, assim ele garante a integridade do dados ao enviar na fila

@Data // Gera getters, setters, toString(), equals(), hashCode()
@NoArgsConstructor // Garante o construtor sem argumentos (fundamental para desserialização de JSON)
@AllArgsConstructor // Gera o construtor com todos os argumentos
public class ParticipanteDto {
    private String nome;
    private String email;
    private String cpf;
    private Integer voto; // Use Integer (classe) em vez de int (primitivo) se o campo puder ser nulo.

}