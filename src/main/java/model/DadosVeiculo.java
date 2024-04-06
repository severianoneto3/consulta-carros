package model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(@JsonAlias("Valor") String preco,
                           @JsonAlias("Marca") String marca,
                           @JsonAlias("Modelo") String modelo,
                           @JsonAlias("AnoModelo") String ano,
                           @JsonAlias("Combustivel") String combustivel) {
    @Override
    public String toString() {
        return "Dados do veículo: " +
                "Preço: " + preco +
                " | Marca: " + marca +
                " | Modelo: " + modelo +
                " | Ano: " + ano +
                " | Combustível: " + combustivel;
    }
}
