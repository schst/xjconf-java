package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.InvalidNamespaceDefinitionException;

/**
 * Testcase for namespace functionality
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class DefinitionParserNamespacesTestCase extends TestCase {

	/**
	 * Test, whether an invalid namespace triggers an exception
	 */
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
        TestCase.fail("Expected InvalidTagDefinitionException.");
    }

	/**
	 * Test defining an namespace
	 */
    public void testNamespace() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML = "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"int\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        TestCase.assertTrue(defs.isNamespaceDefined("http://www.schst.net/"));
        TestCase.assertFalse(defs.isNamespaceDefined("http://www.foo.net/"));
    }

	/**
	 * Test defining an namespace
	 */
    public void testNamespaceTag() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"int\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        TestCase.assertTrue(defs.isTagDefined("http://www.schst.net/", "int"));
        TestCase.assertFalse(defs.isTagDefined("http://www.schst.net/", "foo"));
        TestCase.assertFalse(defs.isTagDefined("http://www.foo.net/", "foo"));
        
        TestCase.assertEquals(1, defs.countTagDefinitions());
        
        TagDefinition tagDef = defs.getTagDefinition("http://www.schst.net/", "int");
        TestCase.assertNotNull(tagDef);

        tagDef = defs.getTagDefinition("http://www.schst.net/", "foo");
        TestCase.assertNull(tagDef);
    
        tagDef = defs.getTagDefinition("http://www.foo.net/", "int");
        TestCase.assertNull(tagDef);
    }

	/**
	 * Test defining two namespaces
	 */
    public void testNamespaces() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"test\" type=\"int\"/></namespace><namespace uri=\"http://www.foo.net/\"><tag name=\"test\" type=\"java.lang.String\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
        TestCase.assertTrue(defs.isNamespaceDefined("http://www.schst.net/"));
        TestCase.assertTrue(defs.isNamespaceDefined("http://www.foo.net/"));

        TestCase.assertTrue(defs.isTagDefined("http://www.schst.net/", "test"));
        TestCase.assertTrue(defs.isTagDefined("http://www.foo.net/", "test"));
    }
}