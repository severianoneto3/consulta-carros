package model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAno(@JsonAlias("codigo") String ano,
                    @JsonAlias("nome") String nome) {
    @Override
    public String toString() {
        return "Ano = " + ano + " | Nome = " + nome;
    }
}
