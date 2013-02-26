package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.InvalidNamespaceDefinitionException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testcase for namespace functionality.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class DefinitionParserNamespacesTest {

    /*
     * Test, whether an invalid namespace triggers an exception
     */
    @Test
    public void testInvalidNamespace() {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML = "<defines><namespace></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        try {
            defParser.parse(stream);
        } catch (InvalidNamespaceDefinitionException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.fail("Expected InvalidTagDefinitionException.");
    }

    /*
     * Test defining an namespace
     */
    @Test
    public void testNamespace() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML =
                "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"int\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        Assert.assertTrue(defs.isNamespaceDefined("http://www.schst.net/"));
        Assert.assertFalse(defs.isNamespaceDefined("http://www.foo.net/"));
    }

    /*
     * Test defining an namespace
     */
    @Test
    public void testNamespaceTag() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String xml =
                "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"int\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        Assert.assertTrue(defs.isTagDefined("http://www.schst.net/", "int"));
        Assert.assertFalse(defs.isTagDefined("http://www.schst.net/", "foo"));
        Assert.assertFalse(defs.isTagDefined("http://www.foo.net/", "foo"));

        Assert.assertEquals(1, defs.countTagDefinitions());

        TagDefinition tagDef = defs.getTagDefinition("http://www.schst.net/", "int");
        Assert.assertNotNull(tagDef);

        tagDef = defs.getTagDefinition("http://www.schst.net/", "foo");
        Assert.assertNull(tagDef);

        tagDef = defs.getTagDefinition("http://www.foo.net/", "int");
        Assert.assertNull(tagDef);
    }

    /*
     * Test defining two namespaces
     */
    @Test
    public void testNamespaces() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String xml =
                "<defines>"
                  + "<namespace uri=\"http://www.schst.net/\">"
                      + "<tag name=\"test\" type=\"int\"/>"
                  + "</namespace>"
                  + "<namespace uri=\"http://www.foo.net/\">"
                      + "<tag name=\"test\" type=\"java.lang.String\"/>"
                  + "</namespace>"
               + "</defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        Assert.assertTrue(defs.isNamespaceDefined("http://www.schst.net/"));
        Assert.assertTrue(defs.isNamespaceDefined("http://www.foo.net/"));

        Assert.assertTrue(defs.isTagDefined("http://www.schst.net/", "test"));
        Assert.assertTrue(defs.isTagDefined("http://www.foo.net/", "test"));
    }

}
