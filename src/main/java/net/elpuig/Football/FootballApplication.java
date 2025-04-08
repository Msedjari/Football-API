/**
 * Clase principal de la aplicación Football
 * Esta clase inicia la aplicación Spring Boot y configura el contexto de la aplicación
 */
package net.elpuig.Football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que marca el punto de entrada de la aplicación Spring Boot
 * La anotación @SpringBootApplication combina:
 * - @Configuration: Para definir beans de configuración
 * - @EnableAutoConfiguration: Para configuración automática de Spring Boot
 * - @ComponentScan: Para escanear componentes en el paquete actual y subpaquetes
 */
@SpringBootApplication
public class FootballApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot
	 * @param args Argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(FootballApplication.class, args);
	}

}
