package net.schst.XJConf.exceptions;

/**
 * Tag has not been defined.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class UnknownTagException extends XJConfException {

    private static final long serialVersionUID = -5088861694209363039L;

    public UnknownTagException(String message) {
        super(message);
    }

    public UnknownTagException(String message, Exception cause) {
        super(message, cause);
    }

}
