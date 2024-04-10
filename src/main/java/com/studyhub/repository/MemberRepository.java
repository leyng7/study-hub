package com.studyhub.repository;

import com.studyhub.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String email);

    boolean existsByUsername(String email);

}
