package ai.powerlaw.workflow.demo.core.workflow.bean;

import lombok.Data;

import java.util.Map;

@Data
public class StartInstanceDTO {
    private String businessId;

    private Map<String, Variable> variableMap;
}
