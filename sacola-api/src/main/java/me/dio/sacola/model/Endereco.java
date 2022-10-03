package me.dio.sacola.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Embeddable
@NoArgsConstructor
public class Endereco {

    private  String cep;
    private String complemento;
}
