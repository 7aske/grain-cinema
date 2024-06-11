package com._7aske.cinema.repository;

import com._7aske.cinema.model.Screening;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.data.repository.CrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.Collection;

@Grain
public interface ScreeningRepository extends CrudRepository<Screening, Long> {
	Collection<Screening> findByMovieId(Long id);
}
