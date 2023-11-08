package com.negocio.movil_ejemplopaises.entity;

public class Pais {

    private String name;
    private String capital;
    private String region;
    private Long population;
    private Flag flags;

    public Flag getFlags() {
        return flags;
    }

    public void setFlags(Flag flags) {
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }
}
