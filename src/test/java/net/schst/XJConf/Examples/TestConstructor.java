package net.schst.XJConf.Examples;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class TestConstructor {

    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-constructor.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-constructor.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        ConstructorColor color = (ConstructorColor)conf.getConfigValue("color");
        System.out.println(color);
        color = (ConstructorColor)conf.getConfigValue("color-no-atts");
        System.out.println(color);
        color = (ConstructorColor)conf.getConfigValue("color2");
        System.out.println(color);
        color = (ConstructorColor)conf.getConfigValue("color3");
        System.out.println(color);
    }
}