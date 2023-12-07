package com.nnk.springboot.mock;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockPrincipalSecurityContextFactory.class)
public @interface WithMockPrincipal {

    String id() default "id";

    String[] authorities() default {"ADMIN"};
}