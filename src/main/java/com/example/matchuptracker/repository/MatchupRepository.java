package com.example.matchuptracker.repository;

import com.example.matchuptracker.model.Matchup;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchupRepository extends JpaRepository<Matchup, Integer> {
    List<Matchup> findByCreatedByEmail(String email, Sort sort);

    List<Matchup> findByFormat(String format);
}
