package com.studyhub.modules.member.controller;

import com.studyhub.infra.config.UserPrincipal;
import com.studyhub.modules.member.request.MemberEdit;
import com.studyhub.modules.member.response.MemberResponse;
import com.studyhub.modules.member.service.MemberService;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/me")
    public void remove(
            @AuthenticationPrincipal UserPrincipal principal
    ) {

        memberService.remove(principal.getMemberId());
    }


}
