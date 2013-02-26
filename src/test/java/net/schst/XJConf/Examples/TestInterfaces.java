package net.schst.XJConf.Examples;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

public final class TestInterfaces {

    private TestInterfaces() {
    }

    /**
     * @param args
     * @throws XJConfException
     */
    public static void main(String[] args) throws XJConfException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-interfaces.xml");

        XmlReader conf = new XmlReader();
        conf.setTagDefinitions(defs);

        try {
            conf.parse("src/test/resources/xml/test-interfaces.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        MyClass foo = (MyClass) conf.getConfigValue("foo");
        System.out.println(foo);
        System.out.println(foo.getBar());
    }
}
