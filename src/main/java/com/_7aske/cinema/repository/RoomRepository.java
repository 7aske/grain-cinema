package com._7aske.cinema.repository;

import com._7aske.cinema.model.Room;
import com._7aske.grain.core.component.Grain;
import org.hibernate.SessionFactory;

@Grain
public class RoomRepository extends BaseRepository<Room> {
	public RoomRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Room.class);
	}
}
