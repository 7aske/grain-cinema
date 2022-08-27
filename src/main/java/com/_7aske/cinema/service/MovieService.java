package com._7aske.cinema.service;

import com._7aske.cinema.model.Movie;
import com._7aske.grain.orm.page.Pageable;

import java.util.Collection;

public interface MovieService {
	Collection<Movie> findAll();
	Collection<Movie> findAll(Pageable pageable);
}
