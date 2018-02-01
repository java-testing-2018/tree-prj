package com.tree.springcloud.bl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.tree.springcloud.bl.repository")
public class JpaConfig {
}
