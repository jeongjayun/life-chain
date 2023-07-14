package com.mysite.sbb;

import com.mysite.sbb.common.DataNotFoundException;
import com.mysite.sbb.member.form.MemberRegisterForm;
import com.mysite.sbb.member.mail.EmailException;
import com.mysite.sbb.member.mail.TempPasswordForm;
import com.mysite.sbb.member.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "form_errors";
    }

    @GetMapping("/login")
    public String login() {
        return "/login/login_form";
    }
    // PostMapping은 스프링 시큐리티가 대신 처리.

    @GetMapping("/register")
    public String register(MemberRegisterForm memberRegisterForm) {
        return "/login/register_form";
    }

    @PostMapping("/register")
    public String register(@Valid MemberRegisterForm memberRegisterForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/login/register_form";
        }
        if(!memberRegisterForm.getMemberPw1().equals(memberRegisterForm.getMemberPw2())) {
            bindingResult.rejectValue("memberPw2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
            return "/login/register_form";
        }
        try {
            memberService.create(memberRegisterForm);
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 회원입니다.");
            return "/login/register_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/login/register_form";
        }

        return "redirect:/";
    }
    
	// 이메일 인증
	@GetMapping("/forgotPass")
	String forgotpass() {
		return "/login/forgot_pass";
	}
	
    @PostMapping("/forgotPass")
    public String sendTempPassword(@Valid TempPasswordForm tempPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	return "/login/forgot_pass";
        }

        try {
            memberService.modifyPassword(tempPasswordForm.getMemberId());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            bindingResult.reject("emailNotFound", e.getMessage());
            return "/login/forgot_pass";
        } catch (EmailException e) {
            e.printStackTrace();
            bindingResult.reject("sendEmailFail", e.getMessage());
            return "/login/forgot_pass";
        }
        return "redirect:/";
    }

}
