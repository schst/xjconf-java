package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.tests.helpers.PrimitivesContainer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrimitiveAttributesTest {

    private DefinitionParser parser = new DefinitionParser();
    private XmlReader xmlReader = new XmlReader();
    private PrimitivesContainer container;

    @Before
    public void setUp() throws Exception {
        NamespaceDefinitions defs =
                parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        xmlReader.addTagDefinitions(defs);

        xmlReader.parse("src/test/resources/tests/xml/PrimitiveAttributesTestCase.xml");
        container = (PrimitivesContainer) xmlReader.getConfigValue("container");
    }

    @Test
    public void testBooleanValue() {
        Assert.assertTrue(container.getBooleanValue());
    }

    @Test
    public void testIntValue() {
        Assert.assertEquals(15, container.getIntValue());
    }

    @Test
    public void testLongValue() {
        Assert.assertEquals(42, container.getLongValue());
    }

    @Test
    public void testFloatValue() {
        Assert.assertEquals(12.34f, container.getFloatValue(), 0.0001);
    }

    @Test
    public void testDoubleValue() {
        Assert.assertEquals(12.34d, container.getDoubleValue(), 0.0001);
    }

    @Test
    public void testShortValue() {
        Assert.assertEquals(-34, container.getShortValue());
    }

}
