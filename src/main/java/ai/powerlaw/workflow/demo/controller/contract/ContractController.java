package ai.powerlaw.workflow.demo.controller.contract;

import ai.powerlaw.workflow.demo.service.contract.ContractService;
import ai.powerlaw.workflow.demo.service.contract.bean.ContractDetail;
import ai.powerlaw.workflow.demo.service.contract.bean.ContractBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.community.rest.client.invoker.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/contract")
public class ContractController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Resource
    private ContractService contractService;

    @PostMapping(value = "/draft")
    @ResponseBody
    public String draft(@RequestBody ContractBody body)
            throws ApiException, JsonProcessingException {
        String contractId = contractService.create(body);
        return contractId;
    }

    @PutMapping(value = "/{contractId}")
    @ResponseBody
    public String update(@PathVariable("contractId") String contractId,
                         @RequestBody ContractBody body)
            throws JsonProcessingException {
        contractService.update(contractId, body);
        return contractId;
    }

    @GetMapping(value = "/{contractId}")
    @ResponseBody
    public ContractDetail get(@PathVariable("contractId") String contractId)
            throws JsonProcessingException {
        return contractService.get(contractId);
    }

    @PostMapping(value = "/draft_completed/{contractId}")
    @ResponseBody
    public void draftCompleted(@PathVariable("contractId") String contractId,
                                 @RequestBody ContractBody body)
            throws JsonProcessingException, ApiException {
        contractService.draftCompleted(contractId, body);
    }
}
