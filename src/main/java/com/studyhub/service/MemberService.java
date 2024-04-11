package com.studyhub.service;

import com.studyhub.domain.Member;
import com.studyhub.expcetion.MemberNotFound;
import com.studyhub.repository.MemberRepository;
import com.studyhub.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMe(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();

    }

}
