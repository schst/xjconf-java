package net.schst.XJConf.exceptions;

import net.schst.XJConf.exceptions.XJConfException;


/**
 * Namespace has not been defined.
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class UnknownNamespaceException extends XJConfException {
    public UnknownNamespaceException(String message) {
        super(message);
    }
    public UnknownNamespaceException(String message, Exception cause) {
        super(message, (Exception) cause);
    }
}
