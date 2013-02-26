package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.UnknownAttributeException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnknownAttributesTest {

    private DefinitionParser parser = new DefinitionParser();
    private XmlReader xmlReader = new XmlReader();

    @Before
    public void setUp() throws Exception {
        NamespaceDefinitions defs =
                this.parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);
    }

    @Test
    public void testParse() throws Exception {
        try {
            this.xmlReader.parse("src/test/resources/tests/xml/UnknownAttributesTestCase.xml");
            Assert.fail("Expecting UnknownAttributeException");
        } catch (UnknownAttributeException ex) {
            Assert.assertEquals(ex.getMessage(),
                    "The attribute unknownAttribute has not been defined for the tag container.");
        }
    }

}
