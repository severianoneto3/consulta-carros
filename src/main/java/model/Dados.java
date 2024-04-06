package model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(@JsonAlias("codigo") String id,
                    @JsonAlias("nome") String nome) {
    @Override
    public String toString() {
        return "Id = " + id + " | Nome = " + nome;
    }
}
