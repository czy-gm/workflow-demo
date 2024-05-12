package ai.powerlaw.workflow.demo.core.workflow.impl;

import ai.powerlaw.workflow.demo.core.workflow.bean.Variable;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

public class VariableMapConvert {
    public static Map<String, VariableValueDto> toVariableMap(final Map<String, Variable> map) {
        final Map<String, VariableValueDto> result = new HashMap<String, VariableValueDto>();
        for (final Map.Entry<String, Variable> entry : map.entrySet()) {
            VariableValueDto variableValueDto = new VariableValueDto();
            BeanUtils.copyProperties(entry.getValue(), variableValueDto);
            result.put(entry.getKey(), variableValueDto);
        }
        return result;
    }
}
