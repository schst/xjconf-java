package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;

import org.junit.Assert;
import org.junit.Test;

public class NamespaceDefinitionsTest {

    /*
     * Test method for 'net.schst.XJConf.NamespaceDefinitions.appendNamespaceDefinitions(NamespaceDefinitions)'
     */
    @Test
    public void testAppendNamespaceDefinitions() throws Exception {

        DefinitionParser defParser = new DefinitionParser();
        String xml =
                "<defines><namespace uri=\"http://www.schst.net/\"><tag name=\"test\" type=\"int\"/></namespace></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs1 = defParser.parse(stream);

        xml = "<defines><namespace uri=\"http://www.foo.net/\"><tag name=\"test\" type=\"int\"/></namespace></defines>";
        stream = new ByteArrayInputStream(xml.getBytes());
        NamespaceDefinitions defs2 = defParser.parse(stream);

        defs1.appendNamespaceDefinitions(defs2);

        Assert.assertEquals(2, defs1.countTagDefinitions());
        Assert.assertTrue(defs1.isNamespaceDefined("http://www.schst.net/"));
        Assert.assertTrue(defs1.isNamespaceDefined("http://www.foo.net/"));
    }

}
