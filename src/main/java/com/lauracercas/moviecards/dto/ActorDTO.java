package com.lauracercas.moviecards.dto;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ActorDTO {
    private Integer id;

    private String name;

    private Date birthDate;

    private Date deadDate;

    private String country;
    private List<Movie> movies;

    public ActorDTO() {}

    public ActorDTO(Integer id, String name, Date birthDate, Date deadDate, String country, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.deadDate = deadDate;
        this.country = country;
        this.movies = movies;
    }

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
        this.birthDate = actor.getBirthDate();
        this.deadDate = actor.getDeadDate();
        this.country = actor.getCountry();
        this.movies = actor.getMovies();
    }



}
