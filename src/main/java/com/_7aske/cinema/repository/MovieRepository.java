package com._7aske.cinema.repository;

import com._7aske.cinema.model.Movie;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.data.repository.CrudRepository;
import com._7aske.grain.web.page.Pageable;

import java.util.Collection;

@Grain
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
