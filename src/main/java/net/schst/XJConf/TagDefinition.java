package net.schst.XJConf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Defintion of an XML tag.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de/>
 * @author Dunja Fehrenbach <dunja@schlund.de/>
 */
public class TagDefinition implements Definition, Cloneable {

    private String name = null;
    private String tagName = null;
    private String type = null;
    private ArrayList<AttributeDefinition> atts = new ArrayList<AttributeDefinition>();
    private String setter = null;
    private String nameAttribute = null;
    private ConstructorDefinition constructor = null;
    private FactoryMethodDefinition factoryMethod = null;

    private ValueConverter valueConverter;

    //TODO Eventually call the setter method for the cdata
    @SuppressWarnings("unused")
    private CDataDefinition cdata = null;

    /**
     * Create a new tag definition.
     *
     * @param name
     * @param type
     * @throws XJConfException
     */
    public TagDefinition(String name, String type) throws XJConfException {

        if (name == null) {
            throw new XJConfException("TagDefinition needs a name.");
        }
        if (type == null) {
            throw new XJConfException("TagDefinition needs a type.");
        }

        this.name = name;
        this.tagName = name;
        this.setType(type);
    }

    /**
     * Set the type of the tag.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Add a new child definition.
     *
     * Possible definitions are:
     * - AttributeDefinition
     * - ConstrcutorDefinition
     * - CDataDefinition
     */
    public void addChildDefinition(Definition def) throws Exception {

        if (def instanceof AttributeDefinition) {
            this.addAttribute((AttributeDefinition) def);
            return;
        }
        if (def instanceof FactoryMethodDefinition) {
            this.factoryMethod = (FactoryMethodDefinition) def;
            return;
        }
        if (def instanceof ConstructorDefinition) {
            this.constructor = (ConstructorDefinition) def;
            return;
        }
        if (def instanceof CDataDefinition) {
            this.cdata = (CDataDefinition) def;
            return;
        }
    }

    /**
     * Add an attribute to the tag.
     *
     * @param    att attribute definition
     * @throws Exception
     */
    public void addAttribute(AttributeDefinition att) throws Exception {
        atts.add(att);
    }

    /**
     * Sets the name of the value.
     *
     * @param aName
     */
    public void setName(String aName) {
        name = aName;
    }

    /**
     * Sets the name of the tag.
     *
     * @param aTagName
     */
    public void setTagName(String aTagName) {
        tagName = aTagName;
    }

    /**
     * Sets the attribute that will be used as key.
     */
    public void setKeyAttribute(String att) {
        this.name = "__attribute";
        this.nameAttribute = att;
    }

    /**
     * get the name of the value.
     *
     * @return   name of the value
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the name of the tag.
     *
     * @return   name of the tag
     */
    public String getTagName() {
        return this.tagName;
    }

    /**
     * get the name of the tag.
     *
     * @return   name of the tag
     */
    public String getKey(DefinedTag tag) {
        if (this.name.equals("__attribute")) {
            return tag.getAttribute(this.nameAttribute);
        }
        return this.name;
    }

    /**
     * get the type of the tag.
     *
     * @return   type of the tag
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the type of the tag.
     *
     * @return  Class object
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        try {
            return this.getValueConverter().getType(loader);
        } catch (Exception e) {
            throw new RuntimeException("Could not return type.");
        }
    }

    /**
     * Set the setter method.
     *
     * @param aSetter   name of the setter method
     */
    public void setSetterMethod(String aSetter) {
        this.setter = aSetter;
    }

    /**
     * Gets the name of the setter method that should be used.
     *
     * @return         name of the setter method
     */
    public String getSetterMethod() {
        if (setter != null) {
            return setter;
        }
        // no name, the parent should be a collection
        if (name.equals("__none")) {
            return null;
        }
        return "set" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
    }

    /**
     * Converts the value of the tag.
     *
     * @param tag      tag that will be converted
     * @param loader   ClassLoader that should be used for loading new classes
     * @return         converted value
     * @throws ValueConversionException
     */
    public Object convertValue(Tag tag, ClassLoader loader) throws ValueConversionException {

        // get the data
        Object data = tag.getContent();
        if (data == null) {
            data = "";
        }

        // no constructor definition has been set,
        // create a new one
        if (this.constructor == null) {
            this.constructor = new ConstructorDefinition();
            try {
                this.constructor.addChildDefinition(new CDataDefinition());
            } catch (Exception e) {
                throw new ValueConversionException("Could not create constructor object", e);
            }
        }

        ArrayList<Definition> conParams;
        if (this.factoryMethod != null) {
            conParams = this.factoryMethod.getParams();
        } else {
            conParams = this.constructor.getParams();
        }
        Definition paramDef;

        Class<?>[] cParamTypes = new Class[conParams.size()];
        Object[] cParams = new Object[conParams.size()];

        // get all values and their types
        for (int i = 0; i < conParams.size(); i++) {
            paramDef = conParams.get(i);
            cParams[i] = paramDef.convertValue(tag, loader);
            cParamTypes[i] = paramDef.getValueType(tag, loader);
        }
        Object instance = this.getValueConverter().convertValue(cParams, cParamTypes, loader);

        // add attributes and child elements
        this.addAttributesToValue(instance, tag, loader);
        this.addChildrenToValue(instance, tag, loader);
        return instance;
    }

    /**
     * Add all attributes using the appropriate setter methods.
     *
     * @param instance
     * @param tag
     * @param loader
     * @throws ValueConversionException
     */
    private void addAttributesToValue(Object instance, Tag tag, ClassLoader loader) throws ValueConversionException {

        Class<?> cl = instance.getClass();

        // set all attributes
        String methodName = null;
        for (int i = 0; i < this.atts.size(); i++) {

            // get the attribute definition
            AttributeDefinition att = this.atts.get(i);
            Object val = att.convertValue(tag, loader);

            // attribute has not been set and there is no
            // default value, skip the method call
            if (val == null) {
                continue;
            }

            try {
                methodName = att.getSetterMethod();
                Class<?>[] meParamTypes = {att.getValueType(tag, loader)};
                Method me = null;

                try {
                    me = cl.getMethod(methodName, meParamTypes);
                } catch (NoSuchMethodException e) {
                    List<Class<?>> interfaces = determineAllInterfaces(att.getValueType(tag, loader));
                    for (Class<?> iface : interfaces) {
                        try {
                            Class<?>[] meParamTypes2 = {iface};
                            me = cl.getMethod(methodName, meParamTypes2);
                            break;
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                }

                Object[] meParams = {val};

                me.invoke(instance, meParams);
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                if (t instanceof Exception) {
                    throw new ValueConversionException("Could not set attribute '" + att.getName() + "' of '"
                            + this.type + "'.", (Exception) t);
                }
                throw new RuntimeException("Could not set attribute '" + att.getName() + "' of '" + this.type
                        + "'.", t);
            } catch (Exception e) {
                throw new ValueConversionException("Could not set attribute '" + att.getName() + "' of '" + this.type
                        + "'.", e);
            }
        }
    }

    /**
     * Add all children to the created instance.
     *
     * @param instance
     * @param tag
     * @param loader
     * @throws ValueConversionException
     */
    @SuppressWarnings("unchecked")
    private void addChildrenToValue(Object instance, Tag tag, ClassLoader loader) throws ValueConversionException {

        ArrayList<String> ignore = this.constructor.getUsedChildrenNames();

        String methodName;
        Class<?> cl = instance.getClass();

        // traverse all children
        ArrayList<Tag> children = tag.getChildren();
        if (children.size() == 0) {
            return;
        }

        for (Tag child : children) {
            if (ignore.contains(child.getName())) {
                continue;
            }

            methodName = child.getSetterMethod();
            Object childValue = child.getConvertedValue(loader);

            try {

                // Check, whether the current instance is a Properties object
                if (instance instanceof Properties) {
                    String key = child.getKey();

                    Properties properties = (Properties) instance;
                    properties.setProperty(key, (String) childValue);

                    // Check, whether the current instance is a HashMap
                } else if (instance instanceof AbstractMap<?, ?>) {
                    Object oName = child.getKey();

                    AbstractMap<Object, Object> map = (AbstractMap<Object, Object>) instance;
                    map.put(oName, childValue);

                    // Check, whether the current instance is a collection
                } else if (methodName == null && instance instanceof AbstractCollection<?>) {
                    AbstractCollection<Object> collection = (AbstractCollection<Object>) instance;
                    collection.add(childValue);

                    // instance is any object
                } else {
                    Class<?>[] childParamTypes = {child.getValueType(child, loader)};
                    Method childMethod = null;

                    try {
                        childMethod = cl.getMethod(methodName, childParamTypes);
                    } catch (NoSuchMethodException e) {
                        List<Class<?>> interfaces = determineAllInterfaces(child.getValueType(child, loader));
                        for (Class<?> iface : interfaces) {
                            try {
                                Class<?>[] childParamTypes2 = {iface};
                                childMethod = cl.getMethod(methodName, childParamTypes2);
                                break;
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                    }
                    if (childMethod == null) {
                        throw new ValueConversionException("Could not add child " + child.getKey() + " to "
                                + this.getType() + " using " + methodName + "() because this method does not exist.");
                    } else if (Modifier.toString(childMethod.getModifiers()).contains("private")) {
                        throw new ValueConversionException("Could not add child " + child.getKey() + " to "
                                + this.getType() + " using " + methodName + "() because this method is private.");
                    }
                    Object[] childParams = {childValue};
                    childMethod.invoke(instance, childParams);
                }
            } catch (Exception e) {
                throw new ValueConversionException("Could not add child " + child.getKey() + " to " + this.getType()
                        + " using " + methodName + "(), exception message: " + e.getMessage() + ".", e);
            }
        }
    }

    /**
     * Checks whether the value supports indexed children.
     *
     * @return true or false
     */
    public boolean supportsIndexedChildren() {
        //TODO Find a better (and working) way to do this check.
        if (type.equals("java.util.ArrayList")) {
            return true;
        }
        return false;
    }

    /**
     * Get all interfaces of a class.
     *
     * @param result
     * @param superClass
     * @return List of Class objects
     */
    private List<Class<?>> determineAllInterfaces(List<Class<?>> result, Class<?> superClass) {
        Class<?>[] subinterfaces = superClass.getInterfaces();
        for (Class<?> inter : subinterfaces) {
            result.add(inter);
            this.determineAllInterfaces(result, inter);
        }
        Class<?> subclass = superClass.getSuperclass();
        if (subclass != null) {
            result.add(subclass);
            this.determineAllInterfaces(result, subclass);
        }
        return result;
    }

    private List<Class<?>> determineAllInterfaces(Class<?> superClass) {
        List<Class<?>> result = new LinkedList<Class<?>>();
        return determineAllInterfaces(result, superClass);
    }

    /**
     * Clone the tag definition.
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        TagDefinition copy;
        try {
            copy = (TagDefinition) super.clone();
            copy.atts = (ArrayList<AttributeDefinition>) this.atts.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Could not extend a tag definition.", e);
        }
        return copy;
    }

    /**
     * Get the value converter for this tag.
     *
     * @return Lazily created ValueConverter.
     */
    private ValueConverter getValueConverter() {

        if (this.valueConverter == null) {
            if (this.type.indexOf(".") == -1) {
                this.valueConverter = new PrimitiveValueConverter(this.type);
            } else {
                if (this.factoryMethod != null) {
                    this.valueConverter = new FactoryMethodValueConverter(this.type, this.factoryMethod.getName());
                } else {
                    this.valueConverter = new ObjectValueConverter(this.type);
                }
            }
        }
        return this.valueConverter;
    }

    /**
     * Check, whether the tag has an attribute defined.
     *
     * @param attributeName
     * @return true or false
     */
    public boolean hasAttributeDefinition(String attributeName) {
        for (AttributeDefinition attDefinition : this.atts) {
            if (attDefinition.getName().equals(attributeName)) {
                return true;
            }
        }
        if (constructor != null) {
            for (Definition def : constructor.getParams()) {
                if (def instanceof AttributeDefinition
                    && def.getName().equals(attributeName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the name attribute.
     *
     * @return String
     */
    public String getNameAttribute() {
        return this.nameAttribute;
    }

}
