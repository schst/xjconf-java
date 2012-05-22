package net.schst.XJConf.tests;

import junit.framework.TestCase;
import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.tests.helpers.FactoryMethodClass;

public class FactoryMethodTestCase extends TestCase {

    private DefinitionParser parser = new DefinitionParser();;
    private XmlReader xmlReader = new XmlReader();
    
    protected void setUp() throws Exception {
        super.setUp();
        NamespaceDefinitions defs = this.parser.parse("src/test/resources/tests/defines/FactoryMethodTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);
    }

    /**
     * Test a factory method without any parameters
     * 
     * @throws Exception
     */
    public void testFactoryMethod() throws Exception {
        this.xmlReader.parse("src/test/resources/tests/xml/FactoryMethodTestCase-1.xml");
    	
        Object value = this.xmlReader.getConfigValue("foo");
        assertTrue(value instanceof FactoryMethodClass);
    }

    /**
     * Test a factory method without any parameters
     * 
     * @throws Exception
     */
    public void testFactoryMethodWithParam() throws Exception {
        this.xmlReader.parse("src/test/resources/tests/xml/FactoryMethodTestCase-2.xml");
    	
        Object value = this.xmlReader.getConfigValue("bar");
        assertTrue(value instanceof FactoryMethodClass);
        
        FactoryMethodClass value2 = (FactoryMethodClass)value;
        assertEquals("It works!", value2.getParam());
    }
}