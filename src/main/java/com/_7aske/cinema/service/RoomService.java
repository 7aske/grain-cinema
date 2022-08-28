package com._7aske.cinema.service;

import com._7aske.cinema.data.dto.RoomDto;
import com._7aske.cinema.model.Room;

import java.util.Collection;

public interface RoomService {
	Room findById(Long id);

	Collection<Room> findAll();

	Room saveOrUpdate(RoomDto room);

	void deleteById(Long id);
}
