package net.schst.XJConf.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.InvalidTagDefinitionException;
import net.schst.XJConf.exceptions.XJConfException;

import org.junit.Assert;
import org.junit.Test;

/**
 * TestCase for the various attributes supported by the DefinitionParser.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class DefinitionParserAttributesTest {

    /*
     * Test a tag definition without a name
     */
    @Test
    public void testMissingName() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String invalidXML = "<defines><tag type=\"int\"/></defines>";
        InputStream stream = new ByteArrayInputStream(invalidXML.getBytes());
        try {
            defParser.parse(stream);
        } catch (InvalidTagDefinitionException e) {
            return;
        }
        Assert.fail("Expected an exception.");
    }

    /*
     * Test a tag definition without a name
     */
    @Test
    public void testNameNone() throws Exception {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" key=\"__none\" type=\"int\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        NamespaceDefinitions defs = defParser.parse(stream);

        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("__none", tagDef.getName());
        Assert.assertNull(tagDef.getSetterMethod());
        Assert.assertEquals("int", tagDef.getType());
        Assert.assertEquals("test", tagDef.getTagName());
    }

    /*
     * Test the 'key' attribute
     */
    @Test
    public void testKey() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" key=\"foo\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        NamespaceDefinitions defs = defParser.parse(stream);

        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("foo", tagDef.getName());
        Assert.assertEquals("setFoo", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.String", tagDef.getType());
        Assert.assertEquals("test", tagDef.getTagName());
    }

    /*
     * Test the 'setter' attribute
     */
    @Test
    public void testSetter() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" setter=\"setFooBar\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        NamespaceDefinitions defs = defParser.parse(stream);

        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("test", tagDef.getName());
        Assert.assertEquals("setFooBar", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.String", tagDef.getType());
        Assert.assertEquals("test", tagDef.getTagName());
    }

    /*
     * Test the 'keyAttribute' attribute
     */
    @Test
    public void testKeyAttribute() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        String xml = "<defines><tag name=\"test\" keyAttribute=\"name\"/></defines>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        NamespaceDefinitions defs = defParser.parse(stream);

        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "test");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("__attribute", tagDef.getName());
        Assert.assertEquals("set__attribute", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.String", tagDef.getType());
        Assert.assertEquals("test", tagDef.getTagName());
    }

}
