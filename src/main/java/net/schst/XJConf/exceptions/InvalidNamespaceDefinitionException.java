package net.schst.XJConf.exceptions;

/**
 * This exception is thrown when a namespace is incorrectly defined.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class InvalidNamespaceDefinitionException extends XJConfException {

    private static final long serialVersionUID = 3277376593527207523L;

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
