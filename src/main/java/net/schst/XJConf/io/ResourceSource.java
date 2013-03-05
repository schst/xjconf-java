package net.schst.XJConf.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletContext;

/**
 * This {@link Source} is providing access to Classpath- or ServletContext-Resources.
 *
 * @author Florian Fray
 *
 */
public class ResourceSource implements Source {

    private final URI location;
    private final ResourceLoader loader;

    /**
     * Convenience for <code>new ResourceSource(new URI(location), classLoader)</code>
     * See {@link #ResourceSource(String, ClassLoader)}.
     */
    public ResourceSource(final String location, final ClassLoader classLoader) throws URISyntaxException {
        this(new URI(location), new ClassResourceLoader(classLoader));
    }

    public ResourceSource(final String location, final ServletContext servletContext) throws URISyntaxException {
        this(new URI(location), servletContext);
    }

    public ResourceSource(final URI location, final ClassLoader classLoader) {
        this(location, new ClassResourceLoader(classLoader));
    }

    public ResourceSource(final URI location, final ServletContext servletContext) {
        this(location, new ServletContextLoader(servletContext));
    }

    /**
     * Creates a new instance using a URI and ClassLoader or a ServletContext.
     *
     * @param location must not be <code>null</code>.
     * @param resourceLoader must not be <code>null</code>.
     * @throws IllegalArgumentException in case a location or classLoader is <code>null</code>,
     *         or the locations&apos; scheme is not supported.
     */
    public ResourceSource(final URI location, final ResourceLoader resourceLoader) {
        if (location == null) {
            throw new IllegalArgumentException("Argument 'location' must not be null!");
        } else if (resourceLoader == null) {
            throw new IllegalArgumentException("Argument 'resourceLoader' must not be null!");
        } else if (location.getScheme() != null
            && !"classpath".equals(location.getScheme())) {
                throw new IllegalArgumentException("Locations' scheme '" + location.getScheme() + "' is not supported.");
        } else if (location.getPath() == null) {
            throw new IllegalArgumentException("Argument 'location' must have a path! Check if you've forgotten a slash after the colon!");
        }

        this.location = location.normalize();
        loader = resourceLoader;
    }

    /**
     * As {@link ClassLoader}s provide URLs this constructor has been introduced.
     * It's a convenience for <code>new ResourceSource(url.toURI(), classLoader)</code>.
     * @throws URISyntaxException
     */
    public ResourceSource(final URL location, final ClassLoader classLoader) throws URISyntaxException {
        this(location.toURI(), classLoader);
    }

    public Source createRelative(String relName) throws IOException {
        URI newLocation = location.resolve(relName);
        return new ResourceSource(newLocation, loader);
    }

    public boolean exists() {
        String path = getLoadablePath();
        return loader.getResource(path) != null;
    }

    public InputStream getInputStream() throws IOException {
        String path = getLoadablePath();
        InputStream result;
        result = loader.getResourceAsStream(path);
        if (result == null) {
            throw new IOException("Resource '" + path + "' could not be resolved.");
        }
        return result;
    }

    public String getName() {
        return location.toString();
    }

    public boolean isReadable() {
        return exists();
    }

    @Override
    public String toString() {
        return super.toString() + "[name: '" + getName() + "']";
    }

    private String getLoadablePath() {
        String path = location.getPath();
        if (path.startsWith("/")) {
            return path.substring(1);
        } else {
            return path;
        }
    }

    /**
     * Implementations of this interface provide access to resources via a path.
     * {@link ClassLoader} and {@link ServletContext} both provide such functionality, but
     * are lacking a common interface.
     *
     * @author Florian Fray
     *
     */
    public interface ResourceLoader {

        /**
         * Provides a URL to access a resource based on a path.
         *
         * @param path
         * @return returns a URL or <code>null</code>.
         *   May return <code>null</code> if the resource does not exist,
         *   in case <code>path</code> is invalid, or if the implementation is
         *   unable to provide access to the resource due to any other limitation.
         */
        URL getResource(String path);

        /**
         * Provides an {@link InputStream} delivering a resources contents.
         * @param path
         * @return may return <code>null</code> in case the resource is unavailable,
         *   or cannot be found.
         */
        InputStream getResourceAsStream(String path);

    }

    /**
     * This class provides access to resources via a {@link ClassLoader}.
     *
     * @author Florian Fray
     *
     */
    public static class ClassResourceLoader implements ResourceLoader {

        private final ClassLoader classLoader;

        public ClassResourceLoader(ClassLoader classLoader) {
            if (classLoader == null) {
                throw new IllegalArgumentException("Argument 'classLoader' must not be null!");
            }
            this.classLoader = classLoader;
        }

        public URL getResource(String path) {
            return classLoader.getResource(path);
        }

        public InputStream getResourceAsStream(String path) {
            return classLoader.getResourceAsStream(path);
        }
    }


    /**
     * This class provides access to resources via a {@link ServletContext}.
     *
     * @author Florian Fray
     *
     */
    public static class ServletContextLoader implements ResourceLoader {

        private final ServletContext servletContext;

        public ServletContextLoader(ServletContext servletContext) {
            if (servletContext == null) {
                throw new IllegalArgumentException("Argument 'servletContext' must not be null!");
            }
            this.servletContext = servletContext;
        }

        public URL getResource(String path) {
            try {
                return servletContext.getResource(path);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        public InputStream getResourceAsStream(String path) {
            return servletContext.getResourceAsStream(path);
        }
    }

}
