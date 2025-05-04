package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.FilmRepository;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Movie random(){
        return repository.getRandomMovie();
    }

    public List<MovieDTO> getAll(int limit){
        Pageable pageable = PageRequest.of(0, limit);
        return repository.findAll(pageable)
                .map(movie -> new MovieDTO(movie.getTitle(), movie.getPoster(),movie.getPlot()))
                .getContent();
    }

    public List<Movie> search(String keyword) {
        return repository.findByTitleContainingIgnoreCase(keyword);
    }
}
