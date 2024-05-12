package ai.powerlaw.workflow.demo.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements CodeEnum {

    ERROR_CODE_SUCCESS(0, "成功"),
    ERROR_CODE_BAD_REQUEST(400, "参数错误"),
    ERROR_CODE_SERVER_ERROR(500, "系统错误"),
    ERROR_CODE_SYSTEM_ERROR(999999, "系统错误"),

    ERROR_CODE_PROCESS_DEFINITION_NOT_EXIST(10001, "流程定义不存在，请联系系统管理员"),
    ERROR_CODE_CONTRACT_NOT_EXIST(10002,"合同不存在"),
    ERROR_CODE_DRAFT_TASK_NOT_EXIST(10003, "起草流程任务不存在，请联系系统管理员")
    ;

    private final int code;
    private final String message;
}
