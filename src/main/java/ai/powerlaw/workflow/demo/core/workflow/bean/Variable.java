package ai.powerlaw.workflow.demo.core.workflow.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variable {
    private Object value;

    private String type;
}
