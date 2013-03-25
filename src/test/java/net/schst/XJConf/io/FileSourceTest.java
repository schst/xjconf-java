package net.schst.XJConf.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileSourceTest {

    private static File tempFile;
    private static File relTempFile;
    private static File tmpDir;
    private static File tmpInSubdir;

    @BeforeClass
    public static void setUp() throws IOException {
        tempFile = File.createTempFile("filesourcetest", ".txt");
        relTempFile = File.createTempFile("filesourcetest", ".txt");
        tmpDir = new File(tempFile.getParentFile(), "somedir");
        tmpDir.mkdir();
        tmpInSubdir = File.createTempFile("filesourcetest", ".txt", tmpDir);
    }

    @AfterClass
    public static void tearDown() {
        tmpInSubdir.delete();
        tmpDir.delete();
        relTempFile.delete();
        tempFile.delete();
    }

    @Test
    public void testTempFile() throws IOException {
        testSource(new FileSource(tempFile));
    }

    @Test
    public void testRelTempFile() throws IOException {
        testSource(new FileSource(relTempFile));
    }

    @Test
    public void testReliveSameDir() throws IOException {
        FileSource src = new FileSource(tempFile);
        testSource(src.createRelative("./" + relTempFile.getName()));
    }

    @Test
    public void testRelativeSubDir() throws IOException {
        FileSource src = new FileSource(tempFile);
        testSource(src.createRelative(tmpDir.getName() + "/" + tmpInSubdir.getName()));
    }

    private void testSource(Source src) throws IOException {
        Assert.assertTrue(src.getName() + ".exists", src.exists());
        Assert.assertTrue(src.getName() + ".readable", src.isReadable());
        InputStream in = src.getInputStream();
        try {
            Assert.assertNotNull(src.getName() + ".inputStream", in);
        } finally {
            in.close();
        }
    }

}
