package net.schst.XJConf.Examples;

public enum Planet {
    MERCURY (3.303e+23, 2.4397e6,  0),
    VENUS   (4.869e+24, 6.0518e6,  0),
    EARTH   (5.976e+24, 6.37814e6, 1),
    MARS    (6.421e+23, 3.3972e6,  2),
    JUPITER (1.9e+27,   7.1492e7,  63),
    SATURN  (5.688e+26, 6.0268e7,  60),
    URANUS  (8.686e+25, 2.5559e7,  27),
    NEPTUNE (1.024e+26, 2.4746e7,  13);
    
    private final double mass;   // in kilograms
    private final double radius; // in meters
    private final int moonNumber; // moon number
    
    public static final double G = 6.67300E-11;
    
    Planet(double mass, double radius, int moonNumber) {
        this.mass = mass;
        this.radius = radius;
        this.moonNumber = moonNumber;
    }

    public int getMoonNumber() {
        return this.moonNumber;
    }
    
    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }
}
