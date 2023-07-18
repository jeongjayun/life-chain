package com.mysite.sbb.member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.member.entity.Answer;
import com.mysite.sbb.member.entity.Comment;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.entity.Question;
import com.mysite.sbb.member.form.MemberRegisterForm;
import com.mysite.sbb.member.service.MemberService;

import org.springframework.ui.Model;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;


@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
	
	private final MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/setting")
	public String setting(Model model, Principal principal) {
		String username = principal.getName();
		Member member = this.memberService.getMember(username);
		model.addAttribute("member", member);
		return "/member/member_setting";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/setting")
	public String setting(Principal principal, MemberRegisterForm memberRegisterForm, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "/member/member_setting";
	    }
	    
	    String username = principal.getName();
	    Member member = this.memberService.getMember(username);
	    if (!member.getMemberId().equals(memberRegisterForm.getMemberId())) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    }
	    
	    this.memberService.memberModify(member, memberRegisterForm.getMemberId(), memberRegisterForm.getMemberNick());
	    return "redirect:/member/setting";
	}

    @GetMapping("/{memberNick}")
    public String userQuestionList(@PathVariable("memberNick") String memberNick, Model model) {
        Member member = memberService.getMemberByMemberNick(memberNick);
        List<Question> questions = member.getQuestion();
        List<Answer> answers = member.getAnswer();
        List<Comment> comments = member.getComment();
        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);
        model.addAttribute("comments", comments);
        model.addAttribute("member", member);

        return "/member/member_page";
    }
    



	
}