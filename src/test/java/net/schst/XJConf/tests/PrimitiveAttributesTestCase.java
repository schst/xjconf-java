package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.tests.helpers.PrimitivesContainer;
import junit.framework.TestCase;

public class PrimitiveAttributesTestCase extends TestCase {

    private DefinitionParser parser = new DefinitionParser();;
    private XmlReader xmlReader = new XmlReader();
    private PrimitivesContainer container;
    
    protected void setUp() throws Exception {
        super.setUp();
        NamespaceDefinitions defs = this.parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);
        
        this.xmlReader.parse("src/test/resources/tests/xml/PrimitiveAttributesTestCase.xml");
        this.container = (PrimitivesContainer)this.xmlReader.getConfigValue("container");
    }
    
    public void testBooleanValue() throws Exception {
        TestCase.assertTrue(this.container.getBooleanValue());
    }

    public void testIntValue() throws Exception {
        TestCase.assertEquals(15, this.container.getIntValue());
    }

    public void testLongValue() throws Exception {
        TestCase.assertEquals(42, this.container.getLongValue());
    }

    public void testFloatValue() throws Exception {        
        TestCase.assertEquals(12.34f, this.container.getFloatValue());
    }

    public void testDoubleValue() throws Exception {        
        TestCase.assertEquals(12.34d, this.container.getDoubleValue());
    }

    public void testShortValue() throws Exception {        
        TestCase.assertEquals(-34, this.container.getShortValue());
    }
}