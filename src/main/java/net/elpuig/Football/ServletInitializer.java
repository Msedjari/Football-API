/**
 * Inicializador del Servlet para despliegue en servidores de aplicaciones web
 * Esta clase permite que la aplicación Spring Boot se ejecute como una aplicación web tradicional
 */
package net.elpuig.Football;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase que configura la aplicación para su despliegue en un servidor de aplicaciones web
 * Extiende SpringBootServletInitializer para permitir el inicio de la aplicación en un contenedor servlet
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación Spring Boot para su despliegue web
	 * @param application Constructor de la aplicación Spring
	 * @return SpringApplicationBuilder configurado con la clase principal de la aplicación
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FootballApplication.class);
	}

}
