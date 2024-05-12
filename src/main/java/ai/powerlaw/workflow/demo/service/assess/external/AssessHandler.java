package ai.powerlaw.workflow.demo.service.assess.external;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssessHandler {
    private static Logger logger = LoggerFactory.getLogger(AssessHandler.class);

    @Bean
    @ExternalTaskSubscription(value = "create_assess")
    public ExternalTaskHandler createAssessHandler() {
        return (externalTask, externalTaskService) -> {
            // TODO 调用创建评审逻辑
            logger.info("assess created.");
            externalTaskService.complete(externalTask);
        };
    }
}
