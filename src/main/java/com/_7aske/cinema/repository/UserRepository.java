package com._7aske.cinema.repository;

import com._7aske.cinema.model.User;
import com._7aske.grain.core.component.Grain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.Optional;

@Grain
public class UserRepository extends BaseRepository<User> {
	public UserRepository(SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
	}

	public Optional<User> findByUsername(String username) {
		try (EntityManager entityManager = getEntityManager()) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> root = cq.from(User.class);

			CriteriaQuery<User> where = cq.select(root)
					.where(cb.equal(
							root.get("username"),
							cb.lower(cb.literal(username)))
					);
			return entityManager.createQuery(where)
					.getResultStream()
					.findFirst();
		}
	}

}
