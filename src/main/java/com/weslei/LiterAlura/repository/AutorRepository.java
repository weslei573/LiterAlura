package com.weslei.LiterAlura.repository;

import com.weslei.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNameContainsIgnoreCase(String name);
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoMorteGreaterThanEqual(Integer anoInicial, Integer anoFinal);
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoMorteIsNull(Integer anoInicial);

}
