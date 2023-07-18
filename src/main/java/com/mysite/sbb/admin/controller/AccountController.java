package com.mysite.sbb.admin.controller;

import com.mysite.sbb.admin.service.AccountService;
import com.mysite.sbb.member.dto.MemberInfoDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.dto.MemberDto;

import com.mysite.sbb.member.entity.MemberImg;
import com.mysite.sbb.member.entity.MemberInfo;
import com.mysite.sbb.member.repository.MemberImgRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    MemberImgRepository memberImgRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    // 회원목록
    @GetMapping("/list")
    public String accountList(Model model) {
        List<MemberDto> memberDtoList = accountService.getMemberList();
        model.addAttribute("memberList", memberDtoList);
        return "/admin/templates/pages/account/list";
    }

    @GetMapping("/{id}")
    public String accountDetail(@PathVariable("id") Long id, Model model) {
        logger.info("accountDetail .......... 진입 ");
        Member member = accountService.getMember(id);
        MemberInfo memberInfo = accountService.getMemberInfoByMember(member);
        MemberImg memberImg = memberImgRepository.findByMemberInfoId(id);

        model.addAttribute("member", member);
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("memberImg", memberImg);

        logger.info("member : " + member);
        logger.info("memberInfo : " + memberInfo);
        logger.info("memberImg : " + memberImg);

        return "/admin/templates/pages/account/detail";
    }


    // 관리자의 회원삭제
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable("id") @Valid Long id) {
        accountService.deleteAccount(id);
        return "redirect:/admin/account/list";
    }

    // 관리자의 회원수정
    @GetMapping("/update/{id}")
    public String updateAccountForm(@PathVariable("id") @Valid Long id, Model model) {
        Member member = accountService.getMember(id);
        MemberInfo memberInfo = accountService.getMemberInfoByMember(member);
        MemberImg memberImg = memberImgRepository.findByMemberInfoId(id);

        model.addAttribute("member", member);
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("memberImg", memberImg);

        return "/admin/templates/pages/account/setting";
    }

    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable("id") @Valid Long id, @Valid MemberDto memberDto,
                                @Valid MemberInfoDto memberInfoDto) {

        accountService.updateMemberEntityByAdmin(memberDto);
        accountService.updateMemberInfoEntityByAdmin(memberDto, memberInfoDto);
        return "redirect:/admin/account/list";
    }

    //관리자가 회원의 쓴 글 조회하기
    @GetMapping("/post/{id}")
    public String findMembersPost(@PathVariable("id") @Valid Long id, Model model) {
        Member member = accountService.getMember(id);
        model.addAttribute("member", member);
        return "/admin/templates/pages/account/posts";
    }
}
