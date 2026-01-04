package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "movies")
@RequiredArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String movieName;

	private Long movieCode;

	private String title;
	private String director;
	private String releaseYear;
	private LocalDate watchedAt;
}
