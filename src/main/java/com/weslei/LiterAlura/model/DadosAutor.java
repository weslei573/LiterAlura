package com.weslei.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(@JsonAlias("title") String titulo,
                         @JsonAlias("name") String autor,
                         @JsonAlias("languages") String idioma,
                         @JsonAlias("download_count") Double numeroDownload) {
}

