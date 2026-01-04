package com.example.demo.controller;

import com.example.demo.dto.MovieResultsDto;
import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@Slf4j
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;

	@PostMapping
	public ResponseEntity<MovieResultsDto> addMovieDetails(
			@RequestBody MovieResultsDto movieResultsDto) {
		log.debug("Adding movieDetails upon user request for movie: {}", movieResultsDto.getMovieName());
		MovieResultsDto result = movieService.addMovieDetails(movieResultsDto);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/{movieCode}")
	public ResponseEntity<MovieResultsDto> getMovieDetailsByCode(
			@PathVariable Integer movieCode) {
		log.debug("Fetching movies with the movie code: {}", movieCode);
		MovieResultsDto response = movieService.getMovieDetails(movieCode);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
