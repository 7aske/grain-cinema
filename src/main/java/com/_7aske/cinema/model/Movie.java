package com._7aske.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movie", indexes = {
		@Index(name = "movie_idx", columnList = "title")
})
@Getter @Setter @ToString
public class Movie {
	@Id
	@Column(name = "movie_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "image_url")
	private String url;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "genre")
	private String genre;
	@Column(name = "duration")
	private Integer duration;
	@Column(name = "director")
	private String director;
	@Column(name = "release_date")
	private LocalDate releaseDate;
	@OneToMany(mappedBy = "movie")
	private List<Screening> screenings;
}
