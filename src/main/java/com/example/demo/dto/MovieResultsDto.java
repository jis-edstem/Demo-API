package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@Getter
public class MovieResultsDto {
	private Long movieCode;
	private String movieName;
	private LocalDate watchedAt;
	private Integer rating;
	private String director;
	private String comments;
	private Instant ratedOn;
	private String ratedBy;
}
