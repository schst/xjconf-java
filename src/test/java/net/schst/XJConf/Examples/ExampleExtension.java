package net.schst.XJConf.Examples;

import java.util.ArrayList;
import java.util.HashMap;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;

public final class ExampleExtension {

    private ExampleExtension() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-extension.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        conf.addExtension("net.schst.XJConf.ext.Math");

        try {
            conf.parse("src/test/resources/xml/test-extension.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        HashMap map = (HashMap) conf.getConfigValue("map");
        System.out.println(map);
        ArrayList array = (ArrayList) conf.getConfigValue("array");
        System.out.println(array);
    }
}
