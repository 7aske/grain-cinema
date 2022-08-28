package com._7aske.cinema.service;

import com._7aske.cinema.data.dto.ScreeningDto;
import com._7aske.cinema.model.Screening;

import java.util.Collection;

public interface ScreeningService {
	int getRemainingSeats(Long screeningId);

	Screening findById(Long id);

	Screening saveOrUpdate(ScreeningDto screeningDto);

	Collection<Screening> findByMovieId(Long id);

	void reserve(Long id, Integer number);
}
