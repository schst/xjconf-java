package net.schst.XJConf.Examples;

import java.io.File;
import java.util.ArrayList;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Example that shows how to use ArrayList and the keyAttribute.
 *
 * @author sschmidt
 */
public final class Example2 {

    private Example2() {
    }

    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        File defines = new File("src/test/resources/xml/defines2.xml");
        NamespaceDefinitions defs = tagParser.parse(defines);

        System.out.println("Number of defined tags: " + defs.countTagDefinitions());

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test2.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        Integer one = (Integer) conf.getConfigValue("one");
        System.out.println("getConfigValue(one) " + one);
        Integer two = (Integer) conf.getConfigValue("two");
        System.out.println("getConfigValue(two) " + two);
        Integer three = (Integer) conf.getConfigValue("three");
        System.out.println("getConfigValue(three) " + three);

        ArrayList colors = (ArrayList) conf.getConfigValue("colors");
        System.out.println("getConfigValue(colors) " + colors);
    }
}
