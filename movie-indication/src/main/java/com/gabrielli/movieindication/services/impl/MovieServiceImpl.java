package com.gabrielli.movieindication.services.impl;

import com.gabrielli.movieindication.dto.MinMaxIntervalDTO;
import com.gabrielli.movieindication.dto.MovieWinnerDTO;
import com.gabrielli.movieindication.entity.Movie;
import com.gabrielli.movieindication.repository.MovieRepository;
import com.gabrielli.movieindication.services.MovieService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private List<Movie> saveAll(List<Movie> movie) {
        return movieRepository.saveAll(movie);
    }

    private void deleteAll() {
        movieRepository.deleteAll();
    }

    @PostConstruct
    private void readMovies() {
        deleteAll();
        List<Movie> movies = new ArrayList<>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath()));
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withSkipLines(1)
                        .withCSVParser(parser)
                        .build();
        ) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                Movie movie = new Movie();
                movie.setYear(new Integer(record[0]));
                movie.setTitle(record[1]);
                movie.setStudios(record[2]);
                movie.setProducers(record[3]);
                movie.setWinner(record[4]);
                movies.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveAll(movies);
    }

    public String filePath() {
        return "src/test/resources/movielist.csv";
    }

    @Override
    public MinMaxIntervalDTO getWinnerMinMax() {
        List<Movie> movies = movieRepository.findAllByWinnerOrderByYear("yes");
        return setMinMax(movies);
    }

    private List<MovieWinnerDTO> getProducerByMinInterval(List<Movie> movies) {
        Map<String, MovieWinnerDTO> winner = new HashMap<>();
        Integer dif = 0;
        Integer minInterval = 9999;

        for (Movie movie : movies) {
            for (String producer : movie.getProducers().replace(" and ", ",").split(",")) {
                if (producer.trim().isEmpty()) {
                    continue;
                }
                if (winner.containsKey(producer)) {
                    MovieWinnerDTO movieWinnerDTO = winner.get(producer);
                    if (movieWinnerDTO.getFollowingWin() == null) {
                        movieWinnerDTO.setFollowingWin(movie.getYear());
                        dif = movieWinnerDTO.getFollowingWin() - movieWinnerDTO.getPreviousWin();
                        movieWinnerDTO.setInterval(dif);
                        winner.put(producer, movieWinnerDTO);
                    } else if (movieWinnerDTO.getInterval() > (movie.getYear() - movieWinnerDTO.getFollowingWin())){
                        movieWinnerDTO.setPreviousWin(movieWinnerDTO.getFollowingWin());
                        movieWinnerDTO.setFollowingWin(movie.getYear());
                        dif = movieWinnerDTO.getFollowingWin() - movieWinnerDTO.getPreviousWin();
                        movieWinnerDTO.setInterval(dif);
                        winner.put(producer, movieWinnerDTO);
                    }
                    if (dif < minInterval) {
                        minInterval = dif;
                    }

                } else {
                    MovieWinnerDTO movieWinnerDTO = new MovieWinnerDTO();
                    movieWinnerDTO.setProducer(producer);
                    movieWinnerDTO.setPreviousWin(movie.getYear());
                    movieWinnerDTO.setInterval(9999);
                    winner.put(producer, movieWinnerDTO);
                }
            }
        }
        return getProducersInterval(winner, minInterval);
    }

    private List<MovieWinnerDTO> getProducerByMaxInterval(List<Movie> movies) {
        Map<String, MovieWinnerDTO> winner = new HashMap<>();
        Integer dif = 0;
        Integer maxInterval = 0;

        for (Movie movie : movies) {
            for (String producer : movie.getProducers().replace(" and ", ",").split(",")) {
                if (producer.trim().isEmpty()) {
                    continue;
                }
                if (winner.containsKey(producer)) {
                    MovieWinnerDTO movieWinnerDTO = winner.get(producer);
                    if (movieWinnerDTO.getFollowingWin() == null) {
                        movieWinnerDTO.setFollowingWin(movie.getYear());
                        dif = movieWinnerDTO.getFollowingWin() - movieWinnerDTO.getPreviousWin();
                        movieWinnerDTO.setInterval(dif);
                        winner.put(producer, movieWinnerDTO);
                    } else if (movieWinnerDTO.getInterval() < (movie.getYear() - movieWinnerDTO.getFollowingWin())){
                        movieWinnerDTO.setPreviousWin(movieWinnerDTO.getFollowingWin());
                        movieWinnerDTO.setFollowingWin(movie.getYear());
                        dif = movieWinnerDTO.getFollowingWin() - movieWinnerDTO.getPreviousWin();
                        movieWinnerDTO.setInterval(dif);
                        winner.put(producer, movieWinnerDTO);
                    }
                    if (dif > maxInterval) {
                        maxInterval = dif;
                    }

                } else {
                    MovieWinnerDTO movieWinnerDTO = new MovieWinnerDTO();
                    movieWinnerDTO.setProducer(producer);
                    movieWinnerDTO.setPreviousWin(movie.getYear());
                    movieWinnerDTO.setInterval(0);
                    winner.put(producer, movieWinnerDTO);
                }
            }
        }
        return getProducersInterval(winner, maxInterval);
    }

    private List<MovieWinnerDTO> getProducersInterval(Map<String, MovieWinnerDTO> movieWinnerDTOMap, Integer interval) {
        return movieWinnerDTOMap.values().stream().filter(movieWinnerDTO -> movieWinnerDTO.getInterval().equals(interval)).collect(Collectors.toList());
    }

    private MinMaxIntervalDTO setMinMax(List<Movie> movies) {
        MinMaxIntervalDTO minMaxIntervalDTO = new MinMaxIntervalDTO();
        minMaxIntervalDTO.setMin(getProducerByMinInterval(movies));
        minMaxIntervalDTO.setMax(getProducerByMaxInterval(movies));
        return minMaxIntervalDTO;
    }

}
