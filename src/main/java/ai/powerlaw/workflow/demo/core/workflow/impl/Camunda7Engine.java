package ai.powerlaw.workflow.demo.core.workflow.impl;

import ai.powerlaw.workflow.demo.core.workflow.WorkFlowEngine;
import ai.powerlaw.workflow.demo.core.workflow.bean.*;
import lombok.var;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.api.TaskApi;
import org.camunda.community.rest.client.dto.*;
import org.camunda.community.rest.client.invoker.ApiException;
import org.camunda.community.rest.client.springboot.CamundaHistoryApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Camunda7Engine implements WorkFlowEngine {
    private static final Logger logger = LoggerFactory.getLogger(Camunda7Engine.class);

    @Resource
    private ProcessDefinitionApi processDefinitionApi;

    @Resource
    private TaskApi taskApi;

    @Resource
    private CamundaHistoryApi camundaHistoryApi;


    @Override
    public Definition getDefinitionByKey(String key) throws ApiException {
        var result = new Definition();

        ProcessDefinitionDto definition = processDefinitionApi.
                getProcessDefinitionByKey(key);
        if (definition == null) {
            return null;
        }

        BeanUtils.copyProperties(definition, result);
        return result;
    }

    @Override
    public Instance startInstance(String definitionId, StartInstanceDTO startInstanceDTO)
            throws ApiException {
        var result = new Instance();
        Map<String, VariableValueDto> variableValueDtoMap = new HashMap<>();

        if (startInstanceDTO.getVariableMap() != null) {
            variableValueDtoMap = VariableMapConvert.
                    toVariableMap(startInstanceDTO.getVariableMap());
        }

        StartProcessInstanceDto startProcessInstanceDto = new StartProcessInstanceDto();
        startProcessInstanceDto.setVariables(variableValueDtoMap);
        startProcessInstanceDto.setBusinessKey(startInstanceDTO.getBusinessId());

        ProcessInstanceWithVariablesDto instance =
                processDefinitionApi.startProcessInstance(
                        definitionId, startProcessInstanceDto);
        if (instance == null) {
            return null;
        }

        BeanUtils.copyProperties(instance, result);
        return result;
    }

    @Override
    public Task getTaskByProcessIdAndKey(String processId, String taskDefinitionKey)
            throws ApiException {
        var result = new Task();

        TaskQueryDto taskQueryDto = new TaskQueryDto();
        taskQueryDto.setProcessInstanceId(processId);
        taskQueryDto.setTaskDefinitionKey(taskDefinitionKey);
        List<TaskDto> tasks = taskApi.queryTasks(null, null, taskQueryDto);

        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }

        BeanUtils.copyProperties(tasks.get(0), result);
        return result;
    }

    @Override
    public void completeTask(String taskId, TaskDTO taskDTO) throws ApiException {
        Map<String, VariableValueDto> variableValueDtoMap = new HashMap<>();
        if (taskDTO.getVariableMap() != null) {
            variableValueDtoMap = VariableMapConvert.
                    toVariableMap(taskDTO.getVariableMap());
        }

        CompleteTaskDto completeTaskDto = new CompleteTaskDto();
        completeTaskDto.setVariables(variableValueDtoMap);
        taskApi.complete(taskId, completeTaskDto);
    }
}
