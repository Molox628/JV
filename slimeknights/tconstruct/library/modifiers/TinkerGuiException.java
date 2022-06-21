package slimeknights.tconstruct.library.modifiers;

public class TinkerGuiException extends Exception
{
    public TinkerGuiException() {
    }
    
    public TinkerGuiException(final String message) {
        super(message);
    }
    
    public TinkerGuiException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public TinkerGuiException(final Throwable cause) {
        super(cause);
    }
    
    public TinkerGuiException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
