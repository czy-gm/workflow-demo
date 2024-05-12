package ai.powerlaw.workflow.demo.mapper.contract;

import ai.powerlaw.workflow.demo.mapper.contract.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractEntity, Integer> {
    ContractEntity findByContractId(String contractId);
}
