package net.schst.XJConf;

import java.util.ArrayList;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * FactoryMethodDefinition
 *
 * Stores information about a factory method that
 * is used to create an instance.
 *
 * @author Stephan Schmidt
 */
public class FactoryMethodDefinition implements Definition {

    /**
     * Field stores the parameters of the factory method.
     */
    private ArrayList<Definition> params = new ArrayList<Definition>();

    private String name;

    public FactoryMethodDefinition(String name) {
        this.name = name;
    }

    /**
     * Gets the parameters of the factory method.
     * @return List of definitions.
     */
    public ArrayList<Definition> getParams() {
        return this.params;
    }

    /**
     * Add a new child definition (equals a parameter of the factory method).
     */
    public void addChildDefinition(Definition def) throws Exception {
        this.params.add(def);
    }

    public String getName() {
        return this.name;
    }

    public Object convertValue(Tag tag, ClassLoader loader) throws ValueConversionException {
        throw new UnsupportedOperationException();
    }

    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        throw new UnsupportedOperationException();
    }

    public String getSetterMethod() {
        throw new UnsupportedOperationException();
    }
}
