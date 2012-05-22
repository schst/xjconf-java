package net.schst.XJConf.Examples;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class TestAttributesRequired {

    public static void main(String[] args) {
        DefinitionParser tagParser = new DefinitionParser();
      	NamespaceDefinitions defs;
		try {
			defs = tagParser.parse("src/test/resources/xml/defines-attributes-required.xml");
	        XmlReader conf = new XmlReader();
	        conf.setTagDefinitions(defs);
            conf.parse("src/test/resources/xml/test-attributes-required.xml");
            Color color = (Color)conf.getConfigValue("red");
            System.out.println(color);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }        
    }
}
