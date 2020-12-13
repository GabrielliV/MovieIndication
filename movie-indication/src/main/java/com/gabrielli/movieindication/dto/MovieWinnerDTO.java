package com.gabrielli.movieindication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieWinnerDTO {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

    public MovieWinnerDTO() {

    }
}
