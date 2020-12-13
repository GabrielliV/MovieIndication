package com.gabrielli.movieindication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Movie {
    @GeneratedValue
    @Id
    private Long id;
    private Integer year;
    private String title;
    private String studios;
    private String producers;
    private String winner;

    public Movie() {

    }
}
