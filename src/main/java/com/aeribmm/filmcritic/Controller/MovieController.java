package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Exception.MovieException.MovieNotFoundException;
import com.aeribmm.filmcritic.Model.Movie;
import com.aeribmm.filmcritic.Service.MovieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/getMovie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id){
        Movie movie = service.getMovie(id);
        if(movie == null){
            throw new MovieNotFoundException();
        }
        return ResponseEntity.ok(movie);
    }

}
