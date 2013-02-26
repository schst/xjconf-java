package net.schst.XJConf.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This Source can be used for direct {@link File} access.
 *
 * @author Florian Fray
 *
 */
public class FileSource implements Source {

    private final File file;

    /**
     * Convenience for <code>new FileSource(new File(filename))</code>.
     */
    public FileSource(String filename) {
        this(new File(filename));
    }

    public FileSource(File file) {
        if (file == null) {
            throw new IllegalArgumentException("Argument 'file' must not be null!");
        }
        this.file = file;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    public Source createRelative(String relName) {
        File newFile;
        if (file.isDirectory()) {
            newFile = new File(file, relName);
        } else {
            newFile = new File(file.getParent(), relName);
        }
        return new FileSource(newFile);
    }

    public String getName() {
        return file.getAbsolutePath();
    }

    public boolean exists() {
        return file.exists();
    }

    public boolean isReadable() {
        return file.canRead();
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return super.toString() + "[name: '" + getName() + "']";
    }
}
