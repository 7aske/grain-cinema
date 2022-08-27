package com._7aske.cinema.repository;

import com._7aske.cinema.model.Screening;
import com._7aske.grain.core.component.Grain;
import org.hibernate.SessionFactory;

@Grain
public class ScreeningRepository extends BaseRepository<Screening> {
	public ScreeningRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Screening.class);
	}
}
