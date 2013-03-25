package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.tests.helpers.IPrimitivesContainer;
import net.schst.XJConf.tests.helpers.PrimitivesContainer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XmlReaderGetTest {

    private DefinitionParser parser = new DefinitionParser();
    private XmlReader xmlReader = new XmlReader();

    @Before
    public void setUp() throws Exception {
        NamespaceDefinitions defs =
                parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        xmlReader.addTagDefinitions(defs);

        xmlReader.parse("src/test/resources/tests/xml/PrimitiveAttributesTestCase.xml");
    }

    @Test
    public void testWithClass() throws Exception {
        PrimitivesContainer container = xmlReader.get("container", PrimitivesContainer.class);
        Assert.assertNotNull(container);
    }

    @Test
    public void testWithInterface() throws Exception {
        IPrimitivesContainer container = xmlReader.get("container", IPrimitivesContainer.class);
        Assert.assertNotNull(container);
        Assert.assertTrue(container instanceof PrimitivesContainer);
    }

    @Test
    public void testWithSuperclass() throws Exception {
        Object container = xmlReader.get("container", Object.class);
        Assert.assertNotNull(container);
        Assert.assertTrue(container instanceof PrimitivesContainer);
        Assert.assertTrue(container instanceof IPrimitivesContainer);
    }

}
