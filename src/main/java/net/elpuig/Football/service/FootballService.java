package net.elpuig.Football.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import net.elpuig.Football.model.League;
import net.elpuig.Football.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servicio que maneja todas las operaciones relacionadas con la API de fútbol
 * Proporciona métodos para obtener información sobre ligas, equipos y estadísticas
 * utilizando la API de football-api-sports.io
 */
@Service
public class FootballService {
    
    /** Cliente REST para realizar las peticiones HTTP */
    private final RestTemplate restTemplate;
    /** Clave de API para autenticación */
    private final String apiKey;
    /** URL base de la API */
    private final String baseUrl = "https://v3.football.api-sports.io";
    
    /**
     * Constructor del servicio
     * @param apiKey Clave de API inyectada desde la configuración
     */
    public FootballService(@Value("${football.api.key}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
    }
    
    /**
     * Crea las cabeceras HTTP necesarias para las peticiones a la API
     * @return HttpHeaders configuradas con la clave de API
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Using API Key: " + apiKey);
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", "v3.football.api-sports.io");
        return headers;
    }
    
    /**
     * Obtiene la lista de las principales ligas de fútbol
     * Solo incluye las 5 grandes ligas europeas: La Liga, Premier League, Serie A, Bundesliga y Ligue 1
     * @return Lista de ligas principales
     */
    public List<League> getLeagues() {
        try {
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());
            
            // Modificamos la URL para obtener las ligas de la temporada actual
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/leagues?season=2023", 
                    HttpMethod.GET, 
                    entity, 
                    Map.class);
            
            System.out.println("API Response Status: " + response.getStatusCode());
            
            List<League> leagues = new ArrayList<>();
            if (response.getBody() != null && response.getBody().get("response") != null) {
                List<Map<String, Object>> leaguesData = (List<Map<String, Object>>) response.getBody().get("response");
                System.out.println("Found " + leaguesData.size() + " leagues in total");
                
                // Lista de IDs de las ligas principales que queremos mostrar
                List<String> mainLeagueIds = List.of(
                    "140",  // La Liga
                    "39",   // Premier League
                    "135",  // Serie A
                    "78",   // Bundesliga
                    "61"    // Ligue 1
                );
                
                for (Map<String, Object> leagueData : leaguesData) {
                    Map<String, Object> leagueInfo = (Map<String, Object>) leagueData.get("league");
                    Map<String, Object> countryInfo = (Map<String, Object>) leagueData.get("country");
                    
                    if (leagueInfo != null && countryInfo != null) {
                        String leagueId = leagueInfo.get("id").toString();
                        // Solo añadir las ligas principales
                        if (mainLeagueIds.contains(leagueId)) {
                            League league = new League(
                                    leagueId,
                                    (String) leagueInfo.get("name"),
                                    (String) countryInfo.get("name"),
                                    (String) leagueInfo.get("logo")
                            );
                            leagues.add(league);
                            System.out.println("Added league: " + league.getName() + " (" + league.getId() + ")");
                        }
                    }
                }
            }
            
            System.out.println("Returning " + leagues.size() + " main leagues");
            return leagues;
            
        } catch (Exception e) {
            System.err.println("Error fetching leagues: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene todos los equipos de una liga específica
     * @param leagueId ID de la liga
     * @return Lista de equipos en la liga especificada
     */
    public List<Team> getTeamsByLeague(String leagueId) {
        if (leagueId == null || leagueId.isEmpty()) {
            System.out.println("Empty league ID provided");
            return new ArrayList<>();
        }
        
        try {
            System.out.println("Making API call for league ID: " + leagueId);
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/teams?league=" + leagueId + "&season=2023", 
                    HttpMethod.GET, 
                    entity, 
                    Map.class);
            
            System.out.println("API response status: " + response.getStatusCode());
            
            List<Team> teams = new ArrayList<>();
            
            if (response.getBody() != null && response.getBody().get("response") != null) {
                List<Map<String, Object>> teamsData = (List<Map<String, Object>>) response.getBody().get("response");
                System.out.println("Teams data size from API: " + teamsData.size());
                
                for (Map<String, Object> teamData : teamsData) {
                    Map<String, Object> teamInfo = (Map<String, Object>) teamData.get("team");
                    
                    if (teamInfo != null) {
                        Team team = new Team(
                                teamInfo.get("id").toString(),
                                (String) teamInfo.get("name"),
                                (String) teamInfo.get("logo")
                        );
                        team.setCountry((String) teamInfo.get("country"));
                        teams.add(team);
                    }
                }
            }
            
            return teams;
        } catch (Exception e) {
            System.out.println("Error fetching teams: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene información detallada de un equipo específico
     * Incluye datos del equipo, estadio y estadísticas actuales
     * @param teamId ID del equipo
     * @return Objeto Team con toda la información del equipo
     */
    public Team getTeamDetails(String teamId) {
        if (teamId == null || teamId.isEmpty()) {
            return new Team("0", "Unknown Team", "");
        }
        
        try {
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/teams?id=" + teamId, 
                    HttpMethod.GET, 
                    entity, 
                    Map.class);
            
            if (response.getBody() != null && response.getBody().get("response") != null) {
                List<Map<String, Object>> teamsData = (List<Map<String, Object>>) response.getBody().get("response");
                
                if (!teamsData.isEmpty()) {
                    Map<String, Object> teamData = teamsData.get(0);
                    Map<String, Object> teamInfo = (Map<String, Object>) teamData.get("team");
                    Map<String, Object> venueInfo = (Map<String, Object>) teamData.get("venue");
                    
                    if (teamInfo == null) {
                        return new Team(teamId, "Unknown Team", "");
                    }
                    
                    Team team = new Team(
                            teamInfo.get("id").toString(),
                            (String) teamInfo.get("name"),
                            (String) teamInfo.get("logo")
                    );
                    
                    // Procesar información del equipo
                    team.setCountry((String) teamInfo.get("country"));
                    team.setCode((String) teamInfo.get("code"));
                    
                    // Procesar año de fundación
                    Object foundedObj = teamInfo.get("founded");
                    if (foundedObj != null) {
                        try {
                            team.setFounded(Integer.parseInt(foundedObj.toString()));
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing founded year: " + e.getMessage());
                            team.setFounded(0);
                        }
                    }
                    
                    // Procesar información del estadio
                    if (venueInfo != null) {
                        String stadiumName = (String) venueInfo.get("name");
                        if (stadiumName != null && !stadiumName.isEmpty()) {
                            team.setStadium(stadiumName);
                            
                            // Procesar capacidad del estadio
                            Object capacityObj = venueInfo.get("capacity");
                            if (capacityObj != null) {
                                try {
                                    team.setStadiumCapacity(Integer.parseInt(capacityObj.toString()));
                                } catch (NumberFormatException e) {
                                    System.err.println("Error parsing stadium capacity: " + e.getMessage());
                                    team.setStadiumCapacity(0);
                                }
                            }
                            
                            // Procesar dirección y ciudad
                            String address = (String) venueInfo.get("address");
                            String city = (String) venueInfo.get("city");
                            team.setStadiumAddress(address != null ? address : "");
                            team.setStadiumCity(city != null ? city : "");
                        }
                    }
                    
                    // Obtener estadísticas del equipo
                    getTeamStatistics(team);
                    
                    return team;
                }
            }
            return new Team(teamId, "Unknown Team", "");
        } catch (Exception e) {
            System.err.println("Error fetching team details: " + e.getMessage());
            e.printStackTrace();
            return new Team(teamId, "Unknown Team", "");
        }
    }
    
    /**
     * Obtiene y actualiza las estadísticas de un equipo para la temporada actual
     * Incluye partidos jugados, victorias, derrotas, empates y goles
     * @param team Equipo al que se actualizarán las estadísticas
     */
    private void getTeamStatistics(Team team) {
        String teamId = team.getId();
        String currentLeagueId = getCurrentLeagueId(teamId);
        
        if (currentLeagueId != null) {
            try {
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());
            ResponseEntity<Map> statsResponse = restTemplate.exchange(
                    baseUrl + "/teams/statistics?team=" + teamId + "&league=" + currentLeagueId + "&season=2023", 
                    HttpMethod.GET, 
                    entity, 
                    Map.class);
            
            if (statsResponse.getBody() != null && statsResponse.getBody().get("response") != null) {
                Map<String, Object> stats = (Map<String, Object>) statsResponse.getBody().get("response");
                
                    // Procesar estadísticas de partidos
                if (stats.get("fixtures") != null) {
                    Map<String, Object> fixtures = (Map<String, Object>) stats.get("fixtures");
                        
                        // Partidos jugados
                        Map<String, Object> played = (Map<String, Object>) fixtures.get("played");
                        if (played != null && played.get("total") != null) {
                            team.setMatchesPlayed(Integer.parseInt(played.get("total").toString()));
                    }
                    
                        // Victorias
                        Map<String, Object> wins = (Map<String, Object>) fixtures.get("wins");
                        if (wins != null && wins.get("total") != null) {
                            team.setWins(Integer.parseInt(wins.get("total").toString()));
                    }
                    
                        // Empates
                        Map<String, Object> draws = (Map<String, Object>) fixtures.get("draws");
                        if (draws != null && draws.get("total") != null) {
                            team.setDraws(Integer.parseInt(draws.get("total").toString()));
                    }
                    
                        // Derrotas
                        Map<String, Object> loses = (Map<String, Object>) fixtures.get("loses");
                        if (loses != null && loses.get("total") != null) {
                            team.setLosses(Integer.parseInt(loses.get("total").toString()));
                        }
                    }
                    
                    // Procesar estadísticas de goles
                if (stats.get("goals") != null) {
                    Map<String, Object> goals = (Map<String, Object>) stats.get("goals");
                        
                        // Goles a favor
                        Map<String, Object> goalsFor = (Map<String, Object>) goals.get("for");
                        if (goalsFor != null && goalsFor.get("total") != null) {
                            Map<String, Object> total = (Map<String, Object>) goalsFor.get("total");
                            if (total != null) {
                                team.setGoalsScored(Integer.parseInt(total.toString()));
                            }
                        }
                        
                        // Goles en contra
                        Map<String, Object> goalsAgainst = (Map<String, Object>) goals.get("against");
                        if (goalsAgainst != null && goalsAgainst.get("total") != null) {
                            Map<String, Object> total = (Map<String, Object>) goalsAgainst.get("total");
                            if (total != null) {
                                team.setGoalsConceded(Integer.parseInt(total.toString()));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error fetching team statistics: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Obtiene el ID de la liga actual de un equipo
     * @param teamId ID del equipo
     * @return ID de la liga en la que juega actualmente el equipo
     */
    private String getCurrentLeagueId(String teamId) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());
            // Get the leagues where the team currently plays
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/leagues?team=" + teamId + "&current=true&season=2023", 
                    HttpMethod.GET, 
                    entity, 
                    Map.class);
            
            if (response.getBody() != null && response.getBody().get("response") != null) {
                List<Map<String, Object>> leaguesData = (List<Map<String, Object>>) response.getBody().get("response");
                
                if (!leaguesData.isEmpty()) {
                    // Get the first league as the main league
                    Map<String, Object> leagueData = leaguesData.get(0);
                    Map<String, Object> leagueInfo = (Map<String, Object>) leagueData.get("league");
                    
                    if (leagueInfo != null && leagueInfo.get("id") != null) {
                        return leagueInfo.get("id").toString();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching current league for team: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene el nombre de una liga por su ID
     * @param leagueId ID de la liga
     * @return Nombre de la liga o "Unknown League" si no se encuentra
     */
    public String getLeagueName(String leagueId) {
        if (leagueId == null || leagueId.isEmpty()) {
            return "Unknown League";
        }
        
        try {
            HttpEntity<String> entity = new HttpEntity<>(createHeaders());
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/leagues?id=" + leagueId, 
                    HttpMethod.GET, 
                    entity, 
                    Map.class);
            
            if (response.getBody() != null && response.getBody().get("response") != null) {
                List<Map<String, Object>> leaguesData = (List<Map<String, Object>>) response.getBody().get("response");
                
                if (!leaguesData.isEmpty()) {
                    Map<String, Object> leagueData = leaguesData.get(0);
                    Map<String, Object> leagueInfo = (Map<String, Object>) leagueData.get("league");
                    
                    if (leagueInfo != null && leagueInfo.get("name") != null) {
                        return (String) leagueInfo.get("name");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching league name: " + e.getMessage());
        }
        
        return "Unknown League";
    }

    /**
     * Obtiene todos los equipos de todas las ligas principales
     * @return Lista combinada de todos los equipos
     */
    public List<Team> getAllTeams() {
        List<Team> allTeams = new ArrayList<>();
        List<League> leagues = getLeagues();
        
        for (League league : leagues) {
            List<Team> teams = getTeamsByLeague(league.getId());
            if (teams != null && !teams.isEmpty()) {
                allTeams.addAll(teams);
            }
        }
        
        return allTeams;
    }
}
