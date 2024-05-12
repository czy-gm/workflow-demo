package ai.powerlaw.workflow.demo.service.contract.bean;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ContractDetail {
    private String contractId;

    private String workFlowId;

    private Integer Status;

    private Integer fileId;

    private Map<String, Object> fieldDetail;
}
