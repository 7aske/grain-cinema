package com._7aske.cinema.service.impl;

import com._7aske.cinema.data.dto.RegisterUserDto;
import com._7aske.cinema.exception.InvalidPasswordException;
import com._7aske.cinema.exception.PasswordsNotMatchingException;
import com._7aske.cinema.model.User;
import com._7aske.cinema.model.domain.Role;
import com._7aske.cinema.repository.UserRepository;
import com._7aske.cinema.service.UserService;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.security.crypto.PasswordEncoder;
import com._7aske.grain.security.exception.UserNotFoundException;
import com._7aske.grain.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Grain
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, com._7aske.grain.security.service.UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Override
	public User findByUsername(String s) throws UserNotFoundException {
		return userRepository.findByUsername(s)
				.orElseThrow(UserNotFoundException::new);
	}

	@Override
	public User register(RegisterUserDto dto) {
		if (StringUtils.isBlank(dto.getPassword()) || StringUtils.isBlank(dto.getConfirm())) {
			throw new InvalidPasswordException("Passwords cannot be empty");
		}

		if (!Objects.equals(dto.getPassword(), dto.getConfirm())) {
			throw new PasswordsNotMatchingException("Passwords do not match");
		}

		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(Role.USER);
		return userRepository.save(user);
	}
}
