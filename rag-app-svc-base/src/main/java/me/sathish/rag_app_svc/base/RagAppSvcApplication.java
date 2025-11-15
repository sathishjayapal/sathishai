package me.sathish.rag_app_svc.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@ComponentScan("me.sathish.rag_app_svc")
@EnableAsync
public class RagAppSvcApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RagAppSvcApplication.class, args);
    }

}
