package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@Getter
public class MovieResultsDto {
	private Long id;
	@NotNull(message = "movieCode cannot be empty")
	private Long movieCode;
	@NotNull(message = "movieName cannot be empty")
	private String movieName;
	private LocalDate watchedAt;
	private Integer rating;
	private String director;
	private String comments;
	private Instant ratedOn;
	private String ratedBy;
}
