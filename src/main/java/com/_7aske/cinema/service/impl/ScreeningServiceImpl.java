package com._7aske.cinema.service.impl;

import com._7aske.cinema.model.Reservation;
import com._7aske.cinema.model.Room;
import com._7aske.cinema.model.Screening;
import com._7aske.cinema.model.User;
import com._7aske.cinema.repository.ReservationRepository;
import com._7aske.cinema.repository.ScreeningRepository;
import com._7aske.cinema.service.ScreeningService;
import com._7aske.cinema.service.UserService;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.security.Authentication;
import com._7aske.grain.security.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Grain
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
	private final ScreeningRepository screeningRepository;
	private final ReservationRepository reservationRepository;
	private final UserService userService;

	@Override
	public int getRemainingSeats(Integer screeningId) {
		Screening screening = screeningRepository.findById(Long.valueOf(screeningId));
		Room room = screening.getRoom();
		List<Reservation> reservations = reservationRepository.findAllByScreeningFk(screening.getId());
		return room.getSeats() - reservations.size();
	}

	@Override
	public Screening findById(Integer id) {
		return screeningRepository.findById(Long.valueOf(id));
	}

	@Override
	public void reserve(Integer id, Integer number) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Screening screening = findById(id);
		if (!screening.isSeatTaken(number)) {
			Reservation reservation = new Reservation();
			reservation.setScreening(screening);
			User user = userService.findByUsername(authentication.getName());
			reservation.setUser(user);
			reservation.setSeat(number);
			reservationRepository.save(reservation);
		} else if (screening.isSeatTakenBy(number, authentication)) {
			Reservation reservation = reservationRepository.findByIdScreeningFkAndSeat(id, number);
			reservationRepository.delete(reservation);
		}
	}
}
