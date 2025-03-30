package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.Movie;
import com.aeribmm.filmcritic.Service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/getMovie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id){
        return ResponseEntity.ok(service.getMovie(id));
    }

}
