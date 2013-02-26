package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinition;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the various methods of the NamespaceDefinition class.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class NamespaceDefinitionTest {

    private NamespaceDefinition nsDef;

    @Before
    public void setUp() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML =
                "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"int\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        this.nsDef = defs.getNamespaceDefinition("http://www.schst.net/");
    }

    /*
     * Test method for 'net.schst.XJConf.NamespaceDefinition.countTagDefinitions()'
     */
    @Test
    public void testCountTagDefinitions() {
        Assert.assertEquals(1, this.nsDef.countTagDefinitions());
    }

    /*
     * Test method for 'net.schst.XJConf.NamespaceDefinition.isDefined(String)'
     */
    @Test
    public void testIsDefined() {
        Assert.assertTrue(this.nsDef.isDefined("int"));
        Assert.assertFalse(this.nsDef.isDefined("foo"));
    }

    /*
     * Test method for 'net.schst.XJConf.NamespaceDefinition.getDefinition(String)'
     */
    @Test
    public void testGetDefinition() {
        TagDefinition tagDef = this.nsDef.getDefinition("int");
        Assert.assertNotNull(tagDef);
    }

    /*
     * Test method for 'net.schst.XJConf.NamespaceDefinition.getNamespaceURI()'
     */
    @Test
    public void testGetNamespaceURI() {
        Assert.assertEquals("http://www.schst.net/", this.nsDef.getNamespaceURI());
    }

}
