package com._7aske.cinema.controller;

import com._7aske.cinema.data.dto.RegisterUserDto;
import com._7aske.cinema.service.MovieService;
import com._7aske.cinema.service.UserService;
import com._7aske.cinema.util.TemplateViewBuilder;
import com._7aske.grain.core.component.Controller;
import com._7aske.grain.orm.page.Pageable;
import com._7aske.grain.web.controller.annotation.*;
import com._7aske.grain.web.http.codec.form.FormBody;
import com._7aske.grain.web.http.session.Session;
import com._7aske.grain.web.view.TemplateView;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexController {
	private final UserService userService;
	private final MovieService movieService;

	@GetMapping
	public View getIndex(@RequestParam(value = "page", defaultValue = "0,3") Pageable page, Session session) {
		return TemplateViewBuilder.builder("index.gtl")
				.withAttribute("movies", movieService.findAll(page))
				.build();
	}

	@GetMapping("/login")
	public View getLogin() {
		return new TemplateView("login.gtl");
	}

	@GetMapping("/register")
	public View getRegister() {
		return new TemplateView("register.gtl");
	}

	@PostMapping("/register")
	public String postRegister(@FormBody RegisterUserDto user) {
		userService.register(user);
		return "redirect:/login?registered";
	}

	@GetMapping("/test")
	public String test(@RequestParam("test") Optional<Long[]> test) {
		return Arrays.toString(test.orElse(new Long[]{Long.MAX_VALUE}));
	}
}
