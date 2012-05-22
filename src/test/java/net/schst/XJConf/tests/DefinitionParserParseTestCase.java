package net.schst.XJConf.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.schst.XJConf.DefinitionParser;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * DefinitionParserParseTestCase
 * 
 * Testcase to test the parsing from different sources.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class DefinitionParserParseTestCase extends TestCase {

    /*
     * Test method for 'net.schst.XJConf.DefinitionParser.parse(String)'
     */
    public void testParseString() throws XJConfException {
        DefinitionParser defParser = new DefinitionParser();
        defParser.parse("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml");
    }

    /*
     * Test method for 'net.schst.XJConf.DefinitionParser.parse(File)'
     */
    public void testParseFile() throws XJConfException  {
        DefinitionParser defParser = new DefinitionParser();
        defParser.parse(new File("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml"));
    }

    /*
     * Test method for 'net.schst.XJConf.DefinitionParser.parse(InputStream)'
     */
    public void testParseInputStream() throws Exception  {
        DefinitionParser defParser = new DefinitionParser();
        
        InputStream stream = new FileInputStream("src/test/resources/tests/defines/DefinitionParserParseTestCase.xml");
        
        defParser.parse(stream);
    }

    /*
     * Test method for parse with an invalid file name
     */
    public void testParseExceptionString() {
        DefinitionParser defParser = new DefinitionParser();
        try {
        	defParser.parse("invalid-file.xml");
        } catch (XJConfException e){
        	return;
        }
        TestCase.fail("Expected XJConf exception because of invalid file.");
    }

}