package com._7aske.cinema.service.impl;

import com._7aske.cinema.data.dto.MovieDto;
import com._7aske.cinema.model.Movie;
import com._7aske.cinema.model.Room;
import com._7aske.cinema.repository.MovieRepository;
import com._7aske.cinema.service.MovieService;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.orm.page.Pageable;
import lombok.RequiredArgsConstructor;

import javax.management.modelmbean.ModelMBeanOperationInfo;
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

	@Override
	public Movie findById(Long id) {
		return movieRepository.findById(id);
	}

	@Override
	public Collection<Movie> search(String search, Pageable pageable) {
		if (search == null || search.isBlank())
			return findAll(pageable);

		return movieRepository.search(search, pageable);
	}

	@Override
	public void deleteById(Long id) {
		movieRepository.deleteById(id);
	}

	@Override
	public Movie saveOrUpdate(MovieDto movieDto) {
		if (movieDto.getId() != null) {
			Movie existing = findById(movieDto.getId());
			existing.setUrl(movieDto.getUrl());
			existing.setTitle(movieDto.getTitle());
			existing.setGenre(movieDto.getGenre());
			existing.setDirector(movieDto.getDirector());
			existing.setDuration(movieDto.getDuration());
			existing.setReleaseDate(movieDto.getReleaseDate());
			existing.setDescription(movieDto.getDescription());
			return movieRepository.update(existing);
		} else {
			Movie movie = new Movie();
			movie.setUrl(movieDto.getUrl());
			movie.setTitle(movieDto.getTitle());
			movie.setGenre(movieDto.getGenre());
			movie.setDirector(movieDto.getDirector());
			movie.setDuration(movieDto.getDuration());
			movie.setReleaseDate(movieDto.getReleaseDate());
			movie.setDescription(movieDto.getDescription());
			return movieRepository.save(movie);
		}
	}
}
