package ai.powerlaw.workflow.demo.core.workflow.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Instance {
    private String id;

    private String definitionId;

    private String businessId;

    private Map<String, Variable> variableMap;
}
