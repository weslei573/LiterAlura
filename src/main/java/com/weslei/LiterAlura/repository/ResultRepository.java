package com.weslei.LiterAlura.repository;

import com.weslei.LiterAlura.model.DadosBuscado;
import com.weslei.LiterAlura.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByTitleContainsIgnoreCase(String title);
}
