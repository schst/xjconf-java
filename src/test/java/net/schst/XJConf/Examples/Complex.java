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
public class Complex {
    private String data = null;
    private Color color = null;
    private String colorString = null;
    private Integer size = new Integer(1);

    public Complex(String data) {
        this.data = data;
    }

    public void setColor(String color) {
        this.colorString = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String render() {
        if (this.color == null) {
            return "<font color=\"" + this.colorString + "\" size=\"" + this.size.toString() + "\">" + this.data
                    + "</font>";
        } else {
            return "<font title=\"This text is written in " + this.color.getName() + " (" + this.color.getColorTitle()
                    + ") \" color=\"" + this.color.getRGB() + "\" size=\"" + this.size.toString() + "\">" + this.data
                    + "</font>";
        }
    }
}
