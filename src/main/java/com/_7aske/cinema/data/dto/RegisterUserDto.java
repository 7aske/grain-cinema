package com._7aske.cinema.data.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
	private String username;
	private String password;
	private String confirm;
}
