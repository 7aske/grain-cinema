package com._7aske.cinema.service;

import com._7aske.cinema.model.Screening;

public interface ScreeningService {
	int getRemainingSeats(Integer screeningId);
	Screening findById(Integer id);

	void reserve(Integer id, Integer number);
}
