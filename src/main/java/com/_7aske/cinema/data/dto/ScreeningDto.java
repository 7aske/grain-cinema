package com._7aske.cinema.data.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScreeningDto {
	private Long id;
	private LocalDateTime time;
	private Long roomId;
	private Long movieId;
}
