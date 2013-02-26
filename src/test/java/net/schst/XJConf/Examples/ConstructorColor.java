package net.schst.XJConf.Examples;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class ConstructorColor {
    private Integer red = null;
    private Integer green = null;
    private Integer blue = null;

    /**
     * @param red
     * @param green
     * @param blue
     */
    public ConstructorColor(Integer red, Integer green, Integer blue) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * @return Returns the blue.
     */
    public Integer getBlue() {
        return this.blue;
    }
    /**
     * @return Returns the green.
     */
    public Integer getGreen() {
        return this.green;
    }
    /**
     * @return Returns the red.
     */
    public Integer getRed() {
        return this.red;
    }

    public String toString() {
        return "R: " + this.getRed() + " / G: " + this.getGreen() + " / B: " + this.getBlue();
    }
}
