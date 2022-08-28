package com._7aske.cinema.controller;

import com._7aske.cinema.data.dto.RegisterUserDto;
import com._7aske.cinema.service.MovieService;
import com._7aske.cinema.service.UserService;
import com._7aske.grain.core.component.Controller;
import com._7aske.grain.http.form.FormBody;
import com._7aske.grain.orm.page.Pageable;
import com._7aske.grain.security.BasicAuthority;
import com._7aske.grain.security.context.SecurityContextHolder;
import com._7aske.grain.web.controller.annotation.GetMapping;
import com._7aske.grain.web.controller.annotation.PostMapping;
import com._7aske.grain.web.controller.annotation.RequestMapping;
import com._7aske.grain.web.controller.annotation.RequestParam;
import com._7aske.grain.web.view.TemplateView;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexController {
	private final UserService userService;
	private final MovieService movieService;

	@GetMapping
	public View getIndex(@RequestParam(value = "page", defaultValue = "0,3") Pageable page) {
		TemplateView dataView = new TemplateView("index.gtl");
		dataView.addAttribute("movies", movieService.findAll(page));
		return dataView;
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
}
