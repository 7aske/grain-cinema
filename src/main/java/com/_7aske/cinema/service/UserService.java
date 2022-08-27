package com._7aske.cinema.service;

import com._7aske.cinema.data.dto.RegisterUserDto;
import com._7aske.cinema.model.User;

public interface UserService {
	User findByUsername(String username);

	User register(RegisterUserDto dto);
}
