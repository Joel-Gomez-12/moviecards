package com.lauracercas.moviecards.dto;

import com.lauracercas.moviecards.model.Actor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Getter
@Setter
public class MovieDTO {
    private Integer id;
    private String title;
    private Integer releaseYear;
    private Integer duration;
    private String country;
    private String director;
    private String genre;
    private String sinopsis;
    private List<Actor> actors;


}
