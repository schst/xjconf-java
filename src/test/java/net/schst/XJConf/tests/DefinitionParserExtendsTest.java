package net.schst.XJConf.tests;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.TagDefinition;
import net.schst.XJConf.exceptions.XJConfException;

import org.junit.Assert;
import org.junit.Test;

public class DefinitionParserExtendsTest {

    @Test
    public void testSimpleExtends() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs =
                defParser.parse("src/test/resources/tests/defines/DefinitionParserExtendsTestCase.xml");

        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "MySecondInt");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("MySecondInt", tagDef.getName());
        Assert.assertEquals("setMySecondInt", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.Integer", tagDef.getType());
        Assert.assertEquals("MySecondInt", tagDef.getTagName());
    }

    @Test
    public void testSimpleExtendsWithSetter() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        NamespaceDefinitions defs =
                defParser.parse("src/test/resources/tests/defines/DefinitionParserExtendsTestCase.xml");

        TagDefinition tagDef = defs.getTagDefinition(DefinitionParser.DEFAULT_NAMESPACE, "ExtendedTagWithSetter");
        Assert.assertNotNull(tagDef);
        Assert.assertEquals("ExtendedTagWithSetter", tagDef.getName());
        Assert.assertEquals("addObject", tagDef.getSetterMethod());
        Assert.assertEquals("java.lang.Integer", tagDef.getType());
        Assert.assertEquals("ExtendedTagWithSetter", tagDef.getTagName());
    }

}
