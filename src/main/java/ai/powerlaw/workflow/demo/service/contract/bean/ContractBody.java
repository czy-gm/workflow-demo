package ai.powerlaw.workflow.demo.service.contract.bean;

import lombok.Data;

import java.util.Map;

@Data
public class ContractBody {
    private int fileId;

    private Map<String, Object> fieldDetail;

    private String pointer;
}
