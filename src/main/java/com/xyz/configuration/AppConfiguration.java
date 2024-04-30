package com.xyz.configuration;


import com.xyz.repo.MySearchRepository;
import com.xyz.repo.MySearchRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.xyz.repo",
        basePackageClasses = {MySearchRepository.class},
        repositoryBaseClass = MySearchRepositoryImpl.class,
        repositoryImplementationPostfix = "Impl")
public class AppConfiguration {
}
