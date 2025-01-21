package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.dto.MovieDTO;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import com.lauracercas.moviecards.util.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class MovieController {

    private final MovieService movieService;
    private static final String MOVIE = "movie";
    private static final String TITLE = "title";
    private static final String MOVIES_FORM = "movies/form";

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    public String getMoviesList(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies/list";
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute(MOVIE, new Movie());
        model.addAttribute(TITLE, Messages.NEW_MOVIE_TITLE);
        return MOVIES_FORM;
    }

    @PostMapping("saveMovie")
    public String saveMovie(@ModelAttribute MovieDTO movieDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return MOVIES_FORM;
        }

        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setDuration(movieDTO.getDuration());
        movie.setCountry(movieDTO.getCountry());
        movie.setDirector(movieDTO.getDirector());
        movie.setGenre(movieDTO.getGenre());
        movie.setSinopsis(movieDTO.getSinopsis());
        movie.setActors(movieDTO.getActors());

        Movie movieSaved = movieService.save(movie);
        if (movieSaved.getId() != null) {
            model.addAttribute("message", Messages.UPDATED_MOVIE_SUCCESS);
        } else {
            model.addAttribute("message", Messages.SAVED_MOVIE_SUCCESS);
        }

        model.addAttribute(MOVIE, movieSaved);
        model.addAttribute(TITLE, Messages.EDIT_MOVIE_TITLE);
        return "redirect:/movies";
    }

    @GetMapping("editMovie/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        List<Actor> actors = movie.getActors();
        model.addAttribute(MOVIE, movie);
        model.addAttribute("actors", actors);

        model.addAttribute(TITLE, Messages.EDIT_MOVIE_TITLE);

        return MOVIES_FORM;
    }


}
