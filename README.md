# Football-API

Una aplicación web Spring Boot que proporciona información sobre las principales ligas de fútbol europeas y sus equipos, utilizando la API de football-api-sports.io.

## Características

- Visualización de las 5 grandes ligas europeas:
  - La Liga (España)
  - Premier League (Inglaterra)
  - Serie A (Italia)
  - Bundesliga (Alemania)
  - Ligue 1 (Francia)
- Información detallada de equipos:
  - Datos básicos (nombre, país, año de fundación)
  - Estadísticas de la temporada actual (partidos, victorias, derrotas, goles)
  - Información del estadio (nombre, capacidad, ubicación)
- Filtrado de equipos por liga
- Interfaz web intuitiva y responsive

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf (motor de plantillas)
- football-api-sports.io API
- Maven (gestión de dependencias)

## Requisitos Previos

- Java JDK 17 o superior
- Maven 3.6 o superior
- Una clave de API de football-api-sports.io

## Configuración

1. Clona el repositorio:
```bash
git clone https://github.com/Msedjari/Football-API.git
```

2. Configura tu clave de API:
   - Crea un archivo `application.properties` en `src/main/resources/`
   - Añade tu clave de API:
```properties
football.api.key=tu_clave_api_aquí
```

3. Compila el proyecto:
```bash
mvn clean install
```

4. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

5. Accede a la aplicación en tu navegador:
```
http://localhost:8080
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── net/elpuig/Football/
│   │       ├── controller/    # Controladores MVC
│   │       ├── model/        # Entidades
│   │       └── service/      # Lógica de negocio
│   └── resources/
│       ├── static/          # Recursos estáticos (CSS, JS)
│       └── templates/       # Plantillas Thymeleaf
```

## Endpoints API

- `GET /` - Página principal con lista de ligas
- `GET /teams` - Lista de equipos (todos o filtrados por liga)
- `GET /team?teamId={id}` - Detalles de un equipo específico

## Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Haz fork del proyecto
2. Crea una rama para tu funcionalidad (`git checkout -b feature/AmazingFeature`)
3. Haz commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Autor

* **Msedjari** - *Trabajo Inicial* - [Msedjari](https://github.com/Msedjari)

