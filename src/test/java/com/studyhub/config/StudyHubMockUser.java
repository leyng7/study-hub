package com.studyhub.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = StudyHubMockSecurityContext.class)
public @interface StudyHubMockUser {

    String username() default "leyng7";

    String password() default "1q2w3e4r!";

    String nickname() default "몽친";

}
