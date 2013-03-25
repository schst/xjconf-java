package net.schst.XJConf.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This {@link Source} wraps up a {@link URL} to provide an InputStream.
 *
 * @author Florian Fray
 *
 */
public class URLSource implements Source {

    private final URL location;

    private StreamHandle handle;

    public URLSource(final URL location) {
        if (location == null) {
            throw new IllegalArgumentException("Argument 'location' must not be null!");
        }
        this.location = location;
    }

    public Source createRelative(String relName) throws IOException {
        try {
            return new URLSource(location.toURI().resolve(relName).toURL());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public boolean exists() {
        if (handle != null && handle.hasStream()) {
            return true;
        }
        try {
            handle = openLocation();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Returns a new InputStream for each caller.
     */
    public InputStream getInputStream() throws IOException {
        if (handle != null && handle.hasStream() && !handle.isUsed()) {
            handle.setUsed(true);
            return handle;
        }
        handle = openLocation();
        handle.setUsed(true);
        return handle;
    }

    public String getName() {
        return location.toString();
    }

    public boolean isReadable() {
        return exists();
    }

    private StreamHandle openLocation() throws IOException {
        return new StreamHandle(location.openStream());
    }

    /**
     * Holds an InputStream and serves a usage-marker.
     * This classed is used to fetch and hold an InputStream and enabling
     * {@link URLSource#getInputStream()} to be used repeatedly.
     *
     * @author Florian Fray
     *
     */
    private static class StreamHandle extends InputStream {

        private InputStream stream;
        private boolean used;

        StreamHandle(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return stream.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return stream.read(b, off, len);
        }

        @Override
        public synchronized void reset() throws IOException {
            stream.reset();
        }

        @Override
        public void close() throws IOException {
            stream.close();
            stream = null;
        }

        public boolean hasStream() {
            return stream != null;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }
    }

}
