package net.elpuig.Football.model;

/**
 * Clase que representa una liga de fútbol
 * Contiene la información básica de una liga, incluyendo su identificador,
 * nombre, país al que pertenece y logo
 */
public class League {
    /** Identificador único de la liga */
    private String id;
    /** Nombre de la liga */
    private String name;
    /** País al que pertenece la liga */
    private String country;
    /** URL del logo de la liga */
    private String logo;
    
    /**
     * Constructor por defecto
     */
    public League() {
    }
    
    /**
     * Constructor con todos los parámetros
     * @param id Identificador de la liga
     * @param name Nombre de la liga
     * @param country País de la liga
     * @param logo URL del logo de la liga
     */
    public League(String id, String name, String country, String logo) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.logo = logo;
    }
    
    /**
     * Obtiene el ID de la liga
     * @return ID de la liga
     */
    public String getId() {
        return id;
    }
    
    /**
     * Establece el ID de la liga
     * @param id Nuevo ID de la liga
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Obtiene el nombre de la liga
     * @return Nombre de la liga
     */
    public String getName() {
        return name;
    }
    
    /**
     * Establece el nombre de la liga
     * @param name Nuevo nombre de la liga
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Obtiene el país de la liga
     * @return País de la liga
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * Establece el país de la liga
     * @param country Nuevo país de la liga
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * Obtiene la URL del logo de la liga
     * @return URL del logo de la liga
     */
    public String getLogo() {
        return logo;
    }
    
    /**
     * Establece la URL del logo de la liga
     * @param logo Nueva URL del logo de la liga
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
} 