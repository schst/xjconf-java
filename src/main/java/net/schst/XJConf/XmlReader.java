package net.schst.XJConf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.schst.XJConf.exceptions.UnknownExtensionException;
import net.schst.XJConf.exceptions.UnknownNamespaceException;
import net.schst.XJConf.exceptions.UnknownTagException;
import net.schst.XJConf.exceptions.ValueNotAvailableException;
import net.schst.XJConf.exceptions.XJConfException;

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
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 * @author Frank Kleine <frank.kleine@schlund.de>
 */
public class XmlReader extends DefaultHandler {

   /**
    * contains the tag objects
    */
    private Stack<Tag> tagStack = new Stack<Tag>();
    
    /**
     * contains the generated objects
     */
    private HashMap config = new HashMap();

   /**
    * tag definitions
    */
    private NamespaceDefinitions tagDefs = null;
    
   /**
    * current tag depth while parsing the document
    */
    private int depth = 0;
    
    /**
     * Extensions used by the parser
     */
    private HashMap<String,Extension> extensions = new HashMap<String,Extension>();
    
    /**
     * Internal namespace
     */
    private String myNamespace = "http://www.schst.net/XJConf";
    
    /**
     * all files that currently are being processed
     */
    private Stack<File> openFiles = new Stack<File>();
    
    /**
     * The factory for all parsers
     */
    private SAXParserFactory parserFactory = null;

    /**
     * Classloader that will be used for extensions
     * and dynamically created classes
     */
    private ClassLoader loader = this.getClass().getClassLoader();
    
    /**
     * Constructor
     */
    public XmlReader() {
        super();
    }

   /**
    * Set the tag definitions
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
        this.tagDefs = defs;
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
        if (this.tagDefs == null) {
            this.setTagDefinitions(defs);
            return;
        }
        this.tagDefs.appendNamespaceDefinitions(defs);
    }
    
    /**
     * Add a new extension
     * 
     * @param namespace
     * @param ext
     */
    public void addExtension(String namespace, Extension ext) {
        this.extensions.put(namespace, ext);
    }
    
    /**
     * Add a new extension
     * 
     * @param name      full-qualified class name
     * @throws UnknownExtensionException
     */
    public void addExtension(String name) throws UnknownExtensionException {
        this.addExtension(name, this.getClass().getClassLoader());
    }

    /**
     * Add a new extension
     * 
     * @param name          full-qualified class name
     * @param classLoader   class loader that should be used
     * @throws UnknownExtensionException
     */
    public void addExtension(String name, ClassLoader classLoader) throws UnknownExtensionException {
        Extension ext;
        try {
            Class c = Class.forName(name, true, classLoader);
            ext = (Extension)c.newInstance();
        } catch (Exception e) {
            throw new UnknownExtensionException("The extension " + name + " could not be loaded.");
        }
        String ns = ext.getNamespace();
        this.addExtension(ns, ext);
    }
    
    /**
     * Parse an input stream.
     * 
     * @param  stream      input stream to parse
     * @throws XJConfException
     * @throws IOException
     */
    public void parse(InputStream stream) throws XJConfException, IOException {
        this.initParserFactory();
        
        SAXParser saxParser;
        try {
            saxParser = this.parserFactory.newSAXParser();
            saxParser.parse(stream, this);
        } catch (XJConfException e) {
            throw e;
        } catch (ParserConfigurationException e) {
            throw new InternalError("Could not configure the parser correctly."); 
        } catch (SAXException e) {
            throw new XJConfException(e.getMessage());
        }
    }
    
    /**
     * initialize the parser factory
     *
     */
    private void initParserFactory() {
        if (null == this.parserFactory) {
            this.parserFactory = SAXParserFactory.newInstance();
            this.parserFactory.setNamespaceAware(true);
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
        this.loader = classLoader;
        this.parse(file);
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
        this.initParserFactory();
        
        this.openFiles.push(file);
        
        SAXParser saxParser;
        try {
            saxParser = this.parserFactory.newSAXParser();
            saxParser.parse(file, this);
        } catch (XJConfException e) {
            throw e;
        } catch (ParserConfigurationException e) {
            throw new InternalError("Could not configure the parser correctly."); 
        } catch (SAXException e) {
            throw new XJConfException(e.getMessage());
        }
        this.openFiles.pop();
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
        this.parse(file, classLoader);
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
        this.parse(file);
    }
    
    /**
     * Get the file that currently is being parsed
     * 
     * @return
     */
    public File getCurrentFile() {
        return (File)this.openFiles.peek();
    }
    
   /**
    * Start element
    * 
    * Creates a new Tag object and pushes it
    * onto the stack.
    */
    public void startElement(String namespaceURI, String sName, String qName, Attributes atts)
        throws SAXException {
        
        if (this.myNamespace.equals(namespaceURI) && this.depth > 0) {
            return;
        }
        this.depth++;
        
        // no namespace defined, use the default namespace
        if (namespaceURI.equals("")) {
            namespaceURI = "__default";
        }

        // ignore the root tag
        if (this.depth == 1) {
            return;
        }      

        // This tag needs to be handled by an extension
        if (this.extensions.containsKey(namespaceURI)) {
            Tag tag = new GenericTag(sName, atts);
            ((Extension)(this.extensions.get(namespaceURI))).startElement(this, tag, this.loader);
            this.tagStack.push(tag);
            
        // This tag has been defined internally
        } else {
            if (!this.tagDefs.isNamespaceDefined(namespaceURI)) {
                File current = this.getCurrentFile();
                throw new UnknownNamespaceException("Unknown namespace " + namespaceURI + " in file " + current.getAbsolutePath());
            }
            if (!this.tagDefs.isTagDefined(namespaceURI, sName)) {
                throw new UnknownTagException("Unknown tag " + sName + " in namespace " + namespaceURI);
            }
            DefinedTag tag = new DefinedTag(sName, atts);
            // fetch the defintion for this tag
            TagDefinition tagDef = this.tagDefs.getTagDefinition(namespaceURI, sName);
            tag.setDefinition(tagDef);
            tag.validate();
            this.tagStack.push(tag);
        }
    }

   /**
    * End element.
    * 
    * Fetches the current element from the stack and
    * converts it to the correct type.
    */
    public void endElement(String namespaceURI, String sName, String qName)
    throws SAXException {
        if (this.myNamespace.equals(namespaceURI) && this.depth > 0) {
            return;
        }
        this.depth--;

        // no namespace defined, use the default namespace
        if (namespaceURI.equals("")) {
            namespaceURI = "__default";
        }

        // ignore the root tag
        if (this.depth == 0) {
            return;
        }

        // get the last tag from the stack
        Tag tag = (Tag)this.tagStack.pop();
        if (this.extensions.containsKey(namespaceURI)) {
            Tag result = ((Extension)(this.extensions.get(namespaceURI))).endElement(this, tag, this.loader);
            if (result != null) {
		        if (this.depth == 1) {
		           	this.config.put(tag.getKey(), result.getConvertedValue(this.loader));
		        } else {
		            Tag parent = (Tag)this.tagStack.pop();
		            if (result.getKey() == null && !parent.supportsIndexedChildren()) {
		            	parent.setContent(result.getConvertedValue(loader));
		            } else {
		            	parent.addChild(result);
		            }
		            this.tagStack.push(parent);
		        }
            }
        } else {
	        if (this.depth == 1) {
	           	this.config.put(tag.getKey(), tag.getConvertedValue(this.loader));
	        } else {
	            Tag parent = (Tag)this.tagStack.pop();
	            parent.addChild(tag);
	            this.tagStack.push(parent);
	        }
        }
    }

   /**
    * Character data handler
    * 
    * Fetches the current tag from the stack and
    * appends the data.
    */
    public void characters(char buf[], int offset, int len) throws SAXException {
        if (this.tagStack.empty()) {
        	return;
        }
        Tag tag = (Tag)this.tagStack.peek();
        tag.addData(buf, offset, len);
    }

   /**
    * fetch a configuration option
    * 
    * @param    name of the option
    * @return   value
    */
    public Object getConfigValue(String name) {
        return this.config.get(name);
    }

    /**
     * fetch a configuration option
     * 
     * @param    name of the option
     * @return   value
     * @throws   XJConfException    if the value has not been set, or does not have the desired type
     */
     public <T extends Object> T get(String name, Class<T> clazz) throws XJConfException {
         Object val = this.config.get(name);
         if (val == null) {
             throw new ValueNotAvailableException("Value with name " + name + " not available in the configuration.");
         }
         if (!clazz.isAssignableFrom(val.getClass())) {
             throw new ValueNotAvailableException("Value with name " + name + " is not of the desired type " + clazz.getName() + ".");
         }
         return (T)val;
     }
}