package com.studyhub.controller;

import com.studyhub.config.UserPrincipal;
import com.studyhub.request.MemberEdit;
import com.studyhub.response.MemberResponse;
import com.studyhub.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        return memberService.get(principal.getMemberId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/me")
    public void edit(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid MemberEdit request
    ) {

        memberService.edit(principal.getMemberId(), request);
    }


}
