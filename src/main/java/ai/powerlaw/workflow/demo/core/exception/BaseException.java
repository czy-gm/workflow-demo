package ai.powerlaw.workflow.demo.core.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private Integer code;

    private String message;

    public BaseException(Throwable cause) {
        this(null, cause.getMessage(), cause);
    }

    public BaseException(Integer code, String message) {
        this(code, message, null);
    }

    public BaseException(Integer code, Throwable cause) {
        this(code, cause.getMessage(), cause);
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public String string() {
        return "{code=" + code + ", message=" + message + "}";
    }
}
