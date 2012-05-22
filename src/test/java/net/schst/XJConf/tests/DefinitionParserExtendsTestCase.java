package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.XJConfException;
import junit.framework.TestCase;

public class DefinitionParserExtendsTestCase extends TestCase {

    public void testSimpleExtends() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs = defParser.parse("src/test/resources/tests/defines/DefinitionParserExtendsTestCase.xml");
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "MySecondInt");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("MySecondInt", tagDef.getName());
        TestCase.assertEquals("setMySecondInt", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.Integer", tagDef.getType());
        TestCase.assertEquals("MySecondInt", tagDef.getTagName());
    }

    public void testSimpleExtendsWithSetter() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs = defParser.parse("src/test/resources/tests/defines/DefinitionParserExtendsTestCase.xml");
        
        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "ExtendedTagWithSetter");
        TestCase.assertNotNull(tagDef);
        TestCase.assertEquals("ExtendedTagWithSetter", tagDef.getName());
        TestCase.assertEquals("addObject", tagDef.getSetterMethod());
        TestCase.assertEquals("java.lang.Integer", tagDef.getType());
        TestCase.assertEquals("ExtendedTagWithSetter", tagDef.getTagName());
    }
}