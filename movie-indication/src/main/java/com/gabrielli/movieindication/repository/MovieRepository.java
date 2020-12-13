package com.gabrielli.movieindication.repository;

import com.gabrielli.movieindication.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByWinnerOrderByYear(String winner);

}
