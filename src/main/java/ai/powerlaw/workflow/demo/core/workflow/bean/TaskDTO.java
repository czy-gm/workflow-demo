package ai.powerlaw.workflow.demo.core.workflow.bean;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TaskDTO {
    private Map<String, Variable> variableMap;
}
