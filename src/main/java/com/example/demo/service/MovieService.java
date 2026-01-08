package com.example.demo.service;

import com.example.demo.dto.MovieResultsDto;
import com.example.demo.exception.MovieNotFound;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

	private final MovieRepository movieRepository;
	private final RatingRepository ratingRepository;

	@Transactional(readOnly = true)
	public MovieResultsDto getMovieDetails(Long movieCode) {
		log.info("Getting movie details for movie with code: {}", movieCode);
		
		Movie movie = movieRepository.findByMovieCode(movieCode)
				.orElseThrow(() -> new MovieNotFound("Movie with the selected code not found"));

		List<Rating> ratings = ratingRepository.findByMovie_MovieCode(movieCode);

		Rating firstRating = ratings.isEmpty() ? null : ratings.get(0);

		return MovieResultsDto.builder()
				.id(movie.getId())
				.movieCode(movie.getMovieCode())
				.movieName(movie.getMovieName())
				.watchedAt(movie.getWatchedAt())
				.director(movie.getDirector())
				.rating(firstRating != null ? firstRating.getScore() : null)
				.ratedBy(firstRating != null ? firstRating.getReviewerName() : null)
				.ratedOn(firstRating != null ? firstRating.getRatedOn() : null)
				.comments(firstRating != null ? firstRating.getReviewText() : null)
				.build();
	}

	@Transactional
	public MovieResultsDto addMovieDetails(MovieResultsDto movieResultsDto) {
		log.info("Adding movieDetails for movie: {}", movieResultsDto.getMovieName());

		Movie movie = movieRepository.findByMovieCode(movieResultsDto.getMovieCode())
				.orElseGet(() -> movieRepository.save(Movie.builder()
						.movieCode(movieResultsDto.getMovieCode())
						.movieName(movieResultsDto.getMovieName())
						.director(movieResultsDto.getDirector())
						.watchedAt(movieResultsDto.getWatchedAt())
						.build()));

		Rating rating = Rating.builder()
				.movie(movie)
				.reviewerName(movieResultsDto.getRatedBy())
				.reviewText(movieResultsDto.getComments())
				.score(movieResultsDto.getRating())
				.ratedOn(Instant.now())
				.build();

		Rating savedRating = ratingRepository.save(rating);

		return MovieResultsDto.builder()
				.id(movie.getId())
				.movieCode(movie.getMovieCode())
				.movieName(movie.getMovieName())
				.watchedAt(movie.getWatchedAt())
				.director(movie.getDirector())
				.rating(savedRating.getScore())
				.ratedBy(savedRating.getReviewerName())
				.ratedOn(savedRating.getRatedOn())
				.comments(savedRating.getReviewText())
				.build();
	}

	@Transactional(readOnly = true)
	public List<MovieResultsDto> getAllMovies() {
		log.info("Fetching all movies");
		return movieRepository.findAll().stream()
				.map(movie -> {
					List<Rating> ratings = movie.getRating();
					Rating firstRating = (ratings != null && !ratings.isEmpty()) ? ratings.get(0) : null;

					return MovieResultsDto.builder()
							.id(movie.getId())
							.movieCode(movie.getMovieCode())
							.movieName(movie.getMovieName())
							.watchedAt(movie.getWatchedAt())
							.director(movie.getDirector())
							.rating(firstRating != null ? firstRating.getScore() : null)
							.ratedBy(firstRating != null ? firstRating.getReviewerName() : null)
							.ratedOn(firstRating != null ? firstRating.getRatedOn() : null)
							.comments(firstRating != null ? firstRating.getReviewText() : null)
							.build();
				})
				.collect(Collectors.toList());
	}
}