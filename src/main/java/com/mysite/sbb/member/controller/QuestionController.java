package com.mysite.sbb.member.controller;

import com.mysite.sbb.member.entity.Answer;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.entity.Question;
import com.mysite.sbb.member.form.AnswerForm;
import com.mysite.sbb.member.form.CommentForm;
import com.mysite.sbb.member.form.QuestionForm;
import com.mysite.sbb.member.service.AnswerService;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.member.service.QuestionService;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	private final MemberService memberService;
	private final AnswerService answerService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging" ,paging);
		model.addAttribute("kw" ,kw);
		
		
		return "/question/question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
			@RequestParam(value = "answerPage", defaultValue = "0") int answerPage, CommentForm commentForm) {
		Question question = this.questionService.getQuestion(id);
		Page<Answer> answerPaging =  this.answerService.getList(question, answerPage);
		questionService.updateView(id);
		model.addAttribute("question", question);
		model.addAttribute("answerPaging", answerPaging);
		return "/question/question_detail";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "/question/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/question/question_form";
        }
        Member member = this.memberService.getMember(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), member);
        return "redirect:/question/list";
    }
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getMemberId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "/question/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
			if(bindingResult.hasErrors()) {
				return "/question/question_form";
		}
			Question question = this.questionService.getQuestion(id);
			if(!question.getAuthor().getMemberId().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
			}
			this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
			return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getMemberId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		Member member = this.memberService.getMember(principal.getName());
		this.questionService.vote(question, member);
		return String.format("redirect:/question/detail/%s", id);
	}

	

	
}