package com.lauracercas.moviecards.dto;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;

import java.util.Date;
import java.util.List;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeadDate() {
        return deadDate;
    }

    public void setDeadDate(Date deadDate) {
        this.deadDate = deadDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
