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
public class ColorPrimitives {
    private int red;
    private int green;
    private int blue;
    private String name = null;
    private String colorTitle = null;

    public ColorPrimitives(String name) {
        this.name = name;
    }

    public void setRed(int val) {
        red = val;
    }
    public void setGreen(int val) {
        green = val;
    }
    public void setBlue(int val) {
        blue = val;
    }

    public void setColorTitle(String title) {
        colorTitle = title;
    }

    public String getRGB() {
        return "#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
    }

    public String getName() {
        return name;
    }

    public String getColorTitle() {
        return colorTitle;
    }

    public String toString() {
        return name + "(" + getRGB() + ")";
    }
}
