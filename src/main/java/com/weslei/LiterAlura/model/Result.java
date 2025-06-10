package com.weslei.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pesquisas")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private List<Auto> authors;
    private List<String> languages;
    private Integer download_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Auto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Auto> authors) {
        this.authors = authors;
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
}
