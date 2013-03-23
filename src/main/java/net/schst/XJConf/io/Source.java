package net.schst.XJConf.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract definition of a source used for reading definitions or for parsing
 * xml documents.
 *
 * @author Florian Fray
 */
public interface Source {

    /**
     * Returns the name of this source.
     *
     * @return never <code>null</code>.
     */
    String getName();

    /**
     * A source can be used to resolve relative locations.
     * Standard relative names like '.', '..' should be supported by implementations.
     *
     * @param relName
     * @return Source object
     * @throws IOException
     */
    Source createRelative(String relName) throws IOException;

    /**
     * Opens an InputStream to the specified resource.
     * Note that using {@link #exists()} is recommended to test whether a source really exists.
     * The caller of this method should also take care to close the stream properly!
     *
     * @return InputStream
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;

    /**
     * Returns a hint, whether a Source really exists or not.
     */
    boolean exists();

    /**
     * Though existent, some sources may actually not be readable.
     * This method tests whether reading a source is possible or not.
     */
    boolean isReadable();

}
