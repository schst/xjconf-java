package net.schst.XJConf.exceptions;

import net.schst.XJConf.exceptions.XJConfException;

/**
 * Tag has not been defined.
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class UnknownTagException extends XJConfException {
    public UnknownTagException(String message) {
        super(message);
    }

    public UnknownTagException(String message, Exception cause) {
        super(message, (Exception) cause);
    }
}
