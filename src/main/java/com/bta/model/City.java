package com.bta.model;

public class City {
    private Long id;
    private String name;
    private Long population;
    private String description;

    //ManyToOne
    private Country country;

    public City(Long id, String name, Long population, String description, Country country) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.description = description;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
