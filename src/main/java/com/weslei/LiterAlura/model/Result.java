package com.weslei.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "idioma")
    private List<String> idiomas;
    private Integer download_count;

    public Result() {
    }

    public Result(DadosBuscado dadosBuscado) {
        this.title = dadosBuscado.title();
        if (!dadosBuscado.authors().isEmpty()) {
            //this.autor = new Autor(dadosBuscado.authors().get(0));
        }
        this.idiomas = dadosBuscado.languages();
        this.download_count = dadosBuscado.download_count();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "------- LIVRO -------" + "\n" +
                " Titulo: " + title + "\n" +
                " Autor: " + (autor != null ? autor.getName() : "N/A") + "\n" +
                " Downloads: " + download_count + "\n" +
                " Idioma: " + idiomas + "\n" +
                "-------------------" + "\n\n";
    }
}
