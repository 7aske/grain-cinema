package com._7aske.cinema.service.impl;

import com._7aske.cinema.data.dto.RoomDto;
import com._7aske.cinema.model.Room;
import com._7aske.cinema.repository.RoomRepository;
import com._7aske.cinema.service.RoomService;
import com._7aske.grain.core.component.Grain;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Grain
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepository roomRepository;

	@Override
	public Room findById(Long id) {
		return roomRepository.findById(id);
	}

	@Override
	public Collection<Room> findAll() {
		return roomRepository.findAll();
	}

	@Override
	public Room saveOrUpdate(RoomDto roomDto) {
		if (roomDto.getId() != null) {
			Room existing = findById(roomDto.getId());
			existing.setSeats(roomDto.getSeats());
			return roomRepository.update(existing);
		} else {
			Room room = new Room();
			room.setSeats(roomDto.getSeats());
			return roomRepository.save(room);
		}
	}

	@Override
	public void deleteById(Long id) {
		roomRepository.deleteById(id);
	}
}
