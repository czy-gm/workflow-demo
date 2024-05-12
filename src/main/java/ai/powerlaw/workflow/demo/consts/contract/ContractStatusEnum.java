package ai.powerlaw.workflow.demo.consts.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContractStatusEnum {
    DRAFT(1),
    ASSESS(2)
    ;

    private int code;
}
