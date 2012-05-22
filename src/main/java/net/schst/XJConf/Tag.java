package net.schst.XJConf;

import java.util.ArrayList;
import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Interface for tag containers
 * 
 * @author Stephan Schmidt <me@schst.net>
 */
public interface Tag {
	public int addChild(Tag child);
	public int addData(char buf[], int offset, int len);
	public void setContent(Object content);
	public boolean hasAttribute(String name);
	public String getAttribute(String name);
	public ArrayList getChildren();
	public Tag getChild(String name);
	public String getName();
	public String getData();
	public Object getContent();
	public Object getConvertedValue(ClassLoader loader) throws ValueConversionException;
	public Class getValueType(Tag tag, ClassLoader loader);
	public String getKey();
	public String getSetterMethod();
	public boolean supportsIndexedChildren();
	public boolean validate() throws XJConfException;
}