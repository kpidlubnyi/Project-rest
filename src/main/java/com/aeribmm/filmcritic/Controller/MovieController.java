package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Exception.MovieException.MovieNotFoundException;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import com.aeribmm.filmcritic.Model.omdbApi.MovieResponse;
import com.aeribmm.filmcritic.Service.MovieService;
import com.aeribmm.filmcritic.Service.OmdbService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@SecurityRequirement(name = "bearerAuth")
public class MovieController {

    private final MovieService service;
    private final OmdbService omdbService;

    public MovieController(MovieService service,OmdbService oService) {
        this.service = service;
        this.omdbService = oService;
    }

    @GetMapping("/getMovie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id){
        Movie movie = service.getMovie(id);
        if(movie == null){
            throw new MovieNotFoundException();
        }
        return ResponseEntity.ok(movie);
    }
    @GetMapping("/get-name/{title}")
    public ResponseEntity<MovieResponse> getMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(omdbService.getMovieByTitle(title));
    }
    @GetMapping("/randomm")
    public ResponseEntity<MovieResponse> randomMovieFromAPI(){
        return ResponseEntity.ok(omdbService.getRandomMovie());
    }
    @GetMapping("/random")
    public ResponseEntity<Movie> randomMovie(){
        return ResponseEntity.ok(service.random());
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<MovieDTO>> getAll(@RequestParam(defaultValue = "40") int limit){
        return ResponseEntity.ok(service.getAll(limit));
    }
}
