package com.gabrielli.movieindication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MinMaxIntervalDTO {
    private List<MovieWinnerDTO> min;
    private List<MovieWinnerDTO> max;

    public MinMaxIntervalDTO() {

    }
}
