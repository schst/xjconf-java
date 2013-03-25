package net.schst.XJConf.Examples;

/**
 * @author sschmidt
 */
public class Complex {
    private String data = null;
    private Color color = null;
    private String colorString = null;
    private Integer size = 1;

    public Complex(String data) {
        this.data = data;
    }

    public void setColor(String color) {
        colorString = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String render() {
        if (color == null) {
            return "<font color=\"" + colorString + "\" size=\"" + size.toString() + "\">" + data
                    + "</font>";
        }
        return "<font title=\"This text is written in " + this.color.getName() + " (" + this.color.getColorTitle()
                + ") \" color=\"" + this.color.getRGB() + "\" size=\"" + this.size.toString() + "\">" + this.data
                + "</font>";
    }

}
