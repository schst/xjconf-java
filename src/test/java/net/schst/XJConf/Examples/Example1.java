/*
 * Created on 24.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.schst.XJConf.Examples;

import java.io.File;
import java.util.ArrayList;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author sschmidt
 */
public class Example1 {

	public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        File defines = new File("src/test/resources/xml/defines.xml");

        NamespaceDefinitions defs = tagParser.parse(defines);

        System.out.println("Number of defined tags: " + defs.countTagDefinitions());

        XmlReader conf = new XmlReader();
        try {
            conf.setTagDefinitions(defs);
    
            conf.parse("src/test/resources/xml/test.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        String foo = (String)conf.getConfigValue("foo");
        System.out.println("getConfigValue(foo) " + foo);

        Integer zahl = (Integer)conf.getConfigValue("zahl");
        System.out.println("getConfigValue(zahl) " + zahl);
        
        UpperString schst = (UpperString)conf.getConfigValue("schst");
        System.out.println("getConfigValue(schst) " + schst.getString());

        Complex bar = (Complex)conf.getConfigValue("complex");
        System.out.println("getConfigValue(complex) " + bar.render());

        Complex bar2 = (Complex)conf.getConfigValue("complex2");
        System.out.println("getConfigValue(complex2) " + bar2.render());

        ArrayList arr = (ArrayList)conf.getConfigValue("array");
        System.out.println("getConfigValue(array) " + arr);

        // with default values
        Complex bar3 = (Complex)conf.getConfigValue("complex3");
        System.out.println("getConfigValue(complex3) " + bar3.render());
        
        System.out.println(arr.get(0));
    }
}