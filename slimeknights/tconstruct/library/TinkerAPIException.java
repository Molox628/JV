package slimeknights.tconstruct.library;

public class TinkerAPIException extends RuntimeException
{
    public TinkerAPIException() {
    }
    
    public TinkerAPIException(final String message) {
        super("[TCon API] " + message);
    }
    
    public TinkerAPIException(final String message, final Throwable cause) {
        super("[TCon API] " + message, cause);
    }
    
    public TinkerAPIException(final Throwable cause) {
        super(cause);
    }
    
    public TinkerAPIException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super("[TCon API] " + message, cause, enableSuppression, writableStackTrace);
    }
}
