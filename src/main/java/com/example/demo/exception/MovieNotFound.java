package com.example.demo.exception;

public class MovieNotFound extends RuntimeException {
	public MovieNotFound(String message) {
		super(message);
	}
}
