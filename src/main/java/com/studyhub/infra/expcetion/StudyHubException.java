package com.studyhub.infra.expcetion;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class StudyHubException extends RuntimeException {


    public final Map<String, String> validation = new HashMap<>();

    public StudyHubException(String message) {
        super(message);
    }

    public StudyHubException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

}
