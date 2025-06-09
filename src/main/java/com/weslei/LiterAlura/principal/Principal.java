package com.weslei.LiterAlura.principal;

import com.weslei.LiterAlura.model.DadosAutor;
import com.weslei.LiterAlura.service.ConsumoApi;
import com.weslei.LiterAlura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados converte = new ConverteDados();

    private final String ENDERECO = "https://gutendex.com/books?search=";

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0){
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
        System.out.println("Insira o nome do livro que você deseja procurar");
        var nome = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nome.replace(" ", "%20"));
        DadosAutor dados = converte.obterDados(json, DadosAutor.class);
        System.out.println(dados);

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
