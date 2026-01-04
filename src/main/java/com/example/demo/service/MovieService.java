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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

	private final MovieRepository movieRepository;
	private final RatingRepository ratingRepository;

	@Transactional(readOnly = true)
	public MovieResultsDto getMovieDetails(Integer movieCode) {
		log.info("Getting movie details for movie with code: {}", movieCode);
		if (!movieRepository.existsById(movieCode)) {
			throw new MovieNotFound("Movie with the selected id not found");
		}
		Optional<Movie> movieResponse = movieRepository.findById(movieCode);
		Optional<Rating> ratingResponse = ratingRepository.findById(movieCode);

		return MovieResultsDto.builder()
				.movieCode(movieResponse.get().getMovieCode())
				.movieName(movieResponse.get().getMovieName())
				.watchedAt(movieResponse.get().getWatchedAt())
				.director(movieResponse.get().getDirector())
				.rating(ratingResponse.get().getScore())
				.ratedBy(ratingResponse.get().getReviewerName())
				.ratedOn(ratingResponse.get().getRatedOn())
				.comments(ratingResponse.get().getReviewText())
				.build();
	}

	@Transactional
	public MovieResultsDto addMovieDetails(MovieResultsDto movieResultsDto) {
		log.info("Adding movieDetails for movie: {}", movieResultsDto.getMovieName());

		Rating rating = Rating.builder()
				.reviewerName(movieResultsDto.getRatedBy())
				.reviewText(movieResultsDto.getComments())
				.score(movieResultsDto.getRating())
				.ratedOn(Instant.now())
				.build();

		Rating savedRatings = ratingRepository.save(rating);

		Movie movie = Movie.builder()
				.movieCode(movieResultsDto.getMovieCode())
				.movieName(movieResultsDto.getMovieName())
				.director(movieResultsDto.getDirector())
				.watchedAt(movieResultsDto.getWatchedAt())
				.build();

		Movie savedMovie = movieRepository.save(movie);

		return MovieResultsDto.builder()
				.movieCode(savedMovie.getMovieCode())
				.movieName(savedMovie.getMovieName())
				.movieCode(savedMovie.getMovieCode())
				.watchedAt(savedMovie.getWatchedAt())
				.director(savedMovie.getDirector())
				.rating(savedRatings.getScore())
				.ratedBy(savedRatings.getReviewerName())
				.ratedOn(savedRatings.getRatedOn())
				.comments(savedRatings.getReviewText())
				.build();
	}
}
