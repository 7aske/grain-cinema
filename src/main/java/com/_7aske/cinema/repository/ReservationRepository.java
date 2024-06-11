package com._7aske.cinema.repository;

import com._7aske.cinema.model.Reservation;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.data.repository.CrudRepository;

import java.util.List;

@Grain
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findAllByScreeningId(Long id);

	Reservation findByUserUsernameAndScreeningIdAndSeat(String username, Long id, Integer number);
}
