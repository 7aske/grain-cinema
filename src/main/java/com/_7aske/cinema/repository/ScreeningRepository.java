package com._7aske.cinema.repository;

import com._7aske.cinema.model.Screening;
import com._7aske.grain.core.component.Grain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.Collection;

@Grain
public class ScreeningRepository extends BaseRepository<Screening> {
	public ScreeningRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Screening.class);
	}

	public Collection<Screening> findByMovieId(Long id) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Screening> cq = cb.createQuery(Screening.class);
		Root<Screening> root = cq.from(Screening.class);
		CriteriaQuery<Screening> where = cq
				.where(cb.equal(
						root.get("movie").get("id"),
						id
				));
		return entityManager.createQuery(where).getResultList();
	}
}
