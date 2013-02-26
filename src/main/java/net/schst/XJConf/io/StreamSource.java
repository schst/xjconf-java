package net.schst.XJConf.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Generic wrapper for InputStreams.
 * Note that this class is intended for one-time usage.
 * See the comments at {@link #getInputStream()}.
 * Be aware that this class is not able to resolve a relative location, see {@link #createRelative(String)}.
 *
 * @author Florian Fray
 *
 */
public class StreamSource implements Source {

    public static final String GENERIC = "generic";

    private final InputStream stream;
    private final String name;

    /**
     * Creates an instance named &quot;generic&quot;.
     * @param stream
     */
    public StreamSource(InputStream stream) {
        this(GENERIC, stream);
    }

    /**
     * Creates a named instance.
     * The stream must not be <code>null</code>!
     * If name is <code>null</code>, &quot;null&quot; will be used as name.
     */
    public StreamSource(String name, InputStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException("Argument 'stream' must not be null!");
        }
        this.name = String.valueOf(name);
        this.stream = stream;
    }

    /**
     * This class is not able to resolve relative names,
     * so this method returns <code>null</code>.
     */
    public Source createRelative(String relName) {
        return null;
    }

    /**
     * Always returns <code>true</code>.
     */
    public boolean exists() {
        return true;
    }

    /**
     * Note that this method returns the same {@link InputStream}
     * each and every time called!
     */
    public InputStream getInputStream() throws IOException {
        return stream;
    }

    public String getName() {
        return name;
    }

    /**
     * Always returns <code>true</code>.
     */
    public boolean isReadable() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "[name: '" + name + "']";
    }

}
