package com.bbb_consumidor.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Document: Marca esta classe como um documento MongoDB.
 * "participantes": É o nome da "collection" (tabela) onde os dados serão salvos no banco.
 *
 * @Data (Lombok): Gera automaticamente getters, setters, toString(), equals() e hashCode().
 * @NoArgsConstructor (Lombok): Gera um construtor sem argumentos.
 * @AllArgsConstructor (Lombok): Gera um construtor com todos os campos.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "participantes")
public class ParticipanteEntity {

    /**
     * @Id: Marca este campo como a chave primária do documento (_id no MongoDB).
     * O tipo String é comumente usado para que o MongoDB gere um ObjectId automaticamente.
     */
    @Id
    private String id;

    /**
     * @Field: (Opcional) Mapeia o atributo da classe para um campo no documento MongoDB.
     * Útil se o nome no Java for diferente do nome no banco. Se os nomes forem iguais, não é necessário.
     */
    @Field(name = "nome")
    private String name;

    @Field(name = "cpf")
    private String cpf;

    @Field(name = "email")
    private String email;


    @Field(name = "voto")
    private Integer voto;

}