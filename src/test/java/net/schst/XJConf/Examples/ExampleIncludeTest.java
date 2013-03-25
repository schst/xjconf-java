package net.schst.XJConf.Examples;

import net.schst.XJConf.exceptions.XJConfException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
@RunWith(Theories.class)
public final class ExampleIncludeTest extends XIncludeFixture {

    @DataPoints
    public static final String[] FILE_NAMES =
        {"src/test/resources/xml/test-xinclude.xml",
         "src/test/resources/xml/test-xinclude-subdir.xml"};

    @Before
    public void setUp() throws XJConfException {
        super.setUp("./src/test/resources/xml/defines-hashmap.xml");
    }

    @Theory
    public void testInclusion(String filename) {
        try {
            xmlReader.parse(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        Assert.assertNotNull("map", xmlReader.getConfigValue("map"));
        Assert.assertNotNull("properties", xmlReader.getConfigValue("properties"));
    }

    public static void main(String[] args) throws XJConfException {
        for (String fileName : FILE_NAMES) {
            ExampleIncludeTest include = new ExampleIncludeTest();
            include.setUp();
            include.testInclusion(fileName);
            System.out.println("map: " + include.xmlReader.getConfigValue("map"));
            System.out.println("properties: " + include.xmlReader.getConfigValue("properties"));
        }
    }
}
