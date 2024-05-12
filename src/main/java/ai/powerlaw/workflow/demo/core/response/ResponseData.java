package ai.powerlaw.workflow.demo.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

import static ai.powerlaw.workflow.demo.core.code.ErrorCodeEnum.ERROR_CODE_BAD_REQUEST;
import static ai.powerlaw.workflow.demo.core.code.ErrorCodeEnum.ERROR_CODE_SUCCESS;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Slf4j
public class ResponseData<T> implements Serializable {
    private Integer code = ERROR_CODE_SUCCESS.getCode();

    private String message;

    private T data;

    public static <T> ResponseData<T> success() {
        return success(null);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(ERROR_CODE_SUCCESS.getCode(),
                ERROR_CODE_SUCCESS.getMessage(), data);
    }

    public static <T> ResponseData<T> failure() {
        return failure(ERROR_CODE_BAD_REQUEST.getCode(), ERROR_CODE_BAD_REQUEST.getMessage());
    }

    public static <T> ResponseData<T> failure(int code, String message) {
        return failure(code, message, null);
    }

    public static <T> ResponseData<T> failure(int code, String message, T data) {
        return new ResponseData<>(code, message, data);
    }
}
