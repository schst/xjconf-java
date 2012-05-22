package net.schst.XJConf.Examples;

public class PlanetInfo {

    private Planet planet;
    
    public void setPlanet(Planet planet) {
        this.planet = planet;   
    }
    
    public Planet getPlanet() {
        return planet;
    }
    
    public double getSurfaceGravity() {
        return planet.surfaceGravity();
    }
    
    public int getMoonNumber() {
        return planet.getMoonNumber();
    }
}
