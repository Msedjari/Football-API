package net.elpuig.Football.model;

/**
 * Clase que representa un equipo de fútbol
 * Contiene toda la información relevante sobre un equipo, incluyendo datos del estadio,
 * estadísticas de la temporada y detalles generales del club
 */
public class Team {
    /** Identificador único del equipo */
    private String id;
    /** Nombre del equipo */
    private String name;
    /** URL del logo del equipo */
    private String logo;
    /** Nombre del estadio */
    private String stadium;
    /** Capacidad del estadio */
    private int stadiumCapacity;
    /** Dirección del estadio */
    private String stadiumAddress;
    /** Ciudad donde se encuentra el estadio */
    private String stadiumCity;
    /** URL de la imagen del estadio */
    private String stadiumImage;
    /** País del equipo */
    private String country;
    /** Año de fundación del equipo */
    private int founded;
    /** Código identificativo del equipo */
    private String code;
    /** Número total de partidos jugados */
    private int matchesPlayed;
    /** Número de victorias */
    private int wins;
    /** Número de empates */
    private int draws;
    /** Número de derrotas */
    private int losses;
    /** Goles marcados */
    private int goalsScored;
    /** Goles recibidos */
    private int goalsConceded;
    
    /**
     * Constructor por defecto
     */
    public Team() {
    }
    
    /**
     * Constructor con parámetros básicos
     * @param id Identificador del equipo
     * @param name Nombre del equipo
     * @param logo URL del logo del equipo
     */
    public Team(String id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }
    
    // Getters and Setters
    /**
     * Obtiene el ID del equipo
     * @return ID del equipo
     */
    public String getId() {
        return id;
    }
    
    /**
     * Establece el ID del equipo
     * @param id Nuevo ID del equipo
     */
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public String getStadium() {
        return stadium;
    }
    
    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
    
    public int getStadiumCapacity() {
        return stadiumCapacity;
    }
    
    public void setStadiumCapacity(int stadiumCapacity) {
        this.stadiumCapacity = stadiumCapacity;
    }
    
    public String getStadiumAddress() {
        return stadiumAddress;
    }
    
    public void setStadiumAddress(String stadiumAddress) {
        this.stadiumAddress = stadiumAddress;
    }
    
    public String getStadiumCity() {
        return stadiumCity;
    }
    
    public void setStadiumCity(String stadiumCity) {
        this.stadiumCity = stadiumCity;
    }
    
    public String getStadiumImage() {
        return stadiumImage;
    }
    
    public void setStadiumImage(String stadiumImage) {
        this.stadiumImage = stadiumImage;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public int getFounded() {
        return founded;
    }
    
    public void setFounded(int founded) {
        this.founded = founded;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public int getMatchesPlayed() {
        return matchesPlayed;
    }
    
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
    
    public int getWins() {
        return wins;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public int getDraws() {
        return draws;
    }
    
    public void setDraws(int draws) {
        this.draws = draws;
    }
    
    public int getLosses() {
        return losses;
    }
    
    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    public int getGoalsScored() {
        return goalsScored;
    }
    
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }
    
    public int getGoalsConceded() {
        return goalsConceded;
    }
    
    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }
    
    /**
     * Calcula los puntos totales del equipo
     * Se calculan sumando 3 puntos por victoria y 1 punto por empate
     * @return Total de puntos del equipo
     */
    public int getPoints() {
        return (wins * 3) + draws;
    }
    
    /**
     * Calcula la diferencia de goles del equipo
     * Se calcula restando los goles recibidos de los goles marcados
     * @return Diferencia de goles
     */
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }
} 