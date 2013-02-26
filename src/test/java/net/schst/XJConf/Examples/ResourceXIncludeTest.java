package net.schst.XJConf.Examples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import net.schst.XJConf.exceptions.XJConfException;
import net.schst.XJConf.io.ResourceSource;

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
public final class ResourceXIncludeTest extends XIncludeFixture {

    @DataPoints
    public static URI[] getResources() throws URISyntaxException {
        return new URI[] {new URI("xml/test-xinclude.xml"),
                new URI("xml/test-xinclude-subdir.xml"),
                new URI("xml/test-xinclude-external.xml")};
    }

    @Before
    public void setUp() throws XJConfException {
        super.setUp("./src/test/resources/xml/defines-hashmap.xml");
    }

    @Theory
    public void testInclusion(URI resource) throws XJConfException, IOException {
        xmlReader.parse(new ResourceSource(resource, getClass().getClassLoader()));
        Map<?, ?> map = (Map<?, ?>) xmlReader.getConfigValue("map");
        Assert.assertNotNull("map", map);
        Properties properties = (Properties) xmlReader.getConfigValue("properties");
        Assert.assertNotNull("properties", properties);
    }

}
