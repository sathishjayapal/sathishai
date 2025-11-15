package me.sathish.rag_app_svc.base.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("me.sathish.rag_app_svc.base")
@EnableJpaRepositories("me.sathish.rag_app_svc.base")
@EnableTransactionManagement
public class DomainConfig {
}
