package net.schst.XJConf.Examples;

import java.util.HashMap;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.Extension;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;
import net.schst.XJConf.ext.XInclude;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class ExampleInclude {
    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-hashmap.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);
        
        Extension xinc = new XInclude();
        conf.addExtension("http://www.w3.org/2001/XInclude", xinc);

        try {
            conf.parse("src/test/resources/xml/test-xinclude.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        HashMap map = (HashMap)conf.getConfigValue("map");
        System.out.println(map);
    }
}