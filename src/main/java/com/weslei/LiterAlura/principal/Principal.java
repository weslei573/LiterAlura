package com.weslei.LiterAlura.principal;

import com.weslei.LiterAlura.model.Autor;
import com.weslei.LiterAlura.model.DadosLivro;
import com.weslei.LiterAlura.service.ConsumoApi;
import com.weslei.LiterAlura.service.ConverteDados;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String GUTENDEX_API = "https://gutendex.com/books/?search=";

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    ---------------------------------------------
                    Escolha o número de sua opção:
                    
                    1- Buscar livro pelo titulo
                    2- Listar livros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos em um determinado ano
                    5- Litar livros em um determinado idioma
                    
                    0- Sair
                    ---------------------------------------------
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarEmDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o título do livro para busca: ");
        var tituloLivro = leitura.nextLine();

        try {
            var tituloCodificado = tituloLivro.replace(" ", "%20");
            var json = consumo.obterDados(GUTENDEX_API + tituloCodificado);

            if (json == null) {
                System.out.println("Não foi possível obter dados da API. Verifique sua conexão.");
                return;
            }

            DadosLivro dadosLivros = conversor.obterDados(json, DadosLivro.class);
            //System.out.println(dadosLivros.results());

            if (dadosLivros.results().isEmpty()) {
                System.out.println("Nenhum livro encontrado com esse título.");
                return;
            }

            dadosLivros.results().forEach(l -> {
                System.out.println("\nTítulo: " + l.title());
                System.out.println("Autor(es): " +
                        l.authors().stream()
                                .map(Autor::name)
                                .collect(Collectors.joining(", ")));
                System.out.println("Idioma: " + l.languages());
                System.out.println("Downloads: " + l.download_count());
            });
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }

    }

    private void listarLivrosRegistrados() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivos() {
    }

    private void listarEmDeterminadoIdioma() {
    }
}
