package ai.powerlaw.workflow.demo.core.workflow;

import ai.powerlaw.workflow.demo.core.workflow.bean.*;
import org.camunda.community.rest.client.invoker.ApiException;

import java.util.List;
import java.util.Map;

public interface WorkFlowEngine {
    Definition getDefinitionByKey(String definitionKey) throws ApiException;

    Instance startInstance(String definitionId,
                           StartInstanceDTO startInstanceDTO) throws ApiException;

    Task getTaskByProcessIdAndKey(String processId, String taskDefinitionKey) throws ApiException;

    void completeTask(String taskId, TaskDTO taskDTO) throws ApiException;

    void subscribeTask(String taskTopic);
}
