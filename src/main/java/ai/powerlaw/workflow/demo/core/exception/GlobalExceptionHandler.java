package ai.powerlaw.workflow.demo.core.exception;

import ai.powerlaw.workflow.demo.core.response.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;

import static ai.powerlaw.workflow.demo.core.code.ErrorCodeEnum.ERROR_CODE_BAD_REQUEST;
import static ai.powerlaw.workflow.demo.core.code.ErrorCodeEnum.ERROR_CODE_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;

    @ExceptionHandler(ValidationException.class)
    public ResponseData<String> handleException(ValidationException e) {
        e.printStackTrace();
        log.error("ValidationException: {}", e.getMessage());
        return ResponseData.failure(ERROR_CODE_BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseData<String> handleException(NullPointerException e) {
        e.printStackTrace();
        log.error("NullPointerException: {}", e.getMessage());
        return ResponseData.failure(ERROR_CODE_SERVER_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseData<String> handleException(Exception e) {
        e.printStackTrace();
        log.error("RuntimeException: {}", e.getMessage());
        return ResponseData.failure(ERROR_CODE_SERVER_ERROR.getCode(), e.getMessage());

    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseData<String> handleException(AsyncRequestTimeoutException e) {
        e.printStackTrace();
        log.error("AsyncRequestTimeoutException: {}", e.getMessage());
        return ResponseData.failure(ERROR_CODE_BAD_REQUEST.getCode(), e.getMessage());
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResponseData) {
            return body;
        }
        if (body instanceof String) {
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return objectMapper.writeValueAsString(ResponseData.success(body));
        }
        return ResponseData.success(body);
    }
}
