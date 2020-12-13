package com.gabrielli.movieindication.resources;

import com.gabrielli.movieindication.dto.MinMaxIntervalDTO;
import com.gabrielli.movieindication.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/movie")
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        super();
        this.movieService = movieService;
    }

    @GetMapping(path = "/winners")
    public ResponseEntity<MinMaxIntervalDTO> findMinAndMaxWinner() {
        return new ResponseEntity<>(movieService.getWinnerMinMax(), HttpStatus.OK);
    }


}
