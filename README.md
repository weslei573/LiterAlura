https://github.com/user-attachments/assets/496778fa-0164-470d-a424-0108d3652412

<p align="center">
<img loading="lazy" src="http://img.shields.io/static/v1?label=STATUS&message=%20FINALIZADO&color=GREEN&style=for-the-badge"/>
</p>

# Resumo do projeto
Desenvolver um Catálogo de Livros que ofereça interação textual (via console) com os usuários, proporcionando no mínimo 5 opções de interação. 
Os livros serão buscados através de uma API específica. As informações sobre a API e as opções de interação com o usuário serão detalhadas na coluna “Backlog”/”Pronto para iniciar”

## 🔨 Funcionalidades do projeto

- `Funcionalidade 1` `Configuração do ambiente Java`:  Nesta primeira fase, mergulharemos na configuração do ambiente de desenvolvimento Java para o nosso desafio de construção do LiterAlura. Certifique-se de ter os seguintes programas, arquivos e versões:
  - Java JDK: versão: 17 em diante - Download the Latest Java LTS Free
  - Maven: versão 4 em diante
  - Spring: versão 3.2.3 - https://start.spring.io/
  - Postgres: versão16 em diante - PostgreSQL: Downloads
  - IDE (Ambiente de desenvolvimento integrado) IntelliJ IDEA- opcional - Baixe o IntelliJ IDEA – O principal IDE para Java e Kotlin.

- `Funcionalidade 2` `Conhecendo a API para trazer os dados`: A API Gutendex é um catálogo com informações sobre os mais de 70 mil livros presentes no Project Gutenberg (biblioteca online gratuita).
   - Link da API: https://gutendex.com/
   - Repositório da API*: https://github.com/garethbjohnson/gutendex

- `Funcionalidade 3` `Construindo uma solicitação à API`:
   - Constuindo o Cliente para Requisições (HttpClient)
   - Construindo a Solicitação (HttpRequest)
   - Construindo a Resposta (HttpResponse)
     
- `Funcionalidade 4` `Analizando a resposta em formato JSON`: Na quarta fase do nosso desafio, mergulharemos na análise da resposta JSON utilizando a biblioteca Jackson em Java. A manipulação de dados JSON é essencial, já que a maioria das respostas das API se apresentam neste formato.
    - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core

- `Funcionalidade 5` `Convertendo os dados`:
    - Nesta etapa, realizaremos as conversões com os dados de livros e autores, agora que contamos com a informação em nosso poder.
    - Experimente utilizar as classes Java para receber os dados obtidos através da API, transformar os atributos do corpo JSON a uma classe Java e mostrar os resultados.
    - É fundamental criar métodos específicos para manejar esses dados, o que fará com que o código seja mais modular e fácil de entender, como os métodosgetters, setters and toString().
![Captura de tela de 2025-06-10 20-15-43](https://github.com/user-attachments/assets/1600969c-251d-47c2-9560-77820b38a47a)

- `Funcionalidade 6` `Interagindo com o usuário`: Nesta etapa do desafio, entraremos na interação com o usuário. O método Main deve implementar a interface CommandLineRunner e seu método run(), em que você deverá chamar um método para exibir o menu.
 Neste método, você deve criar um laço de repetição para apresentar ao usuário as opções de inserção e consulta.
 O usuário deverá selecionar um número que corresponderá à opção numérica e proporcionar os dados que a aplicação receberá, utilizando a classe Scanner para capturar a entrada do usuário.

## ✔️ Técnicas e tecnologias utilizadas

- ``Java``
- ``InteliJ IDEA``
- ``Maven (Initializr utiliza a versão 4)``
- ``Spring Boot (versão 3.2.3)``
- ``Projeto em JAR``
- ``Spring Data JPA``
- ``Postgres Driver``
- ``API GUTENDEX ``
- ``Jackson``

