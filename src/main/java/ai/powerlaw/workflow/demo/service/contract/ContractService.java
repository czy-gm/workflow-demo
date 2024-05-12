package ai.powerlaw.workflow.demo.service.contract;

import ai.powerlaw.workflow.demo.service.contract.bean.ContractDetail;
import ai.powerlaw.workflow.demo.service.contract.bean.ContractBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.community.rest.client.invoker.ApiException;

public interface ContractService {
    String create(ContractBody body) throws ApiException, JsonProcessingException;

    void update(String contractId, ContractBody body) throws JsonProcessingException;

    ContractDetail get(String contractId) throws JsonProcessingException;

    void draftCompleted(String contractId, ContractBody body) throws ApiException, JsonProcessingException;
}
