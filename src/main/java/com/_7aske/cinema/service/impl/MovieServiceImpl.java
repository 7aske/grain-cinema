package com._7aske.cinema.service.impl;

import com._7aske.cinema.model.Movie;
import com._7aske.cinema.repository.MovieRepository;
import com._7aske.cinema.service.MovieService;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.orm.page.Pageable;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Grain
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
	private final MovieRepository movieRepository;

	@Override
	public Collection<Movie> findAll() {
		return findAll(null);
	}

	@Override
	public Collection<Movie> findAll(Pageable pageable) {
		return movieRepository.findAll(pageable);
	}
}
