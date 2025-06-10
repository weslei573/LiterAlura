package com.weslei.LiterAlura.principal;

import com.weslei.LiterAlura.model.*;
import com.weslei.LiterAlura.repository.AutorRepository;
import com.weslei.LiterAlura.repository.ResultRepository;
import com.weslei.LiterAlura.service.ConsumoApi;
import com.weslei.LiterAlura.service.ConverteDados;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String GUTENDEX_API = "https://gutendex.com/books/?search=";
    private List<DadosBuscado> dadosBuscados = new ArrayList<>();

    private ResultRepository resultRepository;
    private AutorRepository autorRepository;

    public Principal(ResultRepository resultRepository, AutorRepository autorRepository){
        this.resultRepository = resultRepository;
        this.autorRepository = autorRepository;
    }

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

    @Transactional
    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o título do livro para busca: ");
        var tituloLivro = leitura.nextLine();

        Optional<Result> resultExistente = resultRepository.findByTitleContainsIgnoreCase(tituloLivro);
        if (resultExistente.isPresent()) {
            System.out.println("\n--- Livro já registrado no banco de dados ---");
            System.out.println(resultExistente.get());
            return;
        }
        try {
            var tituloCodificado = tituloLivro.replace(" ", "%20");
            var json = consumo.obterDados(GUTENDEX_API + tituloCodificado);

            if (json == null || json.contains("\"count\":0")) {
                System.out.println("Não foi possível obter dados da API. Verifique sua conexão.");
                return;
            }

            DadosLivro dadosLivros = conversor.obterDados(json, DadosLivro.class);

            if (dadosLivros.results().isEmpty()) {
                System.out.println("Nenhum livro encontrado com esse título.");
                return;
            }

            DadosBuscado dadosBuscado = dadosLivros.results().get(0);

            Autor autor = null;
            if (!dadosBuscado.authors().isEmpty()) {
                DadosAutor dadosAutorApi = dadosBuscado.authors().get(0); // Pega o primeiro autor da API
                Optional<Autor> autorExistente = autorRepository.findByNameContainsIgnoreCase(dadosAutorApi.name());

                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(dadosAutorApi);
                    autorRepository.save(autor); // Salva o novo autor
                    System.out.println("Autor '" + autor.getName() + "' salvo no banco de dados.");
                }
            }

            Result result = new Result();
            result.setTitle(dadosBuscado.title());
            result.setIdiomas(dadosBuscado.languages());
            result.setDownload_count(dadosBuscado.download_count());
            result.setAutor(autor);

            resultRepository.save(result);

            System.out.println("\n--- Livro e Autor salvos com sucesso! ---");
            System.out.println("Livro: " + result.getTitle());
            System.out.println("Autor: " + (autor != null ? autor.getName() : "N/A"));
            System.out.println("Downloads: " + result.getDownload_count());
            System.out.println("Idiomas: " + result.getIdiomas());


        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar/salvar o livro: " + e.getMessage());
            e.printStackTrace(); // Para debug, remova em produção
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
