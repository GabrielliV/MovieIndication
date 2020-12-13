package com.gabrielli.movieindication.resources;

import com.gabrielli.movieindication.dto.MinMaxIntervalDTO;
import com.gabrielli.movieindication.services.impl.MovieServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
public class MovieServiceImplIT {
    @Autowired
    MovieServiceImpl movieService;

    @Test
    public void testReadMovies() {

        MinMaxIntervalDTO minMaxIntervalDTO = movieService.getWinnerMinMax();

        assertThat(minMaxIntervalDTO.getMax().size(), Matchers.equalTo(1));
        assertThat(minMaxIntervalDTO.getMin().size(), Matchers.equalTo(1));

        assertThat(minMaxIntervalDTO.getMin().get(0).getProducer(), equalTo("Joel Silver"));
        assertThat(minMaxIntervalDTO.getMin().get(0).getInterval(), equalTo(1));
        assertThat(minMaxIntervalDTO.getMin().get(0).getPreviousWin(), equalTo(1990));
        assertThat(minMaxIntervalDTO.getMin().get(0).getFollowingWin(), equalTo(1991));

        assertThat(minMaxIntervalDTO.getMax().get(0).getProducer(), equalTo("Buzz Feitshans"));
        assertThat(minMaxIntervalDTO.getMax().get(0).getInterval(), equalTo(9));
        assertThat(minMaxIntervalDTO.getMax().get(0).getPreviousWin(), equalTo(1985));
        assertThat(minMaxIntervalDTO.getMax().get(0).getFollowingWin(), equalTo(1994));

    }

}
