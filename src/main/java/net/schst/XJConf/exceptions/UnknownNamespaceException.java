package net.schst.XJConf.exceptions;

/**
 * Namespace has not been defined.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class UnknownNamespaceException extends XJConfException {

    private static final long serialVersionUID = -6989557552070554198L;

    public UnknownNamespaceException(String message) {
        super(message);
    }

    public UnknownNamespaceException(String message, Exception cause) {
        super(message, cause);
    }

}
