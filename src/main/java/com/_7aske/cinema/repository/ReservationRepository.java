package com._7aske.cinema.repository;

import com._7aske.cinema.model.Reservation;
import com._7aske.grain.core.component.Grain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.List;

@Grain
public class ReservationRepository extends BaseRepository<Reservation> {
	public ReservationRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Reservation.class);
	}

	public List<Reservation> findAllByScreeningFk(Long id) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> root = cq.from(Reservation.class);

		CriteriaQuery<Reservation> query = cq.select(root)
				.where(cb.equal(
						root.get("screening").get("id"),
						id)
				);
		return entityManager.createQuery(query).getResultList();
	}

	public Reservation findByIdScreeningFkAndSeat(Integer id, Integer number) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> root = cq.from(Reservation.class);

		CriteriaQuery<Reservation> query = cq.select(root)
				.where(cb.and(
						cb.equal(root.get("screening").get("id"), id),
						cb.equal(root.get("seat"), number)));
		return entityManager.createQuery(query).getSingleResult();
	}
}
