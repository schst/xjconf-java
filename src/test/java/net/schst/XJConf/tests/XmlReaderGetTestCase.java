package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.tests.helpers.IPrimitivesContainer;
import net.schst.XJConf.tests.helpers.PrimitivesContainer;
import junit.framework.TestCase;

public class XmlReaderGetTestCase extends TestCase {

    private DefinitionParser parser = new DefinitionParser();;
    private XmlReader xmlReader = new XmlReader();
    
    protected void setUp() throws Exception {
        super.setUp();
        NamespaceDefinitions defs = this.parser.parse("src/test/resources/tests/defines/PrimitiveAttributesTestCase.xml");
        this.xmlReader.addTagDefinitions(defs);
        
        this.xmlReader.parse("src/test/resources/tests/xml/PrimitiveAttributesTestCase.xml");
    }
    
    public void testWithClass() throws Exception {
        PrimitivesContainer container = this.xmlReader.get("container", PrimitivesContainer.class);
        TestCase.assertNotNull(container);
        TestCase.assertTrue(container instanceof PrimitivesContainer);
    }

    public void testWithInterface() throws Exception {
        IPrimitivesContainer container = this.xmlReader.get("container", IPrimitivesContainer.class);
        TestCase.assertNotNull(container);
        TestCase.assertTrue(container instanceof PrimitivesContainer);
        TestCase.assertTrue(container instanceof IPrimitivesContainer);
    }

    public void testWithSuperclass() throws Exception {
        Object container = this.xmlReader.get("container", Object.class);
        TestCase.assertNotNull(container);
        TestCase.assertTrue(container instanceof PrimitivesContainer);
        TestCase.assertTrue(container instanceof IPrimitivesContainer);
        TestCase.assertTrue(container instanceof Object);
    }
}