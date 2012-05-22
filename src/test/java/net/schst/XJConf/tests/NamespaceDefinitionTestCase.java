package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinition;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import junit.framework.TestCase;

/**
 * NamespaceDefinitionTestCase
 *
 * Tests the various methods of the NamespaceDefinition class
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class NamespaceDefinitionTestCase extends TestCase {

	NamespaceDefinition nsDef;
	
	public void setUp() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML = "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"int\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        NamespaceDefinitions defs = defParser.parse(stream);
		this.nsDef = defs.getNamespaceDefinition("http://www.schst.net/");
	}
	
	/*
	 * Test method for 'net.schst.XJConf.NamespaceDefinition.countTagDefinitions()'
	 */
	public void testCountTagDefinitions() {
		TestCase.assertEquals(1, this.nsDef.countTagDefinitions());
	}

	/*
	 * Test method for 'net.schst.XJConf.NamespaceDefinition.isDefined(String)'
	 */
	public void testIsDefined() {
		TestCase.assertTrue(this.nsDef.isDefined("int"));
		TestCase.assertFalse(this.nsDef.isDefined("foo"));
	}

	/*
	 * Test method for 'net.schst.XJConf.NamespaceDefinition.getDefinition(String)'
	 */
	public void testGetDefinition() {
		TagDefinition tagDef = this.nsDef.getDefinition("int");
		TestCase.assertNotNull(tagDef);
	}

	/*
	 * Test method for 'net.schst.XJConf.NamespaceDefinition.getNamespaceURI()'
	 */
	public void testGetNamespaceURI() {
		TestCase.assertEquals("http://www.schst.net/", this.nsDef.getNamespaceURI());
	}
}