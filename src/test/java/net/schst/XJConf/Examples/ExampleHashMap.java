package net.schst.XJConf.Examples;

import java.util.HashMap;
import java.util.Properties;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class ExampleHashMap {

    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-hashmap.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-hashmap.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        HashMap map = (HashMap)conf.getConfigValue("map");
        System.out.println(map);

        Properties props = (Properties)conf.getConfigValue("properties");
        System.out.println(props);
    }
}