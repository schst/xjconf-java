package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.XJConfException;
import junit.framework.TestCase;

public class DefinitionParserTypesTestCase extends TestCase {

    public void testString() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs = defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "string");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("string", tagDef.getName());
        TestCase.assertEquals("setString", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.String", tagDef.getType());
        TestCase.assertEquals("string", tagDef.getTagName());
    }
    
    public void testBoolean() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs = defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "boolean");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("boolean", tagDef.getName());
        TestCase.assertEquals("setBoolean", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.Boolean", tagDef.getType());
        TestCase.assertEquals("boolean", tagDef.getTagName());
    }

    public void testUndefined() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs = defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "undefined");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("undefined", tagDef.getName());
        TestCase.assertEquals("setUndefined", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.String", tagDef.getType());
        TestCase.assertEquals("undefined", tagDef.getTagName());
    }

    public void testInt() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs = defParser.parse("src/test/resources/tests/defines/DefinitionParserTypesTestCase.xml");
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "int");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("int", tagDef.getName());
        TestCase.assertEquals("setInt", tagDef.getSetterMethod());
        TestCase.assertEquals("int", tagDef.getType());
        TestCase.assertEquals("int", tagDef.getTagName());
    }
}