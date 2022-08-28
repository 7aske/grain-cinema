package com._7aske.cinema.controller;

import com._7aske.cinema.service.ScreeningService;
import com._7aske.grain.core.component.Controller;
import com._7aske.grain.web.controller.annotation.GetMapping;
import com._7aske.grain.web.controller.annotation.PathVariable;
import com._7aske.grain.web.controller.annotation.PostMapping;
import com._7aske.grain.web.controller.annotation.RequestMapping;
import com._7aske.grain.web.view.TemplateView;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/screenings")
@RequiredArgsConstructor
public class ScreeningController {
	public final ScreeningService screeningService;

	@GetMapping("/{id}")
	public View index(@PathVariable("id") Long id) {
		TemplateView view = new TemplateView("pages/screening.gtl");
		view.addAttribute("screening", screeningService.findById(id));
		return view;
	}

	@PostMapping("/{id}/reservations/{number}")
	public String reserve(@PathVariable("id") Long id, @PathVariable("number") Integer number) {
		screeningService.reserve(id, number);
		return "redirect:/screenings/" + id;
	}
}
