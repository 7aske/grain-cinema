package com._7aske.cinema.repository;

import com._7aske.cinema.model.User;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.data.repository.CrudRepository;

import java.util.Optional;

@Grain
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
