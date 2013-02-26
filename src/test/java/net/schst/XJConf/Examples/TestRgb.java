package net.schst.XJConf.Examples;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author Daniel Jahnke <daniel.jahnke@1und1.de>
 */

public final class TestRgb {

    private TestRgb() {
    }

    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-enum-2.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-enum2.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        Rgb result = (Rgb) conf.getConfigValue("rgb");
        System.out.println(result.toString());
    }

}
