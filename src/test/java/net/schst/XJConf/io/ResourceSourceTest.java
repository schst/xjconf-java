package net.schst.XJConf.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class ResourceSourceTest {

    private ClassLoader loader = getClass().getClassLoader();

    @Test
    public void testSimpleName() throws IOException, URISyntaxException {
        testResource("resource/base.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimpleNameWithClasspath() throws IOException, URISyntaxException {
        testResource("classpath:resource/base.txt");
    }

    @Test
    public void testAbsName() throws IOException, URISyntaxException {
        testResource("/resource/base.txt");
    }

    @Test
    public void testClasspathPrefix() throws IOException, URISyntaxException {
        testResource("classpath:/resource/base.txt");
    }

    @Test
    public void testSubPackage() throws IOException, URISyntaxException {
        ResourceSource base = new ResourceSource("/resource/base.txt", loader);
        testSource(base.createRelative("sub/sub.txt"));
    }

    @Test
    public void testSubPackage2() throws IOException, URISyntaxException {
        ResourceSource base = new ResourceSource("/resource/base.txt", loader);
        testSource(base.createRelative("./sub/sub.txt"));
    }

    @Test
    public void testParentPackage() throws IOException, URISyntaxException {
        ResourceSource base = new ResourceSource("/resource/sub/sub.txt", loader);
        testSource(base.createRelative("../base.txt"));
    }

    @Test
    public void testParentPackageOnClasspath() throws IOException, URISyntaxException {
        ResourceSource base = new ResourceSource("classpath:/resource/sub/sub.txt", loader);
        testSource(base.createRelative("../base.txt"));
    }

    @Test
    public void testMetaINF() throws IOException, URISyntaxException {
        testResource("classpath:/META-INF/bar.txt");
    }

    @Test
    public void testExternal() throws IOException, URISyntaxException {
        testResource("classpath:/org/junit/Test.class");
    }

    @Test(expected = IOException.class)
    public void testMissingResource() throws URISyntaxException, IOException {
        ResourceSource src = new ResourceSource("classpath:/META-INF/doeshopefullynotexist.txt", loader);
        Assert.assertFalse("'" + src.getName() + "'" + ".exists", src.exists());
        src.getInputStream();
    }

    private void testResource(String name) throws IOException, URISyntaxException {
        testSource(new ResourceSource(name, loader));
    }

    private void testSource(Source src) throws IOException {
        Assert.assertTrue("'" + src.getName() + "'" + ".exists", src.exists());
        Assert.assertTrue(src.getName() + ".readable", src.isReadable());
        InputStream in = src.getInputStream();
        try {
            Assert.assertNotNull(src.getName() + ".inputStream", in);
        } finally {
            in.close();
        }
    }

}
