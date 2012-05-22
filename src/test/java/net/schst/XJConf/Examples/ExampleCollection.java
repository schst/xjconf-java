package net.schst.XJConf.Examples;

import java.util.ArrayList;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class ExampleCollection {

    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-collection.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-collection.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        ArrayList list = (ArrayList)conf.getConfigValue("list");
        System.out.println(list);
    }
}
