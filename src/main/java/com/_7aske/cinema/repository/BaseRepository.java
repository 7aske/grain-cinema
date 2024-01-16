package com._7aske.cinema.repository;

import com._7aske.grain.annotation.NotNull;
import com._7aske.grain.annotation.Nullable;
import com._7aske.grain.web.page.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Part of the Hibernate integration. Allows for more concise usage of the data layer
 * in service classes. Provides basic CRUD operations for given entities. Has support
 * for the {@link Pageable} interface.
 *
 * @param <E> Entity type.
 *
 * @see Pageable
 */
@RequiredArgsConstructor
public abstract class BaseRepository<E> {
	private final SessionFactory sessionFactory;
	private final Class<E> entityClass;

	protected synchronized EntityManager getEntityManager() {
		return sessionFactory.createEntityManager();
	}

	public List<E> findAll(@Nullable Pageable pageable) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityClass);
		Root<E> root = cq.from(entityClass);
		CriteriaQuery<E> where = cq.select(root);
		Query query = entityManager.createQuery(where);
		if (pageable != null) {
			query.setFirstResult(pageable.getPageOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		return query.getResultList();
	}

	public List<E> findAll() {
		return findAll(null);
	}

	public E findById(@NotNull Long id) {
		EntityManager entityManager = getEntityManager();
		return entityManager.find(entityClass, id);
	}

	public E save(@NotNull E entity) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		return entity;
	}

	public E update(@NotNull E entity) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return entity;
	}

	public void delete(@NotNull E entity) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}

	public void deleteById(@NotNull Long id) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(entityClass, id));
		entityManager.getTransaction().commit();
	}

	public void deleteAll() {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		CriteriaDelete<E> delete = entityManager.getCriteriaBuilder().createCriteriaDelete(entityClass);
		entityManager.createQuery(delete).executeUpdate();
		entityManager.getTransaction().commit();
	}
}
