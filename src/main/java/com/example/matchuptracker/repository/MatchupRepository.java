package com.example.matchuptracker.repository;

import com.example.matchuptracker.model.Matchup;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchupRepository extends JpaRepository<Matchup, Integer> {
    /**
     * Read the documentation here regarding Property expressions.
     * In this case, findByCreatedBy_Email should find the nested email property on the [User] CreatedBy object.
     * https://docs.spring.io/spring-data/data-jpa/docs/1.1.x/reference/html/
     * 1.3.2.2.1. Property expressions
     * */
    List<Matchup> findByCreatedBy_Email(String email, Sort sort);

    List<Matchup> findByFormat(String format);

    int countByCreatedBy_Email(String email);

    List<Matchup> findByCreatedBy_EmailAndFormat(String email, String format, Sort sort);
}
