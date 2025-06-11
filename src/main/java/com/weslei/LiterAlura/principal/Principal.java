package com.weslei.LiterAlura.principal;

import com.weslei.LiterAlura.model.*;
import com.weslei.LiterAlura.repository.AutorRepository;
import com.weslei.LiterAlura.repository.ResultRepository;
import com.weslei.LiterAlura.service.ConsumoApi;
import com.weslei.LiterAlura.service.ConverteDados;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String GUTENDEX_API = "https://gutendex.com/books/?search=";
    private List<DadosBuscado> dadosBuscados = new ArrayList<>();

    private List<Result> results = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    private ResultRepository resultRepository;
    private AutorRepository autorRepository;

    public Principal(ResultRepository resultRepository, AutorRepository autorRepository) {
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
        System.out.println("Insira o nome do livro que vocẽ deseja procurar: ");
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
        results = resultRepository.findAll();
        if (results.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
        } else {
            results.stream().forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor resgitrado.");
        } else {
            autores.forEach(a -> {
                System.out.println(a);

                System.out.println("Livros:  " + a.getResults().stream()
                        .map(Result::getTitle)
                        .collect(Collectors.joining(", ")) + "\n");

            });
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano para verificar autores vivos: ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autorVivo = autorRepository.findByAnoNascimentoLessThanEqualAndAnoMorteGreaterThanEqual(ano, ano);
        autorVivo.addAll(autorRepository.findByAnoNascimentoLessThanEqualAndAnoMorteIsNull(ano));

        if (autorVivo.isEmpty()){
            System.out.println("Nenhum autor vivo encontrado no ano " + ano + ".");
        } else {
            System.out.println("\n ------ Autor Vivo em " + ano + "------");
            autorVivo.forEach(System.out::println);
        }
    }

    private void listarEmDeterminadoIdioma() {
        System.out.println("""
                Digite o idioma para busca: 
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var idioma = leitura.nextLine();

        List<Result> livrosPorIdioma = resultRepository.findAll().stream()
                .filter(r -> r.getIdiomas() != null && r.getIdiomas().contains(idioma.toLowerCase()))
                .collect(Collectors.toList());

        if (livrosPorIdioma.isEmpty()){
            System.out.println(" Não existem livros nesse idioma '" + idioma + "' no banco de dados.");
        } else {
            System.out.println("\n ------ Livros no idioma " + idioma + "------");
            livrosPorIdioma.forEach(System.out::println);
        }
    }
}
