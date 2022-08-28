package com._7aske.cinema.controller;

import com._7aske.cinema.data.dto.ScreeningDto;
import com._7aske.cinema.model.Movie;
import com._7aske.cinema.model.Screening;
import com._7aske.cinema.service.MovieService;
import com._7aske.cinema.service.RoomService;
import com._7aske.cinema.service.ScreeningService;
import com._7aske.grain.core.component.Controller;
import com._7aske.grain.http.form.FormBody;
import com._7aske.grain.web.controller.annotation.*;
import com._7aske.grain.web.view.TemplateView;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/movies/{movieId}/screenings")
@RequiredArgsConstructor
public class AdminScreeningController {
	private final ScreeningService screeningService;
	private final RoomService roomService;
	private final MovieService movieService;

	private static final String VIEW = "pages/admin/screenings/index.gtl";
	private static final String ROOM_LIST_ATTR = "rooms";
	private static final String LIST_ATTR = "screenings";
	private static final String SINGLE_ATTR = "screening";

	@GetMapping
	public View getIndex(@PathVariable("movieId") Long movieId,
	                     @RequestParam("screening") Long screeningId) {
		TemplateView templateView = new TemplateView(VIEW);
		templateView.addAttribute(LIST_ATTR, screeningService.findByMovieId(movieId));
		templateView.addAttribute("movie", movieService.findById(movieId));

		if (screeningId != null) {
			templateView.addAttribute(ROOM_LIST_ATTR, roomService.findAll());
			templateView.addAttribute(SINGLE_ATTR, screeningService.findById(screeningId));
		}

		return templateView;
	}

	@GetMapping("/add")
	public View getAdd(@PathVariable("movieId") Long movieId) {
		TemplateView templateView = new TemplateView(VIEW);
		templateView.addAttribute(LIST_ATTR, screeningService.findByMovieId(movieId));
		templateView.addAttribute(ROOM_LIST_ATTR, roomService.findAll());
		Movie movie =movieService.findById(movieId);
		templateView.addAttribute("movie", movie);
		Screening screening = new Screening();
		screening.setMovie(movie);
		templateView.addAttribute("screening", screening);
		return templateView;
	}

	@PostMapping("/save")
	public String postSave(@FormBody ScreeningDto screeningDto) {
		Screening updated = screeningService.saveOrUpdate(screeningDto);
		return "redirect:/admin/movies/" + updated.getMovie().getId() + "/screenings?screening=" + updated.getId();
	}
}
