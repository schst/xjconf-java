/*
 * Created on 25.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.schst.XJConf.Examples;

/**
 * @author sschmidt
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Color {
    private Integer red = null;
    private Integer green = null;
    private Integer blue = null;
    private String name = null;
    private String colorTitle = null;

    public Color(String name) {
        this.name = name;
    }

    public void setRed(Integer val) {
        this.red = val;
    }
    public void setGreen(Integer val) {
        this.green = val;
    }
    public void setBlue(Integer val) {
        this.blue = val;
    }

    public void setColorTitle(String title) {
        this.colorTitle = title;
    }

    public String getRGB() {
        return "#" + Integer.toHexString(this.red.intValue()) + Integer.toHexString(this.green.intValue())
                + Integer.toHexString(this.blue.intValue());
    }

    public String getName() {
        return this.name;
    }

    public String getColorTitle() {
        return this.colorTitle;
    }

    public String toString() {
        return this.name + "(" + this.getRGB() + ")";
    }
}
