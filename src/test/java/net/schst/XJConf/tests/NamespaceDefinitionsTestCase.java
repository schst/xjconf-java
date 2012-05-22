package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import junit.framework.TestCase;

public class NamespaceDefinitionsTestCase extends TestCase {

	/*
	 * Test method for 'net.schst.XJConf.NamespaceDefinitions.appendNamespaceDefinitions(NamespaceDefinitions)'
	 */
	public void testAppendNamespaceDefinitions() throws Exception {

        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"test\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs1 = defParser.parse(stream);
		
        xml = "<defines><namespace uri=\"http://www.foo.net/\"><tag name=\"test\" type=\"int\"/></namespace></defines>";
        stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs2 = defParser.parse(stream);
        
        defs1.appendNamespaceDefinitions(defs2);
        
        TestCase.assertEquals(2, defs1.countTagDefinitions());
        TestCase.assertTrue(defs1.isNamespaceDefined("http://www.schst.net/"));
        TestCase.assertTrue(defs1.isNamespaceDefined("http://www.foo.net/"));
	}
}