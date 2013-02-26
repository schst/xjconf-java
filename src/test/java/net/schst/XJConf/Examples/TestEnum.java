package net.schst.XJConf.Examples;

import java.util.ArrayList;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * @author Daniel Jahnke <daniel.jahnke@1und1.de>
 */
public final class TestEnum {

    private TestEnum() {
    }

    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-enum.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-enum-planet.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        ArrayList<PlanetInfo> list = (ArrayList<PlanetInfo>) conf.getConfigValue("planets");
        for (int i = 0; i < list.size(); i++) {
            PlanetInfo p = list.get(i);
            System.out.println("surface gravity on " + p.getPlanet() + " is: " + p.getSurfaceGravity() + " and has "
                    + p.getMoonNumber() + " moon(s)");
        }
    }

}
