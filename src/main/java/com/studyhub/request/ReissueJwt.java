package com.studyhub.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ReissueJwt {

    private String refreshToken;

}
