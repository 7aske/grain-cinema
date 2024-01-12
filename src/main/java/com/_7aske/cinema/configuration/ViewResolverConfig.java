package com._7aske.cinema.configuration;

import com._7aske.grain.core.component.Grain;
import com._7aske.grain.core.configuration.Configuration;
import com._7aske.grain.exception.GrainRuntimeException;
import com._7aske.grain.security.Authentication;
import com._7aske.grain.ui.LoginPage;
import com._7aske.grain.web.http.HttpRequest;
import com._7aske.grain.web.http.HttpResponse;
import com._7aske.grain.web.http.session.Session;
import com._7aske.grain.web.view.View;
import com._7aske.grain.web.view.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.IOException;

import static com._7aske.grain.web.http.HttpHeaders.CONTENT_TYPE;


/**
 * Thymeleaf integration. Simple implementation of a {@link ViewResolver} with
 * {@link ViewResolver#resolve(View, HttpRequest, HttpResponse, Session, Authentication)} implementation
 * that targets '.html' files is enough to allow usage of Thymeleaf in the project.
 * By default {@link com._7aske.grain.web.view.GtlViewResolver} resolves only files/templates
 * with '.gtl' extension so there will not be any conflict. Only thing that is an
 * exception is the {@link com._7aske.grain.ui.impl.DefaultLoginPage} which is not
 * a physical template, and therefore we cannot process it. Note the call to
 * {@link ViewResolver#populateImplicitObjects(View, HttpRequest, HttpResponse, Session, Authentication, Configuration)}
 * which is crucial for allowing usage of implicit objects in the template.
 *
 * @see View
 * @see ViewResolver
 * @see com._7aske.grain.web.view.ViewResolverProvider
 */
@Grain
public class ViewResolverConfig {

	@Grain
	ITemplateResolver htmlTemplateResolver() {
		StringTemplateResolver resolver
				= new StringTemplateResolver();
		resolver.setCacheable(false);
		return resolver;
	}

	@Grain
	TemplateEngine htmlTemplateEngine() {
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}

	@Grain
	ViewResolver thymeleafViewResolver(
			com._7aske.grain.core.configuration.Configuration configuration
	) {
		return new ViewResolver() {
			final TemplateEngine templateEngine = htmlTemplateEngine();

			@Override
			public boolean supports(View view) {
				return view.getName().endsWith(".html");
			}

			@Override
			public void resolve(View view, HttpRequest request, HttpResponse response, Session session, Authentication authentication) {
				populateImplicitObjects(view, request, response, session, authentication, configuration);

				Context context = new Context();
				context.setVariables(view.getAttributes());

				String body = templateEngine.process(view.getContent(), context);

				try {
					response.getOutputStream().write(body.getBytes());
					response.setHeader(CONTENT_TYPE, view.getContentType());
				} catch (IOException e) {
					throw new GrainRuntimeException(e);
				}
			}
		};
	}
}
