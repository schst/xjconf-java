package net.schst.XJConf.Examples;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class TestPrimitives {

    public static void main(String[] args) throws XJConfException {

        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-primitives.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-primitives.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        ColorPrimitives color = (ColorPrimitives)conf.getConfigValue("color");
        System.out.println(color);
    }
}
