package com.studyhub.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUp {

    private String username;
    private String password;
    private String nickname;

}
