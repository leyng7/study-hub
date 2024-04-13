package com.studyhub.modules.member.service;

import com.studyhub.infra.expcetion.MemberNotFound;
import com.studyhub.modules.member.domain.Member;
import com.studyhub.modules.member.domain.MemberEditor;
import com.studyhub.modules.member.repository.MemberRepository;
import com.studyhub.modules.member.request.MemberEdit;
import com.studyhub.modules.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse get(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();

    }

    @Transactional
    public void edit(Long memberId, MemberEdit edit) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        MemberEditor.MemberEditorBuilder memberBuilder = member.toEditor();

        MemberEditor memberEditor = memberBuilder.nickname(edit.nickname()).build();

        member.edit(memberEditor);
    }

    @Transactional
    public void remove(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        MemberEditor.MemberEditorBuilder memberBuilder = member.toEditor();

        MemberEditor memberEditor = memberBuilder.removed(true).build();

        member.edit(memberEditor);
    }

}
