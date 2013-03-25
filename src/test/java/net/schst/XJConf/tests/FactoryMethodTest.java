package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.tests.helpers.FactoryMethodClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactoryMethodTest {

    private DefinitionParser parser = new DefinitionParser();
    private XmlReader xmlReader = new XmlReader();

    @Before
    public void setUp() throws Exception {
        NamespaceDefinitions defs = parser.parse("src/test/resources/tests/defines/FactoryMethodTestCase.xml");
        xmlReader.addTagDefinitions(defs);
    }

    /*
     * Test a factory method without any parameters
     */
    @Test
    public void testFactoryMethod() throws Exception {
        xmlReader.parse("src/test/resources/tests/xml/FactoryMethodTestCase-1.xml");

        Object value = xmlReader.getConfigValue("foo");
        Assert.assertTrue(value instanceof FactoryMethodClass);
    }

    /*
     * Test a factory method without any parameters
     */
    @Test
    public void testFactoryMethodWithParam() throws Exception {
        xmlReader.parse("src/test/resources/tests/xml/FactoryMethodTestCase-2.xml");

        Object value = xmlReader.getConfigValue("bar");
        Assert.assertTrue(value instanceof FactoryMethodClass);

        FactoryMethodClass value2 = (FactoryMethodClass) value;
        Assert.assertEquals("It works!", value2.getParam());
    }

}
