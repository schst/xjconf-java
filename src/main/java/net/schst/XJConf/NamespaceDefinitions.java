package net.schst.XJConf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Stores definitions of several namespaces.
 *  
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class NamespaceDefinitions {

	private HashMap<String,NamespaceDefinition> namespaces = new HashMap<String,NamespaceDefinition>();
	
   /**
    * Add the definition for a namespace
    * 
    * @param namespace      namespace URI  
    * @param defs           namespace definition object
    */
    public void addNamespaceDefinition(String namespace, NamespaceDefinition defs) {
        this.namespaces.put(namespace, defs);
    }

   /**
    * Get a namespace defintition.
    * 
    * @param namespace      namespace URI
    * @return               namespace definition object
    */
    public NamespaceDefinition getNamespaceDefinition(String namespace) {
        return (NamespaceDefinition)this.namespaces.get(namespace);
    }
    
   /**    
    * Check, whether a namespace has been defined
    * 
    * @param namespace      namespace URI
    * @return               true, if the namespace has been defined, false otherwise
    */
    public boolean isNamespaceDefined(String namespace) {
        return this.namespaces.containsKey(namespace);
    }

    /**
     * Get the defined namespaces.
     * 
     * @return              list of all namespace URIs that have been defined
     */
     public Set getDefinedNamespaces() {
         return this.namespaces.keySet();
     }
    
   /**
    * Check, whether a tag has been defined
    * 
    * @param namespace      namespace URI
    * @param tagname        local tag name
    * @return               true, if the tag has been defined, false otherwise
    */
    public boolean isTagDefined(String namespace, String tagname) {
        if (!this.isNamespaceDefined(namespace)) {
            return false;
        }
        return this.getNamespaceDefinition(namespace).isDefined(tagname);
    }
    
   /**
    * Get the definition of a single tag
    * 
    * @param namespace      namespace URI
    * @param localname      local tag name
    * @return               TagDefinition object or null
    */
    public TagDefinition getTagDefinition(String namespace, String localname) {
        if (!this.isNamespaceDefined(namespace)) {
            return null;
        }
        return (TagDefinition)this.getNamespaceDefinition(namespace).getDefinition(localname);
    }

   /**
    * Get the total amount of defined tags in all namespaces
    * 
    * @return               total amount of defined tags
    */
    public int countTagDefinitions() {
        int amount = 0;
        for (Iterator iter = this.getDefinedNamespaces().iterator(); iter.hasNext(); ) {
            String namespace = (String)iter.next();
            amount = amount + this.getNamespaceDefinition(namespace).countTagDefinitions();
        }
        return amount;
    }
    
    /**
     * Append more namespace definitions to the current
     * definitions. Can be used if namespace definitions are read from
     * more than one file.
     * 
     * @param namespaceDefs
     */
    public void appendNamespaceDefinitions(NamespaceDefinitions nsDefs) {
        String namespace;
        NamespaceDefinition nsDef;
        
        for (Iterator iter = nsDefs.getDefinedNamespaces().iterator(); iter.hasNext();) {
             namespace = (String) iter.next();
             nsDef     = nsDefs.getNamespaceDefinition(namespace);
             this.addNamespaceDefinition(namespace, nsDef);
        }
    }
}