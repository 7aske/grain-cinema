package com._7aske.cinema.controller;

import com._7aske.cinema.data.dto.MovieDto;
import com._7aske.cinema.model.Movie;
import com._7aske.cinema.service.MovieService;
import com._7aske.grain.core.component.Controller;
import com._7aske.grain.exception.http.HttpException;
import com._7aske.grain.http.form.FormBody;
import com._7aske.grain.orm.page.Pageable;
import com._7aske.grain.web.controller.annotation.*;
import com._7aske.grain.web.view.TemplateView;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {
	private final MovieService movieService;

	private static final String VIEW = "pages/admin/movies/index.gtl";
	private static final String EDIT_VIEW = "pages/admin/movies/edit.gtl";
	private static final String LIST_ATTR = "movies";
	private static final String SINGLE_ATTR = "movie";

	@GetMapping
	public View getIndex(@RequestParam(value = "page", defaultValue = "0,12") Pageable pageable,
	                     @RequestParam("movie") Long movieId) {
		TemplateView templateView = new TemplateView(VIEW);
		templateView.addAttribute(LIST_ATTR, movieService.findAll(pageable));
		if (movieId != null) {
			templateView.addAttribute(SINGLE_ATTR, movieService.findById(movieId));
		}
		return templateView;
	}

	@PostMapping("/save")
	public String postSave(@FormBody MovieDto movieDto) {
		Movie movie = movieService.saveOrUpdate(movieDto);
		return "redirect:/admin/movies/" + movie.getId() + "/edit";
	}

	@GetMapping("/add")
	public View getAdd(@PathVariable("id") Long id) {
		TemplateView templateView = new TemplateView(EDIT_VIEW);
		templateView.addAttribute(SINGLE_ATTR, new Movie());
		return templateView;
	}

	@GetMapping("/{id}/edit")
	public View getEdit(@PathVariable("id") Long id) {
		TemplateView templateView = new TemplateView(EDIT_VIEW);
		templateView.addAttribute(SINGLE_ATTR, movieService.findById(id));
		return templateView;
	}

	@PostMapping
	public View getPostIndex(@FormBody Map<String, String[]> body,
	                         @RequestParam(value = "page", defaultValue = "0,12") Pageable pageable) {
		String search = body.get("search") == null ? "" : body.get("search")[0];
		Collection<Movie> movies = movieService.search(search, pageable);
		TemplateView templateView = new TemplateView(VIEW);
		templateView.addAttribute(LIST_ATTR, movies);
		templateView.addAttribute("search", search);
		return templateView;
	}

	@PostMapping("/delete")
	public String postDelete(@FormBody Map<String, String[]> body) {
		if (body.get("id") == null) {
			throw new HttpException.BadRequest("/admin/movies/delete");
		}

		String id = body.get("id")[0];
		movieService.deleteById(Long.parseLong(id));

		return "redirect:/admin/movies";
	}
}
