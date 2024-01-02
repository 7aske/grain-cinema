package com._7aske.cinema.util;

import com._7aske.grain.web.view.TemplateView;

public class TemplateViewBuilder {
    private final TemplateView templateView;

    private TemplateViewBuilder(String view) {
        templateView = new TemplateView(view);
    }

    public static TemplateViewBuilder builder(String view) {
        return new TemplateViewBuilder(view);
    }

    public TemplateViewBuilder withAttribute(String name, Object value) {
        templateView.addAttribute(name, value);
        return this;
    }

    public TemplateView build() {
        return templateView;
    }
}
