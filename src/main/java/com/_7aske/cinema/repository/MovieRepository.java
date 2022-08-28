package com._7aske.cinema.repository;

import com._7aske.cinema.model.Movie;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.orm.page.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.Collection;

@Grain
public class MovieRepository extends BaseRepository<Movie> {
	public MovieRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Movie.class);
	}

	public Collection<Movie> search(String search, Pageable pageable) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
		Root<Movie> root = cq.from(Movie.class);
		CriteriaQuery<Movie> where = cq.select(root)
				.where(cb.or(
						cb.like(root.get("title"), wrapLike(search)),
						cb.like(root.get("description"), wrapLike(search)),
						cb.like(root.get("genre"), wrapLike(search)),
						cb.like(root.get("director"), wrapLike(search))
				));
		return entityManager.createQuery(where)
				.setFirstResult(pageable.getPageOffset())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
	}

	private String wrapLike(String str) {
		return "%" + str + "%";
	}
}
