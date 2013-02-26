package net.schst.XJConf.exceptions;

public class InvalidTagDefinitionException extends XJConfException {

    private static final long serialVersionUID = -5914098280724162400L;

    public InvalidTagDefinitionException(String message) {
        super(message);
    }

    public InvalidTagDefinitionException(Exception e) {
        super(e);
    }

    public InvalidTagDefinitionException(String message, Exception e) {
        super(message, e);
    }

}
