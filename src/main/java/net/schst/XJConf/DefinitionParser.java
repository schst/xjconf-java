package net.schst.XJConf;

import java.io.File;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.schst.XJConf.exceptions.InvalidNamespaceDefinitionException;
import net.schst.XJConf.exceptions.InvalidTagDefinitionException;
import net.schst.XJConf.exceptions.XJConfException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parse tag definitions files.
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class DefinitionParser extends DefaultHandler {
    
    private static final String TAG_FACTORY_METHOD = "factoryMethod";

	/**
     * Stack for currently open definitions
     */
    private Stack<Definition> defStack = new Stack<Definition>();
    
    /**
     * Constant for the default namespace
     */
    public final static String DEFAULT_NAMESPACE = "__default";
    
    /**
     * The current namespace
     */
    private String currentNamespace = DefinitionParser.DEFAULT_NAMESPACE;
    
    /**
     * All extracted namespace definitions
     */
    private NamespaceDefinitions defs = new NamespaceDefinitions();

   /**
    * parse a tag definitions file and return
    * an instance of NamespaceDefinition
    * 
    * @param    filename    filename of the defintions file
    * @return   NamespaceDefinition
    */
    public NamespaceDefinitions parse(String filename)
    	throws XJConfException {
    	return this.parse(new File(filename));
    }
    
   /**
    * parse a tag defintion file 
    * 
    * @param    file     File object that will be parsed
    * @return   NamespaceDefinition
    */
	public NamespaceDefinitions parse(File file)
		throws XJConfException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(file, this);
        }  catch (XJConfException e) {
			throw e;
		} catch (Exception e) {
			throw new XJConfException("Could not read definition file.", e);
		}
        return this.defs;
	}

   /**
    * parse a tag defintion file 
    * 
    * @param    inputStream     InputStream that will be parsed
    * @return   NamespaceDefinition
    */
    public NamespaceDefinitions parse(InputStream inputStream)
        throws XJConfException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputStream, this);
        }  catch (XJConfException e) {
			throw e;
		} catch (Exception e) {
			throw new XJConfException("Could not read definition file.", e);
		}
        return this.defs;
    }
    
    
   /**
    * Start Element handler
    * 
    * Creates the TagDefinition object and places it on
    * the stack.
    */
    public void startElement(String namespaceURI, String sName, String qName, Attributes atts)
        throws SAXException {

        // define a tag
        if (qName.equals("namespace")) {
        	String uri = atts.getValue("uri");
        	if (uri == null) {
        		throw new InvalidNamespaceDefinitionException("The <namespace> tag is missing the uri attribute.");
        	}
            this.currentNamespace = uri;
            return;
        }

        // define a tag
    	if (qName.equals("tag")) {
            TagDefinition def;

            // ensure that the name has been set
            String name = atts.getValue("name");
        	if (name == null) {
        		throw new InvalidTagDefinitionException("The <tag> tag is missing the name attribute.");
        	}
            
        	// 
            String type = atts.getValue("type");
            if (type == null) {
            	type = atts.getValue("primitive");
            }

            // The definition extends another definition
        	if (atts.getValue("extends") != null) {
                NamespaceDefinition nsDef = (NamespaceDefinition)this.defs.getNamespaceDefinition(this.currentNamespace);
        		TagDefinition extendedDef = nsDef.getDefinition(atts.getValue("extends"));
        		def = (TagDefinition)extendedDef.clone();
        		def.setName(name);
        		def.setTagName(name);
            	if (type != null) {
            		def.setType(type);
            	}
        	} else {
        		// Create a definition from scratch
            	if (type == null) {
            		type = "java.lang.String";
            	}
        		
        		def = new TagDefinition(name, type);
        	}
            // key attribute
            String keyAtt = atts.getValue("keyAttribute");
            if (keyAtt != null) {
                def.setKeyAttribute(atts.getValue("keyAttribute"));
            } else {
                String key = atts.getValue("key");
                if (key != null) {
                  	def.setName(key);
                }
            }

            // setter
            String setter = atts.getValue("setter");
            if (setter != null) {
                def.setSetterMethod(setter);
            }
   			this.defStack.push(def);
            return;
    	}
        
        // define the constructor
        if (qName.equals("constructor")) {
            ConstructorDefinition def = new ConstructorDefinition();
            this.defStack.push(def);
        }
        
        // define the constructor
        if (qName.equals(TAG_FACTORY_METHOD)) {
        	// TODO: check, whether name has been specified
            FactoryMethodDefinition def = new FactoryMethodDefinition(atts.getValue("name"));
            this.defStack.push(def);
        }


        // define the character data
        if (qName.equals("cdata")) {
            CDataDefinition def;
            String type = atts.getValue("type");
            if (type == null) {
                def = new CDataDefinition();
            } else {
                def = new CDataDefinition(type);
            }
            String setter = atts.getValue("setter");
            if (setter != null) {
                def.setSetterMethod(setter);
            }
            this.defStack.push(def);
        }

        // define a child tag data
        if (qName.equals("child")) {
        	String name = atts.getValue("name");
        	if (name == null) {
        		throw new InvalidTagDefinitionException("The <child> tag is missing the name attribute.");
        	}
            ChildDefinition def = new ChildDefinition(name);
            this.defStack.push(def);
        }
        
        // define an attribute
        if (qName.equals("attribute")) {
            // get the current tag
            Definition def = (Definition)this.defStack.pop();
            String type = atts.getValue("type");
            if (type == null) {
            	type = atts.getValue("primitive");
            }
        	String name = atts.getValue("name");
        	if (name == null) {
        		throw new InvalidTagDefinitionException("The <attribute> tag is missing the name attribute.");
        	}
            AttributeDefinition attDef = new AttributeDefinition(name, type);
            
            // setter method
            String setter = atts.getValue("setter");
            if (setter != null) {
                attDef.setSetterMethod(setter);
            }
            
            // default value
            String defaultValue = atts.getValue("default");
            if (defaultValue != null) {
                attDef.setDefault(defaultValue);
            }
            
            // required
            String requiredAtt = atts.getValue("required");
            if (requiredAtt != null && requiredAtt.equals("true")) {
                attDef.setRequired(true);
            }
            try {
            	def.addChildDefinition(attDef);
            } catch (Exception e) {
            	throw new InvalidTagDefinitionException("Could not register attribute", e);
            }
            this.defStack.push(def);
            return;
        }
	}

    /**
     * End Element handler
     * 
     * Fetches the TagDefinition from the stack and
     * adds it to the NamespaceDefinition object.
     */
    public void endElement(String namespaceURI, String sName, String qName)
        throws SAXException {

        if (qName.equals("namespace")) {
            this.currentNamespace = DefinitionParser.DEFAULT_NAMESPACE;
        }

        // set the constructor
        if (qName.equals("constructor")) {
            ConstructorDefinition constructorDef = (ConstructorDefinition)this.defStack.pop();
            TagDefinition tagDef = (TagDefinition)this.defStack.peek();
            try {
                tagDef.addChildDefinition(constructorDef);
            } catch (Exception e) {
                throw new InvalidTagDefinitionException("Could not register the constructor", e);
            }
        }

        // set the factory method
        if (qName.equals(TAG_FACTORY_METHOD)) {
            FactoryMethodDefinition factoryDef = (FactoryMethodDefinition)this.defStack.pop();
            TagDefinition tagDef = (TagDefinition)this.defStack.peek();
            try {
                tagDef.addChildDefinition(factoryDef);
            } catch (Exception e) {
                throw new InvalidTagDefinitionException("Could not register the factory method.", e);
            }
        }

        
        // set the cdata handling
        if (qName.equals("cdata")) {
            CDataDefinition cdataDef = (CDataDefinition)this.defStack.pop();
            Definition parentDef = (Definition)this.defStack.peek();
            try {
                parentDef.addChildDefinition(cdataDef);
            } catch (Exception e) {
                throw new InvalidTagDefinitionException("Could not register CData handling", e);
            }
        }

        // set the child handling
        if (qName.equals("child")) {
            ChildDefinition childDef = (ChildDefinition)this.defStack.pop();
            Definition parentDef = (Definition)this.defStack.peek();
            try {
                parentDef.addChildDefinition(childDef);
            } catch (Exception e) {
                throw new InvalidTagDefinitionException("Could not handle child definition", e);
            }
        }

        if (qName.equals("tag")) {
           	TagDefinition def = (TagDefinition)this.defStack.pop();
            
            if (!this.defs.isNamespaceDefined(this.currentNamespace)) {
                this.defs.addNamespaceDefinition(this.currentNamespace, new NamespaceDefinition(this.currentNamespace));
            }
            NamespaceDefinition nsDef = (NamespaceDefinition)this.defs.getNamespaceDefinition(this.currentNamespace);
           	nsDef.addTagDefinition(def);
        }
    }
}