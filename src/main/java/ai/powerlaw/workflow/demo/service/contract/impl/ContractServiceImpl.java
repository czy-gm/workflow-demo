package ai.powerlaw.workflow.demo.service.contract.impl;

import ai.powerlaw.workflow.demo.consts.contract.ContractStatusEnum;
import ai.powerlaw.workflow.demo.core.workflow.WorkFlowEngine;
import ai.powerlaw.workflow.demo.core.workflow.bean.*;
import ai.powerlaw.workflow.demo.core.exception.BusinessException;
import ai.powerlaw.workflow.demo.mapper.contract.ContractRepository;
import ai.powerlaw.workflow.demo.mapper.contract.entity.ContractEntity;
import ai.powerlaw.workflow.demo.service.contract.ContractService;
import ai.powerlaw.workflow.demo.service.contract.bean.ContractDetail;
import ai.powerlaw.workflow.demo.service.contract.bean.ContractBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ai.powerlaw.workflow.demo.core.code.ErrorCodeEnum.*;

@Service
public class ContractServiceImpl implements ContractService {
    @Value("${workflow.definition.process.contract}")
    private String draftWorkFlowDefinitionKey;

    @Value("${workflow.definition.task.draft}")
    private String draftTaskDefinitionKey;

    @Resource
    private WorkFlowEngine workFlowEngine;

    @Resource
    private ContractRepository contractRepository;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String create(ContractBody body) throws ApiException, JsonProcessingException {
        Definition definition = workFlowEngine.
                getDefinitionByKey(draftWorkFlowDefinitionKey);
        if (definition == null) {
            throw new BusinessException(ERROR_CODE_PROCESS_DEFINITION_NOT_EXIST);
        }

        Map<String, Variable> variableMap = new HashMap<>();
        if (body.getFieldDetail() != null) {
            body.getFieldDetail().forEach((x, y) -> {
                Variable variable = new Variable(y, "string");
                variableMap.put(x, variable);
            });
        }
        variableMap.put("pointer", new Variable("draft", "string"));

        StartInstanceDTO startInstanceDTO = new StartInstanceDTO();
        startInstanceDTO.setVariableMap(variableMap);
        Instance instance = workFlowEngine.startInstance(definition.getId(), startInstanceDTO);

        String contractId = UUID.randomUUID().toString();

        contractRepository.save(
                ContractEntity.builder()
                        .contractId(contractId)
                        .workFlowId(instance.getId())
                        .fileId(body.getFileId())
                        .status(ContractStatusEnum.DRAFT)
                        .fieldDetail(objectMapper.writeValueAsString(body.getFieldDetail()))
                        .build()
        );
        return contractId;
    }

    @Override
    public void update(String contractId, ContractBody body)
            throws JsonProcessingException {
        ContractEntity contract = contractRepository.findByContractId(contractId);
        if (contract == null) {
            throw new BusinessException(ERROR_CODE_CONTRACT_NOT_EXIST);
        }
        // 更新引擎变量

        contract.setFieldDetail(objectMapper.writeValueAsString(body.getFieldDetail()));
        contractRepository.save(contract);

    }

    @Override
    public ContractDetail get(String contractId) throws JsonProcessingException {
        ContractEntity contract = contractRepository.findByContractId(contractId);
        if (contract == null) {
            throw new BusinessException(ERROR_CODE_CONTRACT_NOT_EXIST);
        }

        Map<String, Object> fieldDetail = objectMapper.
                readValue(contract.getFieldDetail(), Map.class);
        return ContractDetail.builder()
                .contractId(contract.getContractId())
                .workFlowId(contract.getWorkFlowId())
                .fileId(contract.getFileId())
                .Status(contract.getStatus().getCode())
                .fieldDetail(fieldDetail)
                .build();
    }

    @Override
    public void draftCompleted(String contractId, ContractBody body)
            throws ApiException, JsonProcessingException {
        ContractEntity contract = contractRepository.findByContractId(contractId);
        if (contract == null) {
            throw new BusinessException(ERROR_CODE_CONTRACT_NOT_EXIST);
        }

        Task task = workFlowEngine.
                getTaskByProcessIdAndKey(contract.getWorkFlowId(), draftTaskDefinitionKey);
        if (task == null) {
            throw new BusinessException(ERROR_CODE_DRAFT_TASK_NOT_EXIST);
        }

        Map<String, Variable> variableMap = new HashMap<>();
        if (body.getFieldDetail() != null) {
            body.getFieldDetail().forEach((x, y) -> {
                Variable variable = new Variable(y, "string");
                variableMap.put(x, variable);
            });
        }

        workFlowEngine.completeTask(task.getId(),
                TaskDTO.builder()
                        .variableMap(variableMap)
                        .build()
        );

        contract.setFileId(body.getFileId());
        contract.setFieldDetail(objectMapper.writeValueAsString(body.getFieldDetail()));
        contractRepository.save(contract);
    }
}
