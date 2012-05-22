package net.schst.XJConf.Examples;

import java.io.File;
import java.util.Set;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Example that shows how to use ArrayList and the keyAttribute
 * 
 * @author sschmidt
 */
public class Example3 {

	public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        File defines = new File("src/test/resources/xml/defines3.xml");
        NamespaceDefinitions defs = tagParser.parse(defines);

        Set namespaces = defs.getDefinedNamespaces();
        
        System.out.println("Defined namespaces:");
        System.out.println(namespaces);
        System.out.println();
        
        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);
        
        try {
            conf.parse("src/test/resources/xml/test3.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        Integer zahl = (Integer)conf.getConfigValue("zahl");
        System.out.println("getConfigValue(zahl) " + zahl);
        String text = (String)conf.getConfigValue("text");
        System.out.println("getConfigValue(text) " + text);
	}
}