package net.schst.XJConf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.schst.XJConf.exceptions.UnknownAttributeException;
import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

import org.xml.sax.Attributes;

/**
 * Container for an XML tag
 * 
 * Stores the character data, attributes as well as already
 * converted children.
 * 
 * @author Stephan Schmidt
 */
public class DefinedTag implements Tag {
	
   /**
    * name of the tag 
    */
    private String name = null;
    
   /**
    * character data
    */
    private StringBuffer data = new StringBuffer();
    
    
    private Object content = null;
    
   /**
    * attributes of the tag
    */
	private HashMap<String,String> atts = new HashMap<String,String>();
    
   /**
    * Children of the tag
    */
    private ArrayList<Tag> children = new ArrayList<Tag>();

    /**
     * Tag definition used for this tag
     */
    private TagDefinition def = null;
    
   /**
    * Create a new tag without attributes
    * 
    * @param name   name of the tag
    */
    public DefinedTag(String name) {
    	this.name = name;
    }

   /**
    * Create a new tag with attributes
    * 
    * @param name   name of the tag
    * @param atts   attributes of the tag
    */
    public DefinedTag(String name, Attributes atts) {
        this.name = name;

        // store attributes in a HashMap
        for (int i = 0; i < atts.getLength(); i++ ) {
        	this.atts.put(atts.getQName(i), atts.getValue(i));
        }
    }

   /**
    * Add a new child to this tag.
    * 
    * @param child  child to add
    * @return       number of childs added
    */
    public int addChild(Tag child) {
    	this.children.add(child);
        return this.children.size();
    }

    /**
     * Add text data
     */
    public int addData(char buf[], int offset, int len) {
    	String s = new String(buf, offset, len);
        this.data.append(s);
        return this.data.length();
    }

    /**
     * Check, whether the tag has a certain attribute
     * 
     * @param name
     * @return
     */
    public boolean hasAttribute(String name) {
        return this.atts.containsKey(name);
    }
    
   /**
    * get an attribute
    * 
    * @param    name of the attribute
    * @return   value of the attribute
    */
    public String getAttribute(String name) {
    	return (String)this.atts.get(name);
    }

   /**
    * Get all children of the tag
    * 
    * @return   children
    */
    public ArrayList getChildren() {
        return this.children;
    }

    /**
     * Get the child with a specific name
     * 
     * @param name
     * @return
     */
    public Tag getChild(String name) {
        for (Iterator iter = this.children.iterator(); iter.hasNext();) {
            DefinedTag child = (DefinedTag) iter.next();
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }
    
   /**
    * Get the name of the tag
    * 
    * @return   name of the tag
    */
    public String getName() {
        return this.name;
    }
    
   /**
    * Get the character data of the tag
    * 
    * @return   character data
    */
    public String getData() {
    	return this.data.toString().trim();
    }
    
   /**
    * Set the tag definition object used for this tag
    *  
    * @param def
    */
    public void setDefinition(TagDefinition def) {
        this.def = def;
    }
    
    /**
     * get the tag definition object used for this tag
     *  
     * @return def
     */
    public TagDefinition getDefinition() {
        return this.def;
    }

    /**
     * Convert the value into the correct tag
     *  
     * @return  converted value
     */
    public Object getConvertedValue(ClassLoader loader) throws ValueConversionException {
        return this.def.convertValue(this,loader);
    }

    /**
     * Get the type of the attribute
     * 
     * @return  Class object
     */
    public Class getValueType(Tag tag, ClassLoader loader) {
        return this.def.getValueType(tag, loader);
    }
    
   /**
    * Get the key, under which the tag will be stored in
    * the configuration
    * 
    * @return   key for the tag
    */
    public String getKey() {
        return this.def.getKey(this);
    }

    public String getSetterMethod() {
    	return this.def.getSetterMethod();
    }    

	public void setContent(Object content) {
		this.content = content;
	}

	public Object getContent() {
		if (this.content != null) {
			return this.content;
		}
		return this.getData();
	}
	
	public boolean supportsIndexedChildren() {
		return this.def.supportsIndexedChildren();
	}

	/**
	 * Validate the tag against the definition
	 */
    public boolean validate() throws XJConfException {
        for (String attributeName : this.atts.keySet()) {
            if (attributeName.equals(this.def.getNameAttribute())) {
                continue;
            }
            if (!this.def.hasAttributeDefinition(attributeName)) {
                throw new UnknownAttributeException("The attribute " + attributeName + " has not been defined for the tag " + this.def.getTagName() + ".");
            }
        }
        return true;
    }
}