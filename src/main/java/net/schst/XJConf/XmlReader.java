package net.schst.XJConf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.schst.XJConf.exceptions.UnknownExtensionException;
import net.schst.XJConf.exceptions.UnknownNamespaceException;
import net.schst.XJConf.exceptions.UnknownTagException;
import net.schst.XJConf.exceptions.ValueNotAvailableException;
import net.schst.XJConf.exceptions.XJConfException;
import net.schst.XJConf.io.FileSource;
import net.schst.XJConf.io.Source;
import net.schst.XJConf.io.StreamSource;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class to read XML configuration files.
 *
 * Prior to parsing an XML document you need to supply the
 * namespace definitions that should be used to convert the
 * tags and attributes to Java objects.
 *
 * You can either construct those definitions by hand (which
 * is cumbersome) or use the DefinitionParser class to read
 * the tag definitions from an XML file.
 *
 * @author Stephan Schmidt
 * @author Frank Kleine
 */
public class XmlReader extends DefaultHandler {

    /**
     * tagStack contains the tag objects.
     */
    private Stack<Tag> tagStack = new Stack<Tag>();

    /**
     * config contains the generated objects.
     */
    private HashMap<String, Object> config = new HashMap<String, Object>();

    /**
     * tagDefs contains the tag definitions.
     */
    private NamespaceDefinitions tagDefs = null;

    /**
     * depth contains the current tag depth while parsing the document.
     */
    private int depth = 0;

    /**
     * HashMap contains the extensions used by the parser.
     */
    private HashMap<String, Extension> extensions = new HashMap<String, Extension>();

    /**
     * myNamespace contains the Internal namespace.
     */
    private String myNamespace = "http://www.schst.net/XJConf";

    /**
     * The object stores all sources that currently are being processed.
     */
    private Stack<Source> openSources = new Stack<Source>();

    /**
     * The object stores the factory for all parsers.
     */
    private SAXParserFactory parserFactory = null;

    /**
     * Classloader that will be used for extensions
     * and dynamically created classes.
     */
    private ClassLoader loader = getClass().getClassLoader();

    public XmlReader() {
        super();
    }

    /**
     * Set the tag definitions.
     *
     * Tag definitions will define, how the tags in your document
     * will be converted to Java objects.
     *
     * You can either create a collection by hand, or use
     * the supplied DefinitionParser class.
     *
     * @param defs    Tag definitions
     * @see   net.schst.XJConf.DefinitionParser
     */
    public void setTagDefinitions(NamespaceDefinitions defs) {
        tagDefs = defs;
    }

    /**
     * Add more namespacedefinitions to the currently
     * added definitions.
     *
     * This is useful, when your namespace definitions
     * are distributed among different files.
     *
     * @param defs  namespace definitions
     */
    public void addTagDefinitions(NamespaceDefinitions defs) {
        if (tagDefs == null) {
            setTagDefinitions(defs);
            return;
        }
        tagDefs.appendNamespaceDefinitions(defs);
    }

    /**
     * Add a new extension.
     *
     * @param namespace
     * @param ext
     */
    public void addExtension(String namespace, Extension ext) {
        extensions.put(namespace, ext);
    }

    /**
     * Add a new extension.
     *
     * @param name      full-qualified class name
     * @throws UnknownExtensionException
     */
    public void addExtension(String name) throws UnknownExtensionException {
        addExtension(name, getClass().getClassLoader());
    }

    /**
     * Add a new extension.
     *
     * @param name          full-qualified class name
     * @param classLoader   class loader that should be used
     * @throws UnknownExtensionException
     */
    public void addExtension(String name, ClassLoader classLoader) throws UnknownExtensionException {
        Extension ext;
        try {
            Class<?> c = Class.forName(name, true, classLoader);
            ext = (Extension) c.newInstance();
        } catch (Exception e) {
            throw new UnknownExtensionException("The extension " + name + " could not be loaded.");
        }
        String ns = ext.getNamespace();
        addExtension(ns, ext);
    }

    /**
     * Parse an input stream.
     * Same as <code>parse("generic-stream", stream);</code>.
     *
     * @param  stream      input stream to parse
     * @throws XJConfException
     * @throws IOException
     * @deprecated Use {@link #parse(InputStream, String)} instead.
     */
    @Deprecated
    public void parse(InputStream stream) throws XJConfException, IOException {
        parse("generic-stream", stream);
    }

    public void parse(String name, InputStream stream) throws XJConfException, IOException {
        Source source = new StreamSource(name, stream);
        parse(source);
    }

    /**
     * Initialize the parser factory.
     *
     */
    private void initParserFactory() {
        if (null == parserFactory) {
            parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
        }
    }

    /**
     * Parse a configuration file, that you already
     * opened.
     *
     * @param    file      File object to parse
     * @param    classLoader
     * @throws   XJConfException
     * @throws IOException
     */
    public void parse(File file, ClassLoader classLoader) throws XJConfException, IOException {
        loader = classLoader;
        parse(file);
    }

    /**
     * Parse a configuration file, that you already
     * opened.
     *
     * @param    file      File object to parse
     * @throws   XJConfException
     * @throws IOException
     */
    public void parse(File file) throws XJConfException, IOException {
        parse(new FileSource(file));
    }

    /**
     * Parse a configuration file.
     *
     * @param    filename    filename of the configuration file
     * @throws IOException
     * @throws XJConfException
     */
    public void parse(String filename, ClassLoader classLoader) throws XJConfException, IOException {
        File file = new File(filename);
        parse(file, classLoader);
    }

    /**
     * Parse a configuration file.
     *
     * @param    filename    filename of the configuration file
     * @throws IOException
     * @throws XJConfException
     */
    public void parse(String filename) throws XJConfException, IOException {
        File file = new File(filename);
        parse(file);
    }

    /**
     * Parses a source and uses a specified ClassLoader to instantiate parsed classes.
     *
     * @param source
     * @param classLoader
     * @throws XJConfException
     * @throws IOException
     */
    public void parse(Source source, ClassLoader classLoader) throws XJConfException, IOException {
        loader = classLoader;
        parse(source);
    }

    /**
     * Parse a configuration file.
     *
     * @param source
     * @throws XJConfException
     * @throws IOException
     */
    public void parse(Source source) throws XJConfException, IOException {
        initParserFactory();

        openSources.push(source);

        SAXParser saxParser;
        InputStream stream = null;
        try {
            saxParser = parserFactory.newSAXParser();
            stream = source.getInputStream();
            saxParser.parse(stream, this);
        } catch (XJConfException e) {
            throw e;
        } catch (ParserConfigurationException e) {
            throw new InternalError("Could not configure the parser correctly.");
        } catch (SAXException e) {
            throw new XJConfException(e.getMessage());
        } finally {
            openSources.pop();
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * Get the file that currently is being parsed.
     *
     * @return
     * @deprecated Use {@link #getCurrentSource()} instead.
     */
    @Deprecated
    public File getCurrentFile() {
        Source currentSource = getCurrentSource();
        if (currentSource != null
            && currentSource instanceof FileSource) {
            FileSource fileSource = (FileSource) currentSource;
            return fileSource.getFile();
        } else {
            return null;
        }
    }

    public Source getCurrentSource() {
        if (openSources.isEmpty()) {
            return null;
        } else {
            return openSources.peek();
        }
    }

    /**
     * Start element
     *
     * Creates a new Tag object and pushes it
     * onto the stack.
     */
    public void startElement(String namespaceURI, String sName, String qName, Attributes atts) throws SAXException {

        if (myNamespace.equals(namespaceURI) && depth > 0) {
            return;
        }
        depth++;

        // no namespace defined, use the default namespace
        if (namespaceURI.equals("")) {
            namespaceURI = "__default";
        }

        // ignore the root tag
        if (depth == 1) {
            return;
        }

        // This tag needs to be handled by an extension
        if (extensions.containsKey(namespaceURI)) {
            Tag tag = new GenericTag(sName, atts);
            ((Extension) (extensions.get(namespaceURI))).startElement(this, tag, loader);
            tagStack.push(tag);

            // This tag has been defined internally
        } else {
            if (!tagDefs.isNamespaceDefined(namespaceURI)) {
                Source source = getCurrentSource();
                String name = source == null ? null : source.getName();
                throw new UnknownNamespaceException("Unknown namespace " + namespaceURI + " in file "
                        + String.valueOf(name));
            }
            if (!tagDefs.isTagDefined(namespaceURI, sName)) {
                throw new UnknownTagException("Unknown tag " + sName + " in namespace " + namespaceURI);
            }
            DefinedTag tag = new DefinedTag(sName, atts);
            // fetch the defintion for this tag
            TagDefinition tagDef = tagDefs.getTagDefinition(namespaceURI, sName);
            tag.setDefinition(tagDef);
            tag.validate();
            tagStack.push(tag);
        }
    }

    /**
     * End element.
     *
     * Fetches the current element from the stack and
     * converts it to the correct type.
     */
    public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
        if (myNamespace.equals(namespaceURI) && depth > 0) {
            return;
        }
        depth--;

        // no namespace defined, use the default namespace
        if (namespaceURI.equals("")) {
            namespaceURI = "__default";
        }

        // ignore the root tag
        if (depth == 0) {
            return;
        }

        // get the last tag from the stack
        Tag tag = (Tag) tagStack.pop();
        if (extensions.containsKey(namespaceURI)) {
            Tag result = ((Extension) (extensions.get(namespaceURI))).endElement(this, tag, loader);
            if (result != null) {
                if (depth == 1) {
                    config.put(tag.getKey(), result.getConvertedValue(loader));
                } else {
                    Tag parent = (Tag) tagStack.pop();
                    if (result.getKey() == null && !parent.supportsIndexedChildren()) {
                        parent.setContent(result.getConvertedValue(loader));
                    } else {
                        parent.addChild(result);
                    }
                    tagStack.push(parent);
                }
            }
        } else {
            if (depth == 1) {
                config.put(tag.getKey(), tag.getConvertedValue(loader));
            } else {
                Tag parent = (Tag) tagStack.pop();
                parent.addChild(tag);
                tagStack.push(parent);
            }
        }
    }

    /**
     * Character data handler
     *
     * Fetches the current tag from the stack and
     * appends the data.
     */
    public void characters(char[] buf, int offset, int len) throws SAXException {
        if (tagStack.empty()) {
            return;
        }
        Tag tag = (Tag) tagStack.peek();
        tag.addData(buf, offset, len);
    }

    /**
     * fetch a configuration option.
     *
     * @param    name of the option
     * @return   value
     */
    public Object getConfigValue(String name) {
        return config.get(name);
    }

    /**
     * fetch a configuration option.
     *
     * @param    name of the option
     * @return   value
     * @throws   XJConfException    if the value has not been set, or does not have the desired type
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T get(String name, Class<T> clazz) throws XJConfException {
        Object val = config.get(name);
        if (val == null) {
            throw new ValueNotAvailableException("Value with name " + name + " not available in the configuration.");
        }
        if (!clazz.isAssignableFrom(val.getClass())) {
            throw new ValueNotAvailableException("Value with name " + name + " is not of the desired type "
                    + clazz.getName() + ".");
        } else {
            return (T) val;
        }
    }

    public Set<String> getNames() {
        return Collections.unmodifiableSet(config.keySet());
    }

    public Collection<Object> getValues() {
        return Collections.unmodifiableCollection(config.values());
    }
}
