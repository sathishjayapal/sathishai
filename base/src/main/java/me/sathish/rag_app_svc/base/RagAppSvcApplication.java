package me.sathish.rag_app_svc.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("me.sathish.rag_app_svc")
public class RagAppSvcApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RagAppSvcApplication.class, args);
    }

}
