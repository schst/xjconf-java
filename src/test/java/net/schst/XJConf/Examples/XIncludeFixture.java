package net.schst.XJConf.Examples;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.Extension;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;
import net.schst.XJConf.ext.XInclude;

public class XIncludeFixture {

    protected XmlReader xmlReader;

    public void setUp(String ... definesFiles) throws XJConfException {
        XmlReader reader = new XmlReader();

        DefinitionParser tagParser = new DefinitionParser();
        for (String definesFile : definesFiles) {
            NamespaceDefinitions defs = tagParser.parse(definesFile);
            reader.addTagDefinitions(defs);
        }

        Extension xinc = new XInclude();
        reader.addExtension("http://www.w3.org/2001/XInclude", xinc);
        this.xmlReader = reader;
    }
}
