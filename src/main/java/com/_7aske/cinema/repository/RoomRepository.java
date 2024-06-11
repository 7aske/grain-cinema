package com._7aske.cinema.repository;

import com._7aske.cinema.model.Room;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.data.repository.CrudRepository;
import org.hibernate.SessionFactory;

@Grain
public interface RoomRepository extends CrudRepository<Room, Long> {
}
