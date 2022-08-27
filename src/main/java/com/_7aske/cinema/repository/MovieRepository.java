package com._7aske.cinema.repository;

import com._7aske.cinema.model.Movie;
import com._7aske.grain.core.component.Grain;
import org.hibernate.SessionFactory;

@Grain
public class MovieRepository extends BaseRepository<Movie> {
	public MovieRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Movie.class);
	}
}
