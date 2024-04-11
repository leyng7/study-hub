package com.studyhub.controller;

import com.studyhub.config.UserPrincipal;
import com.studyhub.response.MemberResponse;
import com.studyhub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public MemberResponse me(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return memberService.getMe(principal.getMemberId());
    }


}
