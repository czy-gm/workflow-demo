package ai.powerlaw.workflow.demo.mapper.contract.entity;

import ai.powerlaw.workflow.demo.consts.contract.ContractStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract", indexes = {
        @Index(name = "contract_id_index", columnList = "contractId", unique = true)
})
public class ContractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractId;

    private String workFlowId;

    private ContractStatusEnum status;

    private Integer fileId;

    @Column(columnDefinition = "text")
    private String fieldDetail;
}
