package com.minegocio.backend.repository;

import com.minegocio.backend.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    // Búsqueda fulltext (Modo natural)
    @Query(value = "SELECT * FROM faq WHERE MATCH(question, answer) AGAINST (?1 IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Faq> searchFullText(String texto);

    // Opcional: búsqueda booleana (+palabras obligatorias)
    @Query(value = "SELECT * FROM faq WHERE MATCH(question, answer) AGAINST (?1 IN BOOLEAN MODE)", nativeQuery = true)
    List<Faq> searchFullTextBoolean(String texto);

    // Filtro simple por coincidencia en pregunta
    List<Faq> findByQuestionContainingIgnoreCase(String question);

}
