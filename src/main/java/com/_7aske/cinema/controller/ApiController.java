package com._7aske.cinema.controller;

import com._7aske.cinema.service.ScreeningService;
import com._7aske.grain.core.component.Controller;
import com._7aske.grain.web.controller.annotation.GetMapping;
import com._7aske.grain.web.controller.annotation.PathVariable;
import com._7aske.grain.web.controller.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
	private final ScreeningService screeningService;

	@GetMapping("/screenings/{screeningId}/seats")
	public String getRemainingSeats(@PathVariable("screeningId") Long id){
		return String.valueOf(screeningService.getRemainingSeats(id));
	}
}
