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
<<<<<<< HEAD
        NamespaceDefinitions defs =
                parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        xmlReader.addTagDefinitions(defs);
=======
        NamespaceDefinitions defs = this.parser
                .parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);
>>>>>>> 430a9d8b1843787a6d1e906f4f5d36aec68d680b

        xmlReader.parse("src/test/resources/tests/xml/PrimitiveAttributesTestCase.xml");
        container = (PrimitivesContainer) xmlReader.getConfigValue("container");
    }

    @Test
<<<<<<< HEAD
    public void testBooleanValue() throws Exception {
        Assert.assertTrue(container.getBooleanValue());
    }

    @Test
    public void testIntValue() throws Exception {
        Assert.assertEquals(15, container.getIntValue());
    }

    @Test
    public void testLongValue() throws Exception {
        Assert.assertEquals(42, container.getLongValue());
    }

    @Test
    public void testFloatValue() throws Exception {
        Assert.assertEquals(12.34f, container.getFloatValue(), 0.0001);
    }

    @Test
    public void testDoubleValue() throws Exception {
        Assert.assertEquals(12.34d, container.getDoubleValue(), 0.0001);
    }

    @Test
    public void testShortValue() throws Exception {
        Assert.assertEquals(-34, container.getShortValue());
=======
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
>>>>>>> 430a9d8b1843787a6d1e906f4f5d36aec68d680b
    }

}
