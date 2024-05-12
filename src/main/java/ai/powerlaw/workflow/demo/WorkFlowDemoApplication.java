package ai.powerlaw.workflow.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WorkFlowDemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(WorkFlowDemoApplication.class);
    }
}
