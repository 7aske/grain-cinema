package com._7aske.cinema.controller;

import com._7aske.cinema.model.Screening;
import com._7aske.cinema.service.ScreeningService;
import com._7aske.cinema.util.TemplateViewBuilder;
import com._7aske.grain.web.controller.annotation.*;
import com._7aske.grain.web.exception.HttpException;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/screenings")
@RequiredArgsConstructor
public class ScreeningController {
	public final ScreeningService screeningService;

	@GetMapping("/{id}")
	public View index(@PathVariable("id") Long id) {
		Screening screening = screeningService.findById(id);
		if (screening == null) {
			throw new HttpException.NotFound("Screening not found");
		}

		return TemplateViewBuilder.builder("pages/screening.gtl")
				.withAttribute("screening", screeningService.findById(id))
				.build();
	}

	@PostMapping("/{id}/reservations/{number}")
	public String reserve(@PathVariable("id") Long id, @PathVariable("number") Integer number) {
		screeningService.reserve(id, number);
		return "redirect:/screenings/" + id;
	}
}
