package com._7aske.cinema.service.impl;

import com._7aske.cinema.data.dto.ScreeningDto;
import com._7aske.cinema.model.Reservation;
import com._7aske.cinema.model.Room;
import com._7aske.cinema.model.Screening;
import com._7aske.cinema.model.User;
import com._7aske.cinema.repository.ReservationRepository;
import com._7aske.cinema.repository.ScreeningRepository;
import com._7aske.cinema.service.MovieService;
import com._7aske.cinema.service.RoomService;
import com._7aske.cinema.service.ScreeningService;
import com._7aske.cinema.service.UserService;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.security.Authentication;
import com._7aske.grain.security.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@Grain
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
	private final ScreeningRepository screeningRepository;
	private final ReservationRepository reservationRepository;
	private final UserService userService;
	private final RoomService roomService;
	private final MovieService movieService;

	@Override
	public int getRemainingSeats(Long screeningId) {
		Screening screening = screeningRepository.findById(screeningId);
		Room room = screening.getRoom();
		List<Reservation> reservations = reservationRepository.findAllByScreeningFk(screening.getId());
		return room.getSeats() - reservations.size();
	}

	@Override
	public Screening findById(Long id) {
		return screeningRepository.findById(id);
	}

	@Override
	public Screening saveOrUpdate(ScreeningDto screeningDto) {
		if (screeningDto.getId() != null) {
			Screening existing = findById(screeningDto.getId());
			existing.setTime(screeningDto.getTime());
			existing.setRoom(roomService.findById(screeningDto.getRoomId()));
			existing.setMovie(movieService.findById(screeningDto.getMovieId()));
			return screeningRepository.update(existing);
		} else {
			Screening screening = new Screening();
			screening.setTime(screeningDto.getTime());
			screening.setRoom(roomService.findById(screeningDto.getRoomId()));
			screening.setMovie(movieService.findById(screeningDto.getMovieId()));
			return screeningRepository.save(screening);
		}
	}

	@Override
	public Collection<Screening> findByMovieId(Long id) {
		return screeningRepository.findByMovieId(id);
	}

	@Override
	public void reserve(Long id, Integer number) {
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
			Reservation reservation = reservationRepository
					.findByUsernameAndIdScreeningFkAndSeat(authentication.getName(), id, number);
			reservationRepository.delete(reservation);
		}
	}
}
