package com.mysite.sbb.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Auditing 기능 적용 테스트")
    void findUser() {
        // given
        Member member = Member.builder()
                .memberId("test5@test.com")
                .memberPw("1234")
                .memberNick("test5")
                .build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertNotNull(savedMember.getCreatedDate());
        assertNotNull(savedMember.getUpdatedDate());
    }
}