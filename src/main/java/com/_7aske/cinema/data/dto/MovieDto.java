package com._7aske.cinema.data.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieDto {
	private Long id;
	private String url;
	private String title;
	private String description;
	private String genre;
	private Integer duration;
	private String director;
	private LocalDate releaseDate;
}
