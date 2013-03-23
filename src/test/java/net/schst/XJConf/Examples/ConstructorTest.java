package net.schst.XJConf.Examples;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.NamespaceDefinitions;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
@RunWith(Parameterized.class)
public final class ConstructorTest {

    private static XmlReader xmlReader;

    private String id;
    private Integer red;
    private Integer green;
    private Integer blue;

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"color", i(100), i(25), i(10)},
                {"color2", i(100), i(25), i(23)},
                {"color3", i(111), i(222), i(333)},
                {"color-no-atts", null, null, null}
        });
    }

    @BeforeClass
    public static void setUp() throws XJConfException, IOException {
        DefinitionParser tagParser = new DefinitionParser();
        NamespaceDefinitions defs = tagParser.parse("src/test/resources/xml/defines-constructor.xml");

        xmlReader = new XmlReader();
        xmlReader.setTagDefinitions(defs);
        xmlReader.parse("src/test/resources/xml/test-constructor.xml");
    }

    public ConstructorTest(String id, Integer red, Integer green, Integer blue) {
        this.id = id;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Test
    public void testColor() {
        ConstructorColor color = (ConstructorColor) xmlReader.getConfigValue(id);
        Assert.assertEquals("color.red", red, color.getRed());
        Assert.assertEquals("color.green", green, color.getGreen());
        Assert.assertEquals("color.blue", blue, color.getBlue());
    }

    public static void main(String[] args) throws XJConfException, IOException {
        setUp();
        System.out.println(xmlReader.getConfigValue("color"));
        System.out.println(xmlReader.getConfigValue("color2"));
        System.out.println(xmlReader.getConfigValue("color3"));
        System.out.println(xmlReader.getConfigValue("color-no-atts"));
    }

    private static Integer i(final int primitive) {
        return Integer.valueOf(primitive);
    }

}
