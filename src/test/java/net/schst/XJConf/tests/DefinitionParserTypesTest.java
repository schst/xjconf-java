package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.XJConfException;

import org.junit.Assert;
import org.junit.Test;

public class DefinitionParserTypesTest {

    @Test
    public void testString() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs =
                defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "string");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("string", tagDef.getName());
        Assert.assertEquals("setString", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.String", tagDef.getType());
        Assert.assertEquals("string", tagDef.getTagName());
    }

    @Test
    public void testBoolean() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs =
                defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "boolean");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("boolean", tagDef.getName());
        Assert.assertEquals("setBoolean", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.Boolean", tagDef.getType());
        Assert.assertEquals("boolean", tagDef.getTagName());
    }

    @Test
    public void testUndefined() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs =
                defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "undefined");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("undefined", tagDef.getName());
        Assert.assertEquals("setUndefined", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.String", tagDef.getType());
        Assert.assertEquals("undefined", tagDef.getTagName());
    }

    @Test
    public void testInt() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs =
                defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "int");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("int", tagDef.getName());
        Assert.assertEquals("setInt", tagDef.getSetterMethod());
        Assert.assertEquals("int", tagDef.getType());
        Assert.assertEquals("int", tagDef.getTagName());
    }

}
