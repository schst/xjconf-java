package net.schst.XJConf.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.exceptions.XJConfException;
import net.schst.XJConf.io.ResourceSource;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testcase to test the parsing from different sources.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class DefinitionParserParseTest {

    /*
     * Test method for 'net.schst.XJConf.DefinitionParser.parse(String)'
     */
    @Test
    public void testParseString() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        defParser.parse("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml");
    }

    @Test
    public void testParseSource() throws XJConfException, URISyntaxException {
        testParseSource("tests/defines/DefinitionParserParseTestCase.xml");
        testParseSource("/tests/defines/DefinitionParserParseTestCase.xml");
    }

    public void testParseSource(String location) throws XJConfException, URISyntaxException {
        URI uri = new URI(location);
        String path = uri.getPath();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        Assert.assertNotNull(getClass().getClassLoader().getResource(path));
        DefinitionParser defParser = new DefinitionParser();
        defParser.parse(new ResourceSource(uri, getClass().getClassLoader()));
    }

    /*
     * Test method for 'net.schst.XJConf.DefinitionParser.parse(File)'
     */
    @Test
    public void testParseFile() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        defParser.parse(new File("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml"));
    }

    /*
     * Test method for 'net.schst.XJConf.DefinitionParser.parse(InputStream)'
     */
    @Test
    public void testParseInputStream() throws Exception {
        DefinitionParser defParser = new DefinitionParser();

        InputStream stream = new FileInputStream("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml");

        defParser.parse("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml", stream);
    }

    /*
     * Test method for parse with an invalid file name
     */
    @Test
    public void testParseExceptionString() {
        DefinitionParser defParser = new DefinitionParser();
        try {
            defParser.parse("invalid-file.xml");
        } catch (XJConfException e) {
            return;
        }
        Assert.fail("Expected XJConf exception because of invalid file.");
    }

}
