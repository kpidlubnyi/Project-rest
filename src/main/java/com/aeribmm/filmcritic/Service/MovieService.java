package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.FilmRepository;
import com.aeribmm.filmcritic.Model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    private final FilmRepository repository;

    public MovieService(FilmRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Movie getMovie(String id) {
        return repository.getMovieByImdbId(id);
    }
}
