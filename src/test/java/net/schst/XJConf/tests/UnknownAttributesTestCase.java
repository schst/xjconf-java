package net.schst.XJConf.tests;

import junit.framework.TestCase;
import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.UnknownAttributeException;

public class UnknownAttributesTestCase extends TestCase {

    private DefinitionParser parser = new DefinitionParser();;
    private XmlReader xmlReader = new XmlReader();
    
    protected void setUp() throws Exception {
        super.setUp();
        NamespaceDefinitions defs = this.parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);
    }
    
    public void testParse() throws Exception {
        try {
            this.xmlReader.parse("src/test/resources/tests/xml/UnknownAttributesTestCase.xml");
            TestCase.fail("Expecting UnknownAttributeException");
        } catch (UnknownAttributeException ex) {
            TestCase.assertEquals(ex.getMessage(), "The attribute unknownAttribute has not been defined for the tag container.");
        }
    }
}