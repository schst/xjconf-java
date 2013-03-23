package net.schst.XJConf;

import java.util.ArrayList;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Definition for the constructor of a class.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de/>
 */
public class ConstructorDefinition implements Definition {

    /**
     * Parameters of the constructor.
     */
    private ArrayList<Definition> params = new ArrayList<Definition>();

    /**
     * Add a new child definition (equals a parameter of the constructor).
     */
    public void addChildDefinition(Definition def) throws Exception {
        this.params.add(def);
    }

    /**
     * Gets the parameters of the constructor.
     * @return List of definitions.
     */
    public ArrayList<Definition> getParams() {
        return this.params;
    }

    /**
     * Convert the constructor.
     *
     * This does not do anything!
     */
    public Object convertValue(Tag tag, ClassLoader loader) throws ValueConversionException {
        return null;
    }

    /**
     * Get the name under which it will be stored.
     */
    public String getName() {
        return "__constructor";
    }

    /**
     * Gets the type of the constructor.
     *
     * @return  Always null
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        return null;
    }

    /**
     * Get the setter method.
     */
    public String getSetterMethod() {
        return null;
    }

    /**
     * Gets the names of all child elements that are used in
     * the constructor.
     *
     * These children are not used when adding them using
     * setter-methods.
     *
     * @return List of children names
     */
    public ArrayList<String> getUsedChildrenNames() {
        ArrayList<String> childrenNames = new ArrayList<String>();
        Definition def;
        for (int i = 0; i < this.params.size(); i++) {
            def = this.params.get(i);
            if (def instanceof ChildDefinition) {
                childrenNames.add(def.getName());
            }
        }
        return childrenNames;
    }

}
