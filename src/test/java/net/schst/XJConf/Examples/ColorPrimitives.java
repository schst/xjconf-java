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
        this.red = val;
    }
    public void setGreen(int val) {
        this.green = val;
    }
    public void setBlue(int val) {
        this.blue = val;
    }

    public void setColorTitle(String title) {
        this.colorTitle = title;
    }

    public String getRGB() {
        return "#" + Integer.toHexString(this.red) + Integer.toHexString(this.green) + Integer.toHexString(this.blue);
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
