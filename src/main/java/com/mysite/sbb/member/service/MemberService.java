package com.mysite.sbb.member.service;

import com.mysite.sbb.member.entity.MemberInfo;
import com.mysite.sbb.common.config.MemberStatus;
import com.mysite.sbb.member.form.MemberRegisterForm;
import com.mysite.sbb.member.mail.EmailException;
import com.mysite.sbb.member.repository.MemberInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.mail.TempPasswordMail;
import com.mysite.sbb.member.repository.MemberRepository;
import com.mysite.sbb.common.CommonUtil;
import com.mysite.sbb.common.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TempPasswordMail tempPasswordMail;
    private final CommonUtil commonUtil;

    public Member create(MemberRegisterForm memberRegisterForm) {
        Member member = Member.builder()
                .memberId(memberRegisterForm.getMemberId())
                .memberPw(passwordEncoder.encode(memberRegisterForm.getMemberPw1()))
                .memberNick(memberRegisterForm.getMemberNick())
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        // Member 저장
        this.memberRepository.save(member);

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMember(member); // 연관 관계 설정
        memberInfo.setMemberId(member.getMemberId());

        // Member 저장 후 ID 값 가져오기
        this.memberRepository.save(member);
        Long memberId = member.getId();

        // 가져온 ID 값을 사용하여 MemberInfo 저장
        memberInfo.setId(memberId);
        this.memberInfoRepository.save(memberInfo);

        return member;
    }

    public Member getMember(String memberId) {
        Optional<Member> member = this.memberRepository.findBymemberId(memberId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    public void memberModify(Member member, String memberId, String memberNick) {
        member.setMemberId(memberId);
        member.setMemberNick(memberNick);
        this.memberRepository.save(member);
    }

    public Member getMemberByMemberNick(String memberNick) {
        Member member = new Member();
        member.setMemberNick(memberNick);
        return member;
    }

    @Transactional
    public void modifyPassword(String memberId) throws EmailException {
        String tempPassword = commonUtil.createTempPassword();
        Member member = memberRepository.findBymemberId(memberId)
                .orElseThrow(() -> new DataNotFoundException("해당 이메일의 유저가 없습니다."));
        member.setMemberPw(passwordEncoder.encode(tempPassword));
        memberRepository.save(member);
        tempPasswordMail.sendSimpleMessage(memberId, tempPassword);
    }

    public Member findByMemberNick(String memberNick) {
        // 사용자 닉네임으로 Member 조회하는 로직
        return memberRepository.findByMemberNick(memberNick);
    }
}
