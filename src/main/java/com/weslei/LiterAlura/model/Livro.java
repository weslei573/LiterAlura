package com.weslei.LiterAlura.model;

import java.util.List;

public class Livro {
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        private String title;
        private List<Autor> authors;
        private List<String> languages;
        private Integer download_count;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Autor> getAuthors() {
            return authors;
        }

        public void setAuthors(List<Autor> authors) {
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

    public static class Autor {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}