package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "movie_rating")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;

	private String reviewerName;
	private Integer score;
	private String reviewText;
	private Instant ratedOn;

}
