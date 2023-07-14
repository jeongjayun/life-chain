package com.mysite.sbb.admin.service;

import com.mysite.sbb.admin.repository.AccountRepository;
import com.mysite.sbb.common.DataNotFoundException;
import com.mysite.sbb.common.vo.AttachImageVO;
import com.mysite.sbb.member.entity.MemberImg;
import com.mysite.sbb.member.repository.MemberImgRepository;
import com.mysite.sbb.member.dto.MemberDto;
import com.mysite.sbb.member.dto.MemberInfoDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.entity.MemberInfo;
import com.mysite.sbb.member.repository.MemberInfoRepository;
import com.mysite.sbb.member.repository.MemberRepository;
import com.mysite.sbb.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberService memberService;
    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;
    private final MemberImgRepository memberImgRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public List<MemberDto> getMemberList() { //전체회원 가져오기
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (Member member : members) {
            MemberDto memberDto = MemberDto.builder()
                    .id(member.getId())
                    .memberId(member.getMemberId())
                    .memberNick(member.getMemberNick())
                    .memberDate(member.getCreatedDate())
                    .memberStatus(member.getMemberStatus())
                    .lastLoginDate(member.getUpdatedDate())
                    .build();
            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

    public Member getMember(Long id) { //id로 멤버 찾기
        Optional<Member> member = this.accountRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    public void deleteAccount(Long id) { //관리자의 회원삭제
        accountRepository.deleteById(id);
    } //회원삭제

    public MemberInfo getMemberInfoByMember(Member member) { //member로 memberInfo 찾기
        if (member != null) {
            MemberInfo memberInfo = member.getMemberInfo();
            if (memberInfo != null) {
                return memberInfo;
            }
        }
        return null;
    }

    @Transactional
    public void updateMemberEntityByAdmin(MemberDto memberDto) { //Member 내용변경
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Member not found"));
        //member Id에 있는 계정 정보 확인

        member.setMemberNick(memberDto.getMemberNick());
        member.setMemberStatus(memberDto.getMemberStatus());
        accountRepository.save(member);

        logger.info("updateMemberEntityByAdmin(MemberDto memberDto) 실행");
        logger.info("수정된 member값 확인 : " + member);
    }

    @Transactional
    public void updateMemberInfoEntityByAdmin(MemberDto memberDto, MemberInfoDto memberInfoDto) { //MemberInfo 변경
        logger.info("updateMemberInfoEntityByAdmin 로거찍기");
        Member member = memberRepository.findBymemberId(memberDto.getMemberId())
                .orElseThrow(() -> new DataNotFoundException("Member not found"));
        MemberInfo memberInfo = getMemberInfoByMember(member);

        // AttachImageVO 객체를 MemberImg 엔티티로 변환하여 저장
        if (!memberInfoDto.getImageList().isEmpty()) {
            memberImgRepository.deleteAllByMemberInfo(memberInfo);

            for (AttachImageVO attachImageVO : memberInfoDto.getImageList()) {
                MemberImg memberImg = new MemberImg();
                memberImg.setUploadPath(attachImageVO.getUploadPath());
                memberImg.setUuid(attachImageVO.getUuid());
                memberImg.setFileName(attachImageVO.getFileName());
                memberImg.setMemberInfo(memberInfo);
                memberImgRepository.save(memberImg);
            }
        }

        memberInfo.setZipcode(memberInfoDto.getZipcode());
        memberInfo.setAddress1(memberInfoDto.getAddress1());
        memberInfo.setAddress2(memberInfoDto.getAddress2());
        memberInfo.setMemberIntroduce(memberInfoDto.getMemberIntroduce());
        memberInfo.setMemberTel(memberInfoDto.getMemberTel());

        logger.info("수정한 memberInfo : " + memberInfo);
        member.setMemberInfo(memberInfo);
        memberInfoRepository.save(memberInfo); // memberInfo 저장
    }
}