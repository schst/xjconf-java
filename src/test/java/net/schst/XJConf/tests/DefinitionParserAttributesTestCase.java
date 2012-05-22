package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.InvalidTagDefinitionException;
import net.schst.XJConf.exceptions.XJConfException;
import junit.framework.TestCase;

/**
 * TestCase for the various attributes supported by the DefinitionParser 
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class DefinitionParserAttributesTestCase extends TestCase {

	/**
	 * Test a tag definition without a name
	 * 
	 * @throws Exception
	 */
    public void testMissingName() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML = "<defines><tag type=\"int\"/></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        try {
        	defParser.parse(stream);
        } catch (InvalidTagDefinitionException e) {
        	return;
        }
        TestCase.fail("Expected an exception.");
    }
	
	/**
	 * Test a tag definition without a name
	 * 
	 * @throws Exception
	 */
    public void testNameNone() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" key=\"__none\" type=\"int\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        
        NamespaceDefinitions defs = defParser.parse(stream);
       
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("__none",tagDef.getName());
        TestCase.assertNull(tagDef.getSetterMethod());
        TestCase.assertEquals("int", tagDef.getType());
        TestCase.assertEquals("test", tagDef.getTagName());
    }
	
    /**
     * Test the 'key' attribute
     * @throws XJConfException
     */
    public void testKey() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" key=\"foo\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        
        NamespaceDefinitions defs = defParser.parse(stream);
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("foo", tagDef.getName());
        TestCase.assertEquals("setFoo", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.String", tagDef.getType());
        TestCase.assertEquals("test", tagDef.getTagName());
    }

    /**
     * Test the 'setter' attribute
     * @throws XJConfException
     */
    public void testSetter() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" setter=\"setFooBar\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        
        NamespaceDefinitions defs = defParser.parse(stream);
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("test", tagDef.getName());
        TestCase.assertEquals("setFooBar", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.String", tagDef.getType());
        TestCase.assertEquals("test", tagDef.getTagName());
    }

    /**
     * Test the 'keyAttribute' attribute
     * @throws XJConfException
     */
    public void testKeyAttribute() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" keyAttribute=\"name\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        
        NamespaceDefinitions defs = defParser.parse(stream);
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("__attribute", tagDef.getName());
        TestCase.assertEquals("set__attribute", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.String", tagDef.getType());
        TestCase.assertEquals("test", tagDef.getTagName());
    }
}