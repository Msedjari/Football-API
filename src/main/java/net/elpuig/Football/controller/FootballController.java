package net.elpuig.Football.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.elpuig.Football.service.FootballService;
import net.elpuig.Football.model.Team;
import java.util.List;

/**
 * Controlador principal de la aplicación de fútbol
 * Maneja todas las peticiones HTTP relacionadas con la visualización de ligas y equipos
 */
@Controller
public class FootballController {
    
    /** Servicio que proporciona la lógica de negocio y acceso a datos */
    @Autowired
    private FootballService footballService;
    
    /**
     * Maneja la petición a la página principal
     * Muestra la lista de ligas disponibles
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista a renderizar
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("leagues", footballService.getLeagues());
        return "home";
    }
    
    /**
     * Maneja la petición para ver equipos
     * Puede mostrar todos los equipos o filtrar por liga
     * @param leagueId ID de la liga para filtrar (opcional)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista a renderizar
     */
    @GetMapping("/teams")
    public String getTeams(@RequestParam(required = false) String leagueId, Model model) {
        List<Team> teams;
        String leagueName = "All Teams";
        
        if (leagueId != null && !leagueId.isEmpty()) {
            // Si se especifica una liga, mostrar solo los equipos de esa liga
            teams = footballService.getTeamsByLeague(leagueId);
            leagueName = footballService.getLeagueName(leagueId);
        } else {
            // Si no se especifica una liga, mostrar todos los equipos
            teams = footballService.getAllTeams();
        }
        
        model.addAttribute("teams", teams);
        model.addAttribute("leagueName", leagueName);
        model.addAttribute("leagueId", leagueId != null ? leagueId : "all");
        
        return "teams";
    }
    
    /**
     * Maneja la petición para ver los detalles de un equipo específico
     * @param teamId ID del equipo a mostrar
     * @param leagueId ID de la liga (opcional, para navegación)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista a renderizar
     */
    @GetMapping("/team")
    public String getTeamDetails(@RequestParam String teamId, 
                                @RequestParam(required = false) String leagueId, 
                                Model model) {
        model.addAttribute("team", footballService.getTeamDetails(teamId));
        model.addAttribute("leagueId", leagueId);
        return "team-details";
    }

    /**
     * Maneja las peticiones que resultan en error
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista de error
     */
    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }
} 