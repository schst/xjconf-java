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
public class UpperString {
    private String data = null;
    
	public UpperString(String data) {
        this.data = data.toUpperCase();
    }
    
    public String getString() {
        return this.data;
    }
}