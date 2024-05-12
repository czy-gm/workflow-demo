package ai.powerlaw.workflow.demo.core.workflow.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String id;

    private String name;

    private String processDefinitionId;

    private String processInstanceId;

    private String taskDefinitionKey;
}
