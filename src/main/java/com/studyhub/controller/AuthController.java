package com.studyhub.controller;

import com.studyhub.request.Login;
import com.studyhub.response.JwtResponse;
import com.studyhub.request.SignUp;
import com.studyhub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/reissue")
//    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
//        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
//    }

}