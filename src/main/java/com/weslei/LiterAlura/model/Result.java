package com.weslei.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private List<String> languages;
    private Integer download_count;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Result(DadosBuscado dadosBuscado) {
        this.title = dadosBuscado.title();
        this.languages = dadosBuscado.languages();
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

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
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
        return "Result{" +
                " - title='" + title +
                " - download_count=" + download_count +
                " - languages=" + languages + '}';
    }
}
