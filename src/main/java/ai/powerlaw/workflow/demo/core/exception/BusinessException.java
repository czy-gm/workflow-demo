package ai.powerlaw.workflow.demo.core.exception;


import ai.powerlaw.workflow.demo.core.code.CodeEnum;
import ai.powerlaw.workflow.demo.core.code.ErrorCodeEnum;

import java.io.Serializable;

public class BusinessException extends BaseException implements Serializable {
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        this(ErrorCodeEnum.ERROR_CODE_SYSTEM_ERROR.getMessage());
    }

    public BusinessException(String msg, Object... objects) {
        super(ErrorCodeEnum.ERROR_CODE_SYSTEM_ERROR.getCode(), String.format(msg, objects));
    }

    public BusinessException(Throwable cause) {
        super(cause instanceof BaseException ?
                ((BaseException) cause).getCode() : ErrorCodeEnum.ERROR_CODE_SYSTEM_ERROR.getCode(), cause);
    }

    public BusinessException(CodeEnum codeEnum, Object... objects) {
        this(null, codeEnum, objects);
    }

    public BusinessException(int code, String message, Object... objects) {
        super(code, String.format(message, objects), null);
    }

    public BusinessException(Throwable cause, CodeEnum codeEnum, Object... objects) {
        super(codeEnum.getCode(), String.format(codeEnum.getMessage(), objects), cause);
    }
}
