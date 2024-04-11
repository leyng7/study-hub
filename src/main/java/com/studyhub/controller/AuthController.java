package com.studyhub.controller;

import com.studyhub.request.Login;
import com.studyhub.request.ReissueJwt;
import com.studyhub.request.SignUp;
import com.studyhub.response.JwtResponse;
import com.studyhub.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUp signUp) {

        authService.signUp(signUp);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Login login) {

        JwtResponse jwtResponse = authService.login(login);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtResponse> reissue(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid ReissueJwt reissueJwt
    ) {

        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
            String accessToken = authorization.split(" ")[1].trim();
            JwtResponse jwtResponse = authService.reissue(accessToken, reissueJwt.refreshToken());
            return ResponseEntity.ok(jwtResponse);
        }

        return ResponseEntity.badRequest().build();
    }

}