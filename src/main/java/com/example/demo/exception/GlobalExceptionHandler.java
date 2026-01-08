package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MovieNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ProblemDetail handleMovieNotFoundException(MovieNotFound ex) {
		log.info("Movie not found: {}", ex.getMessage());
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setTitle("Movie Not Found");
		problemDetail.setType(URI.create("/errors/movie-not-found"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}
}