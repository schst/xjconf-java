package net.schst.XJConf.exceptions;

/**
 * InvalidNamespaceDefinitionException
 *
 * This exception is thrown, when a namespace is incorrectly defined.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class InvalidNamespaceDefinitionException extends XJConfException {

	public InvalidNamespaceDefinitionException(String message) {
		super(message);
	}

	public InvalidNamespaceDefinitionException(Exception e) {
		super(e);
	}

	public InvalidNamespaceDefinitionException(String message, Exception e) {
		super(message, e);
	}
}