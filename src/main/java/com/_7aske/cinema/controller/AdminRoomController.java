package com._7aske.cinema.controller;

import com._7aske.cinema.data.dto.RoomDto;
import com._7aske.cinema.model.Room;
import com._7aske.cinema.service.RoomService;
import com._7aske.cinema.util.TemplateViewBuilder;
import com._7aske.grain.web.controller.annotation.*;
import com._7aske.grain.web.exception.HttpException;
import com._7aske.grain.web.http.codec.form.FormBody;
import com._7aske.grain.web.view.TemplateView;
import com._7aske.grain.web.view.View;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Controller
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class AdminRoomController {
	private final RoomService service;

	private static final String VIEW = "pages/admin/rooms/index.gtl";
	private static final String LIST_ATTR = "rooms";

	@GetMapping
	public View getIndex(@RequestParam(value = "room", required = false) Long roomId) {
		TemplateView templateView = TemplateViewBuilder.builder(VIEW)
				.withAttribute(LIST_ATTR, service.findAll())
				.build();

		if (roomId != null) {
			templateView.addAttribute("room", service.findById(roomId));
		}

		return templateView;
	}

	@GetMapping("/add")
	public View getAdd() {
		return TemplateViewBuilder.builder(VIEW)
				.withAttribute(LIST_ATTR, service.findAll())
				.withAttribute("room", new Room())
				.build();
	}

	@PostMapping("/save")
	public String postSave(@FormBody RoomDto room) {
		Room updated = service.saveOrUpdate(room);
		return "redirect:/admin/rooms?room=" + updated.getId();
	}

	@PostMapping("/delete")
	public String postDelete(@FormBody Map<String, String[]> body) {
		if (body.get("id") == null) {
			throw new HttpException.BadRequest("Invalid id");
		}
		String id =  body.get("id")[0];
		service.deleteById(Long.valueOf(id));
		return "redirect:/admin/rooms";
	}
}
