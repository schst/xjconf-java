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
        NamespaceDefinitions defs = this.parser
                .parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);

        this.xmlReader.parse("src/test/resources/tests/xml/PrimitiveAttributesTestCase.xml");
        this.container = (PrimitivesContainer) this.xmlReader.getConfigValue("container");
    }

    @Test
    public void testBooleanValue() {
        Assert.assertTrue(this.container.getBooleanValue());
    }

    @Test
    public void testIntValue() {
        Assert.assertEquals(15, this.container.getIntValue());
    }

    @Test
    public void testLongValue() {
        Assert.assertEquals(42, this.container.getLongValue());
    }

    @Test
    public void testFloatValue() {
        Assert.assertEquals(12.34f, this.container.getFloatValue(), 0.0001);
    }

    @Test
    public void testDoubleValue() {
        Assert.assertEquals(12.34d, this.container.getDoubleValue(), 0.0001);
    }

    @Test
    public void testShortValue() {
        Assert.assertEquals(-34, this.container.getShortValue());
    }

}
