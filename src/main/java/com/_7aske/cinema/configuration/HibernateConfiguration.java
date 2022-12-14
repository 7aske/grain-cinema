package com._7aske.cinema.configuration;

import com._7aske.cinema.CinemaApp;
import com._7aske.grain.core.component.Grain;
import com._7aske.grain.util.classloader.GrainClassLoader;
import com._7aske.grain.util.classloader.GrainJarClassLoader;
import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;

/**
 * Integration with Hibernate. All of Hibernates required configuration properties
 * are read from System#getProperties() which are loaded from application.properties
 * file. Only thing that is required to do "manually" is to scan for and load
 * Entity classes which this class is responsible for. This replaces Grain's
 * persistence layer, therefore it must not be enabled in the application.properties.
 *
 * @see com._7aske.grain.orm.database.DatabaseExecutor
 */
@Grain
public class HibernateConfiguration {

	@Grain
	public SessionFactory sessionFactory() {
		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

		GrainClassLoader grainClassLoader = new GrainJarClassLoader(CinemaApp.class.getPackageName());
		grainClassLoader.loadClasses(cl -> cl.isAnnotationPresent(Entity.class))
				.forEach(configuration::addAnnotatedClass);

		return configuration.buildSessionFactory();
	}
}
