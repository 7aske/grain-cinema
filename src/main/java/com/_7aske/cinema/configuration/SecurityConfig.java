package com._7aske.cinema.configuration;

import com._7aske.grain.core.component.Grain;
import com._7aske.grain.logging.Logger;
import com._7aske.grain.logging.LoggerFactory;
import com._7aske.grain.security.config.SecurityConfigurer;
import com._7aske.grain.security.config.builder.SecurityConfigurationBuilder;

/**
 * Replaces the default {@link com._7aske.grain.security.config.DefaultSecurityConfigurer}
 * and allow security configuration customization.
 */
@Grain
public class SecurityConfig implements SecurityConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	@Override
	public void configure(SecurityConfigurationBuilder sec) {
		logger.debug("Configuring security");
		sec.withRules()
				.urlPattern("/screenings/*/reservations/*").authenticated().and()
				.urlPattern("/admin/**").authenticated().roles("ADMIN")
				.buildRules();
	}
}
