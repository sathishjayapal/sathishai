package me.sathish.rag_app_svc.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication (exclude = {
DataSourceAutoConfiguration .class,
        HibernateJpaAutoConfiguration.class
    })
@ComponentScan("me.sathish.rag_app_svc")
@EnableAsync
public class RagAppSvcApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RagAppSvcApplication.class, args);
    }

}
