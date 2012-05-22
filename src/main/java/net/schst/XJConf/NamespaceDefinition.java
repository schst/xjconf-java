package net.schst.XJConf;

import java.util.HashMap;

/**
 * Container for all tag definitions in one namespace
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class NamespaceDefinition {
    
	private HashMap<String,TagDefinition> tagDefinitions = new HashMap<String,TagDefinition>();
	
	/**
	 * URI of this namespace
	 */
    private String namespaceURI = null;
    
   /**    
    * Constructor for a namespace definition
    * 
    * @param namespaceURI       URI of the new namespace
    */
    public NamespaceDefinition(String namespaceURI) {
        super();
        this.namespaceURI = namespaceURI;
    }
    
   /**
    * Add a new tag definition
    * 
    * @param    tag definition
    */
    public void addTagDefinition(TagDefinition tag) {
        this.tagDefinitions.put(tag.getTagName(), tag);
    }

   /**
    * Count the number of defined tags
    * 
    * @return   number of defined tags
    */
    public int countTagDefinitions() {
    	return this.tagDefinitions.size();
    }

   /**
    * Check, whether a tag has been defined
    * 
    * @param    tagName  name of the tag
    * @return   true, if the tag has been defined, false otherwise
    */
    public boolean isDefined(String tagName){
    	return this.tagDefinitions.containsKey(tagName);
    }

   /**
    * Get the definition of a tag
    * 
    * @param    tagName name of the tag
    * @return   The TagDefinition for this tag
    * @see      net.schst.XJConf.TagDefinition
    */
    public TagDefinition getDefinition(String tagName){
        return (TagDefinition)this.tagDefinitions.get(tagName);
    }

    /**
     * Get the URI for this namespace
     * 
     * @return
     */
    public String getNamespaceURI() {
    	return this.namespaceURI;
    }    
}