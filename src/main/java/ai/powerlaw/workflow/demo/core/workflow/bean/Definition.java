package ai.powerlaw.workflow.demo.core.workflow.bean;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Definition {
    private String id;

    private String key;

    private String name;

    private Integer version;
}
