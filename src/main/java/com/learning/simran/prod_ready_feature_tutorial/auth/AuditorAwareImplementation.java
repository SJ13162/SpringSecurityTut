package com.learning.simran.prod_ready_feature_tutorial.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImplementation implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //get security context
        //get authentication
        //get principle
        //get the username
        return Optional.of("Simranjeet Singh");
    }

}
