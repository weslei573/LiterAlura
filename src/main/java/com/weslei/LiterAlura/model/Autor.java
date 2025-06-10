package com.weslei.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private Integer anoNascimento;
    private Integer anoMorte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Result> results = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dadosAutor) {
        this.name = dadosAutor.name();
        this.anoNascimento = dadosAutor.birth_year();
        this.anoMorte = dadosAutor.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        results.forEach(l -> l.setAutor(this));
        this.results = results;
    }

    public void adicionarResult(Result result) {
        this.results.add(result);
        result.setAutor(this);
    }

    @Override
    public String toString() {
        return "Autor: " + name +
                ", Ano Nascimento: " + anoNascimento +
                ", Ano Morte: " + (anoMorte != null ? anoMorte : "N/A");
    }
}
