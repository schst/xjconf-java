package net.schst.XJConf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
        tagName = name;
        setType(type);
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
            addAttribute((AttributeDefinition) def);
        }
        else if (def instanceof FactoryMethodDefinition) {
            factoryMethod = (FactoryMethodDefinition) def;
        }
        else if (def instanceof ConstructorDefinition) {
            constructor = (ConstructorDefinition) def;
        }
        else if (def instanceof CDataDefinition) {
            cdata = (CDataDefinition) def;
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
     * set the name of the value.
     *
     * @return   name of the value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the name of the tag.
     *
     * @param aName
     */
    public void setTagName(String aName) {
        tagName = aName;
    }

    /**
     * Set the attribute that will be used as key.
     *
     * @return   name of the value
     */
    public void setKeyAttribute(String att) {
        name = "__attribute";
        nameAttribute = att;
    }

    /**
     * get the name of the value.
     *
     * @return   name of the value
     */
    public String getName() {
        return name;
    }

    /**
     * get the name of the tag.
     *
     * @return   name of the tag
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * get the name of the tag.
     *
     * @return   name of the tag
     */
    public String getKey(DefinedTag tag) {
        if (name.equals("__attribute")) {
            return tag.getAttribute(nameAttribute);
        }
        return name;
    }

    /**
     * get the type of the tag.
     *
     * @return   type of the tag
     */
    public String getType() {
        return type;
    }

    /**
     * Get the type of the tag.
     *
     * @return  Class object
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        try {
            return getValueConverter().getType(loader);
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
        setter = aSetter;
    }

    /**
     * Get the name of the setter method that should be used.
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
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Convert the value of the tag.
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
        if (constructor == null) {
            constructor = new ConstructorDefinition();
            try {
                constructor.addChildDefinition(new CDataDefinition());
            } catch (Exception e) {
                throw new ValueConversionException("Could not create constructor object", e);
            }
        }

        ArrayList<Definition> conParams;
        if (factoryMethod != null) {
            conParams = factoryMethod.getParams();
        } else {
            conParams = constructor.getParams();
        }
        Definition paramDef;

        Class<?>[] cParamTypes = new Class[conParams.size()];
        Object[] cParams = new Object[conParams.size()];

        // get all values and their types
        for (int i = 0; i < conParams.size(); i++) {
            paramDef = (Definition) conParams.get(i);
            cParams[i] = paramDef.convertValue(tag, loader);
            cParamTypes[i] = paramDef.getValueType(tag, loader);
        }
        Object instance = getValueConverter().convertValue(cParams, cParamTypes, loader);

        // add attributes and child elements
        addAttributesToValue(instance, tag, loader);
        addChildrenToValue(instance, tag, loader);
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
        for (int i = 0; i < atts.size(); i++) {

            // get the attribute definition
            AttributeDefinition att = (AttributeDefinition) atts.get(i);
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
                        } catch (Exception ex) {
                            continue;
                        }
                        break;
                    }
                }

                Object[] meParams = {val};

                me.invoke(instance, meParams);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw new ValueConversionException("Could not set attribute '" + att.getName() + "' of '"
                            + type + "'.", (Exception) e.getTargetException());
                } else {
                    throw new RuntimeException("Could not set attribute '" + att.getName() + "' of '" + type
                            + "'.", e.getTargetException());
                }
            } catch (Exception e) {
                throw new ValueConversionException("Could not set attribute '" + att.getName() + "' of '" + type
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

        ArrayList<String> ignore = constructor.getUsedChildrenNames();

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
                if (instance instanceof java.util.Properties) {
                    String key = (String) child.getKey();

                    Properties properties = (Properties) instance;
                    properties.setProperty(key, (String) childValue);

                    // Check, whether the current instance is a HashMap
                } else if (instance instanceof java.util.AbstractMap<?, ?>) {
                    Object oName = (Object) child.getKey();

                    AbstractMap<Object, Object> map = (AbstractMap<Object, Object>) instance;
                    map.put(oName, childValue);

                    // Check, whether the current instance is a collection
                } else if (methodName == null && instance instanceof java.util.AbstractCollection<?>) {
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
                            } catch (Exception ex) {
                                continue;
                            }
                            break;
                        }
                    }
                    if (childMethod == null) {
                        throw new ValueConversionException("Could not add child " + child.getKey() + " to "
                                + getType() + " using " + methodName + "() because this method does not exist.");
                    } else if (Modifier.toString(childMethod.getModifiers()).contains("private")) {
                        throw new ValueConversionException("Could not add child " + child.getKey() + " to "
                                + getType() + " using " + methodName + "() because this method is private.");
                    }
                    Object[] childParams = {childValue};
                    childMethod.invoke(instance, childParams);
                }
            } catch (Exception e) {
                throw new ValueConversionException("Could not add child " + child.getKey() + " to " + getType()
                        + " using " + methodName + "(), exception message: " + e.getMessage() + ".", e);
            }
        }
    }

    /**
     * Check, whether the value supports indexed children.
     *
     * @return true or false
     */
    public boolean supportsIndexedChildren() {
        //TODO Find a better (and working) way to do this check.
        return type.equals("java.util.ArrayList");
    }

    /**
     * Get all interfaces of a class.
     *
     * @param result
     * @param superClass
     * @return
     */
    private List<Class<?>> determineAllInterfaces(List<Class<?>> result, Class<?> superClass) {
        Class<?>[] subinterfaces = superClass.getInterfaces();
        for (Class<?> inter : subinterfaces) {
            result.add(inter);
            determineAllInterfaces(result, inter);
        }
        Class<?> subclass = superClass.getSuperclass();
        if (subclass != null) {
            result.add(subclass);
            determineAllInterfaces(result, subclass);
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
            copy.atts = (ArrayList<AttributeDefinition>) atts.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Could not extend a tag definition.", e);
        }
        return copy;
    }

    /**
     * Get the value converter for this tag.
     *
     * @return
     */
    private ValueConverter getValueConverter() {

        if (valueConverter == null) {
            if (type.indexOf(".") == -1) {
                valueConverter = new PrimitiveValueConverter(type);
            } else {
                if (factoryMethod != null) {
                    valueConverter = new FactoryMethodValueConverter(type, factoryMethod.getName());
                } else {
                    valueConverter = new ObjectValueConverter(type);
                }
            }
        }
        return valueConverter;
    }

    /**
     * Check, whether the tag has an attribute defined.
     *
     * @param attributeName
     * @return
     */
    public boolean hasAttributeDefinition(String attributeName) {
        for (AttributeDefinition attDefinition : atts) {
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
     * @return
     */
    public String getNameAttribute() {
        return nameAttribute;
    }
}
