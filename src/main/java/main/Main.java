package main;

import model.Dados;
import model.DadosAno;
import model.DadosModelo;
import model.DadosVeiculo;
import services.ConsultaAPI;
import services.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner input = new Scanner(System.in);
    private ConsultaAPI consultaAPI = new ConsultaAPI();
    private ConverteDados converter = new ConverteDados();

    public void selecionarVeiculo() {
        System.out.println("Digite o número correspondente a escolha do seu veículo:");
        System.out.println("1 - Carros | 2 - Motos | 3 - Caminhões");
        String caseVeiculo = input.nextLine();
        String veiculo = "";

        do {
            switch (caseVeiculo) {
                case "1":
                    veiculo = "carros";
                    break;
                case "2":
                    veiculo = "motos";
                    break;
                case "3":
                    veiculo = "caminhoes";
                    break;
                default:
                    System.out.println("Número/caractere inválido");
                    caseVeiculo = input.nextLine();
            }
        } while (veiculo.equals(""));

        String endpoint = URL_BASE + veiculo + "/marcas";
        String json = consultaAPI.consultar(endpoint);
        List<Dados> dadosMarcas = converter.obterLista(json, Dados.class);
        Map<String,String> mapMarcas = dadosMarcas.stream()
                .collect(Collectors.toMap(Dados::id,Dados::nome));

        dadosMarcas.forEach(System.out::println);

        System.out.println("\nDigite o nome da marca que deseja buscar:");
        String nomeMarca = input.nextLine();
        String idMarca = verificacaoNomeMarca(mapMarcas,nomeMarca);

        while (idMarca == null) {
            System.out.println("A marca informada não está contida na lista. Por favor insira uma marca válida.");
            nomeMarca = input.nextLine();
            idMarca = verificacaoNomeMarca(mapMarcas,nomeMarca);
        }

        endpoint = endpoint + "/" + idMarca + "/modelos";
        json = consultaAPI.consultar(endpoint);
        DadosModelo modelo = converter.obterDados(json, DadosModelo.class);
        Map<String,String> mapModelos = modelo.modelos().stream()
                        .collect(Collectors.toMap(Dados::id,Dados::nome));

        modelo.modelos().forEach(System.out::println);

        System.out.println("\nInforme o nome do modelo do carro desejado:");
        String nomeModelo = input.nextLine();
        Map<String,String> resultadoModelo = verificacaoNomeModelo(mapModelos,nomeModelo);

        while (resultadoModelo.isEmpty()) {
            System.out.println("O modelo informado não está contido na lista. Por favor insira um modelo válido.");
            nomeModelo = input.nextLine();
            resultadoModelo = verificacaoNomeModelo(mapModelos,nomeModelo);
        }

        resultadoModelo.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(r -> System.out.println("Id = " + r.getKey() + " | Nome = " + r.getValue()));

        System.out.println("\nInforme o ID do modelo do carro desejado:");
        String idModelo = input.nextLine();

        endpoint = endpoint + "/" + idModelo + "/anos";
        json = consultaAPI.consultar(endpoint);
        List<DadosAno> anos = converter.obterLista(json, DadosAno.class);
//        anos.forEach(System.out::println);
        Map<String,String> mapAnos = anos.stream()
                .collect(Collectors.toMap(DadosAno::ano,DadosAno::nome));
        Stream<String> anosSet = mapAnos.keySet().stream();
        List<String> anosConsulta = anosSet.toList();
        List<DadosVeiculo> veiculos = new ArrayList<>();

        endpoint = endpoint + "/";

        for (int i = 0; i < anos.size(); i++) {
            String enderecoFinal = endpoint + anosConsulta.get(i);
            json = consultaAPI.consultar(enderecoFinal);
            DadosVeiculo dadosVeiculo = converter.obterDados(json, DadosVeiculo.class);
            veiculos.add(dadosVeiculo);
        }

        System.out.println("\n");
        veiculos.stream()
                .sorted(Comparator.comparing(DadosVeiculo::ano).reversed())
                .forEach(System.out::println);

//        System.out.println("\nInforme o ano do carro desejado:");
//        String ano = input.nextLine();
//        String anoVerificado = verificacaoAno(mapAnos,ano);
//
//        while (anoVerificado == null) {
//            System.out.println("O ano informado não está contido na lista. Por favor insira um ano válido.");
//            ano = input.nextLine();
//            anoVerificado = verificacaoNomeMarca(mapAnos,ano);
//        }



    }

    private String verificacaoNomeMarca(Map<String, String> mapa, String nome) {
        return mapa.entrySet().stream()
                .filter(n -> n.getValue().equalsIgnoreCase(nome))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private Map<String,String> verificacaoNomeModelo(Map<String, String> mapa, String nome) {

        return mapa.entrySet().stream()
                .filter(n -> n.getValue().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String verificacaoAno(Map<String, String> mapa, String ano) {
        return mapa.entrySet().stream()
                .filter(n -> n.getKey().contains(ano))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}